/*******************************************************************************
 * Copyright (c) 2012 Arieh 'Vainolo' Bibliowicz
 * You can use this code for educational purposes. For any other uses
 * please contact me: vainolo@gmail.com
 *******************************************************************************/
package com.vainolo.phd.opm.interpreter;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;
import com.vainolo.phd.opm.model.OPMFactory;
import com.vainolo.phd.opm.model.OPMObject;
import com.vainolo.phd.opm.model.OPMProcess;
import com.vainolo.utils.SimpleLoggerFactory;

public class OPMJavaProcessInstance extends OPMAbstractProcessInstance implements OPMExecutableInstance {
  private static final Logger logger = SimpleLoggerFactory.createLogger(OPMJavaProcessInstance.class.getName());

  private final Pattern classAndMethodAndParametersPattern = Pattern.compile("(.*)\\.([^\\.]*)\\((.*)\\)");

  private String className = null;
  private String methodName = null;
  private String[] parameters = new String[0]; // to avoid null checking and
                                               // instead use iteration on zero
                                               // length array

  private Method method;
  private Object[] arguments;
  private Object target = null;

  private OPMProcess process;

  public OPMJavaProcessInstance(OPMProcess process) {
    this.process = process;
  }

  @Override
  public String getName() {
    return process.getName();
  }

  @Override
  protected void executing() {
    loadMethod();
    for(String parameter : parameters) {
      OPMObject object = OPMFactory.eINSTANCE.createOPMObject();
      object.setName(parameter);
    }

    arguments = new Object[parameters.length];
    for(int i = 0; i < parameters.length; i++) {
      arguments[i] = getArgument("arg" + i);
    }

    if(!Modifier.isStatic(method.getModifiers()))
      target = getArgument("this");

    final Object result = callMethod(method);
    OPMObjectInstance instance = OPMObjectInstance.create(result);
    if(!method.getReturnType().equals(Void.TYPE)) {
      setArgument("result", instance);
    }
  }

  private Object callMethod(Method method) {
    Preconditions.checkNotNull(method);

    Object result = null;
    try {
      result = method.invoke(target, arguments);
    } catch(ReflectiveOperationException e) {
      logger.info("Method " + methodName + " could not be called. See log for details.");
      throw new RuntimeException(e);
    }
    return result;
  }

  private Method loadMethod() {
    // In the description we store the class name and the full signature of the
    // method -
    // fullClassName.methodName(className1,className2...)
    // final Matcher classAndMethodAndParametersMatcher =
    // classAndMethodAndParametersPattern.matcher(getProcess().getDescription());
    //
    // if(!classAndMethodAndParametersMatcher.find()) {
    // logger.info("Could not parse method definition " +
    // getProcess().getDescription() + " for process " +
    // getProcess().getName());
    // throw new RuntimeException("Could not parse method definition " +
    // getProcess().getDescription() +
    // " for process " + getProcess().getName());
    // }

    // className = classAndMethodAndParametersMatcher.group(1);
    // methodName = classAndMethodAndParametersMatcher.group(2);
    // String methodParameters = classAndMethodAndParametersMatcher.group(3);

    // if(!methodParameters.isEmpty()) {
    // methodParameters = methodParameters.replaceAll("\\s*", "");
    // parameters = methodParameters.split(",");
    // }

    Method method = null;
    try {

      Class<?> cls = null;
      Class<?>[] parameterClasses = null;
      cls = getClass(className);

      if(parameters.length != 0) {
        parameterClasses = new Class[parameters.length];
        for(int i = 0; i < parameters.length; i++) {
          parameterClasses[i] = getClass(parameters[i]);
        }
      }
      method = cls.getMethod(methodName, parameterClasses);

    } catch(ClassNotFoundException e) {
      // logger.info("Could not load class " + className + " for process " +
      // getProcess().getName()
      // + ". Check that the class is in the classpath.");
      // throw new RuntimeException(e);
    } catch(NoSuchMethodException e) {
      logger.info("Could not find methdod " + methodName + " in class " + className
          + ". Please check that you have given the correct parameters and try again.");
      throw new RuntimeException(e);
    } catch(SecurityException e) {
      logger.info("Some security exception happened. Don't know what this means :-)");
      throw new RuntimeException(e);
    }

    return method;
  }

  private Class<?> getClass(String name) throws ClassNotFoundException {
    if(byte.class.getName().equals(name))
      return byte.class;
    if(short.class.getName().equals(name))
      return short.class;
    if(int.class.getName().equals(name))
      return int.class;
    if(long.class.getName().equals(name))
      return long.class;
    if(float.class.getName().equals(name))
      return float.class;
    if(double.class.getName().equals(name))
      return double.class;
    if(boolean.class.getName().equals(name))
      return boolean.class;
    if(char.class.getName().equals(name))
      return char.class;

    if(name.charAt(0) == '[')
      throw new UnsupportedOperationException("Array parameters are not supported yet.");

    return Class.forName(name);
  }

  @Override
  public boolean isReady() {
    return true;
  }
}
