package com.vainolo.phd.opm.interpreter.inzoomedprocessinstance;

import static com.vainolo.phd.opm.utilities.OPMLogger.logFine;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vainolo.phd.opm.interpreter.OPMObjectInstance;
import com.vainolo.phd.opm.interpreter.OPMObjectInstanceValueAnalyzer;
import com.vainolo.phd.opm.interpreter.OPMParameter;
import com.vainolo.phd.opm.interpreter.OPMProcessInstance;
import com.vainolo.phd.opm.model.OPMLink;
import com.vainolo.phd.opm.model.OPMObject;
import com.vainolo.phd.opm.model.OPMProcess;
import com.vainolo.phd.opm.utilities.analysis.OPDAnalyzer;

public class OPMInZoomedProcessArgumentHandler {
  private OPDAnalyzer analyzer = new OPDAnalyzer();
  private OPMObjectInstanceValueAnalyzer valueAnalyzer;
  private OPMInZoomedProcessInstanceHeap heap;

  public OPMInZoomedProcessArgumentHandler(OPDAnalyzer analyzer, OPMInZoomedProcessInstanceHeap heap) {
    this.analyzer = analyzer;
    this.heap = heap;
    this.valueAnalyzer = new OPMObjectInstanceValueAnalyzer();
  }

  public void loadInstanceArguments(OPMProcess process, OPMProcessInstance instance) {
    Map<String, OPMArgument> namedArguments = Maps.newHashMap();
    List<OPMArgument> anonymousArguments = Lists.newArrayList();

    for(OPMLink incomingDataLink : analyzer.findIncomingDataLinks(process)) {
      OPMArgument argument = new OPMArgument(analyzer.getObject(incomingDataLink));
      if(incomingDataLink.getCenterDecoration() == null || "".equals(incomingDataLink.getCenterDecoration())) {
        anonymousArguments.add(argument);
      } else if(incomingDataLink.getCenterDecoration().contains(",")) {
        argument.isCollectionElement = true;
        String argName = incomingDataLink.getCenterDecoration().split(",")[0];
        String collectionReference = incomingDataLink.getCenterDecoration().split(",")[1];
        if(valueAnalyzer.isNumericalLiteral(collectionReference)) {
          argument.collectionElementIndex = valueAnalyzer.parseNumericalLiteral(collectionReference);
        } else {
          argument.collectionElementName = collectionReference;
        }
        if("".equals(argName)) {
          anonymousArguments.add(argument);
        } else {
          namedArguments.put(argName, argument);
        }
      } else {
        namedArguments.put(incomingDataLink.getCenterDecoration(), argument);
      }
    }

    logFine("Found {0} anonymous arguments and {1} named arguments.", anonymousArguments.size(), namedArguments.size());

    List<String> availableParametersNames = Lists.transform(instance.getIncomingParameterNames(),
        new Function<OPMParameter, String>() {
          @Override
          public String apply(OPMParameter input) {
            return input.getName();
          }
        });
    loadNamedArguments(instance, namedArguments);
    availableParametersNames.removeAll(namedArguments.keySet());

    loadAnonymousArguments(instance, anonymousArguments, availableParametersNames);
  }

  private void loadNamedArguments(OPMProcessInstance instance, Map<String, OPMArgument> namedArguments) {
    for(String parameterName : namedArguments.keySet()) {
      instance.setArgument(parameterName, getValue(namedArguments.get(parameterName)));
    }
  }

  private void loadAnonymousArguments(OPMProcessInstance instance, List<OPMArgument> arguments,
      List<String> availableParameters) {

    // First arguments who's variable names matches the parameter name
    Iterator<String> availableParametersIterator = availableParameters.iterator();
    while(availableParametersIterator.hasNext()) {
      String parameterName = availableParametersIterator.next();
      if(arguments.contains(parameterName)) {
        OPMObjectInstance argument = getValue(arguments.get(arguments.indexOf(parameterName)));
        instance.setArgument(parameterName, argument);
        arguments.remove(parameterName);
        availableParametersIterator.remove();
      }
    }

    // now remaining arguments using available parameter names
    availableParametersIterator = availableParameters.iterator();
    while(availableParametersIterator.hasNext()) {
      String parameterName = availableParametersIterator.next();
      instance.setArgument(parameterName, getValue(arguments.remove(0)));
      availableParametersIterator.remove();
    }

    // In case there are left arguments, pass them as parameters with default
    // names
    if(arguments.size() > 0) {
      int argNumber = 0;
      for(OPMArgument argument : arguments) {
        instance.setArgument("arg" + argNumber, getValue(argument));
        argNumber++;
      }
    }
  }

  public void extractResultsToVariables(OPMProcess process, OPMProcessInstance instance) {
    Map<String, OPMArgument> namedResults = Maps.newHashMap();
    List<OPMArgument> anonymousResult = Lists.newArrayList();

    for(OPMLink resultLink : analyzer.findOutgoingDataLinks(process)) {
      OPMArgument argument = new OPMArgument(analyzer.getObject(resultLink));
      if(resultLink.getCenterDecoration() == null || "".equals(resultLink.getCenterDecoration())) {
        anonymousResult.add(argument);
      } else if(resultLink.getCenterDecoration().contains(",")) {
        argument.isCollectionElement = true;
        String argName = resultLink.getCenterDecoration().split(",")[0];
        String collectionReference = resultLink.getCenterDecoration().split(",")[1];
        if(valueAnalyzer.isNumericalLiteral(collectionReference)) {
          argument.collectionElementIndex = valueAnalyzer.parseNumericalLiteral(collectionReference);
        } else {
          argument.collectionElementName = collectionReference;
        }
        if("".equals(argName)) {
          anonymousResult.add(argument);
        } else {
          namedResults.put(argName, argument);
        }
      } else {
        namedResults.put(resultLink.getCenterDecoration(), argument);
      }
    }

    logFine("Found {0} anonymous results and {1} named results.", anonymousResult.size(), namedResults.size());

    // First extract named results
    List<String> outgoingParametersNames = Lists.transform(instance.getOutgoingParameterNames(),
        new Function<OPMParameter, String>() {
          @Override
          public String apply(OPMParameter input) {
            return input.getName();
          }
        });
    for(String namedResult : namedResults.keySet()) {
      OPMArgument argument = namedResults.get(namedResult);
      if(argument.object.isCollection()) {
        setCollectionElementValue(argument, instance.getArgument(namedResult));
      } else {
        heap.setVariable(argument.object, instance.getArgument(namedResult));
      }
      outgoingParametersNames.remove(namedResult);
    }

    // Now extract where the variable in the instance matched the result object
    Iterator<OPMArgument> anonymousResultsIterator = anonymousResult.iterator();
    while(anonymousResultsIterator.hasNext()) {
      OPMArgument argument = anonymousResultsIterator.next();
      if(instance.getOutgoingParameterNames().contains(argument.object.getName())) {
        if(instance.getArgument(argument.object.getName()) != null) {
          if(argument.object.isCollection()) {
            setCollectionElementValue(argument, instance.getArgument(argument.object.getName()));
          } else {
            heap.setVariable(argument.object, instance.getArgument(argument.object.getName()));
          }
          anonymousResultsIterator.remove();
        }
      }
    }

    // Now extract all remaining outgoing parameters to the remaining anonymous
    // result objects
    int remainingResults = (outgoingParametersNames.size() < anonymousResult.size()) ? outgoingParametersNames.size()
        : anonymousResult.size();
    for(int i = 0; i < remainingResults; i++) {
      OPMArgument argument = anonymousResult.get(i);
      if(argument.object.isCollection()) {
        setCollectionElementValue(argument, instance.getArgument(outgoingParametersNames.get(i)));
      } else {
        heap.setVariable(argument.object, instance.getArgument(outgoingParametersNames.get(i)));
      }
    }
  }

  public void setCollectionElementValue(OPMArgument argument, OPMObjectInstance value) {
    OPMObjectInstance currentValue = heap.getVariable(argument.object);
    if(currentValue == null) {
      currentValue = OPMObjectInstance.createCollectionInstace();
    }
    if(argument.isCollectionElement) {
      if(argument.collectionElementName != null) {
        currentValue.putCollectionElement(argument.collectionElementName, value);
      } else if(argument.collectionElementIndex != null) {
        currentValue.putCollectionElementAtIndex(argument.collectionElementIndex.intValue(), value);
      }
    } else {
      currentValue.appendCollectionElement(value);
    }
    heap.setVariable(argument.object, currentValue);
  }

  private OPMObjectInstance getValue(OPMArgument argument) {
    if(!argument.isCollectionElement) {
      return heap.getVariable(argument.object);
    } else {
      OPMObjectInstance instance = heap.getVariable(argument.object);
      if(argument.collectionElementName != null) {
        return instance.getCollectionElement(argument.collectionElementName);
      } else if(argument.collectionElementIndex != null) {
        return instance.getCollectionElementAtIndex(argument.collectionElementIndex.intValue());
      } else {
        throw new IllegalStateException("Referenced collection element with no index or name.");
      }
    }
  }

  class OPMArgument {
    public BigDecimal collectionElementIndex;
    public String collectionElementName;
    private OPMObject object;
    private boolean isCollectionElement = false;

    public OPMArgument(OPMObject object) {
      this.object = object;
    }
  }
}
