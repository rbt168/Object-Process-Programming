/*******************************************************************************
 * Copyright (c) 2012 Arieh 'Vainolo' Bibliowicz
 * You can use this code for educational purposes. For any other uses
 * please contact me: vainolo@gmail.com
 *******************************************************************************/
package com.vainolo.phd.opm.interpreter;

import com.vainolo.phd.opm.interpreter.builtin.OPMAddProcessInstance;
import com.vainolo.phd.opm.interpreter.builtin.OPMAssignProcessInstance;
import com.vainolo.phd.opm.interpreter.builtin.OPMConceptualProcess;
import com.vainolo.phd.opm.interpreter.builtin.OPMInputProcessInstance;
import com.vainolo.phd.opm.interpreter.builtin.OPMOutputProcessInstance;
import com.vainolo.phd.opm.interpreter.builtin.OPMSleepProcessInstance;
import com.vainolo.phd.opm.model.OPMObjectProcessDiagram;
import com.vainolo.phd.opm.model.OPMProcess;

public class OPMExecutableInstanceFactory {
  public static OPMExecutableInstance createExecutableInstance(final OPMObjectProcessDiagram opd) {
    return null;
  }

  public static OPMExecutableInstance createExecutableInstance(final OPMProcess process) {
    OPMExecutableInstance processInstance = null;
    switch(process.getKind()) {
    case BUILT_IN:
      processInstance = createBuildInProcess(process);
      break;
    case COMPOUND:
      processInstance = new OPMCompoundProcessInstance(process);
      break;
    case CONCEPTUAL:
      processInstance = new OPMConceptualProcess(process);
      break;
    case JAVA:
      processInstance = new OPMJavaProcessInstance(process);
      break;
    }

    return processInstance;
  }

  private static OPMExecutableInstance createBuildInProcess(final OPMProcess process) {
    OPMExecutableInstance processInstance;

    if(process.getName().equals("Input")) {
      processInstance = new OPMInputProcessInstance(process);
    } else if(process.getName().equals("Output")) {
      processInstance = new OPMOutputProcessInstance(process);
    } else if(process.getName().equals("Add") || process.getName().equals("+")) {
      processInstance = new OPMAddProcessInstance(process);
    } else if(process.getName().equals("Sleep")) {
      processInstance = new OPMSleepProcessInstance(process);
    } else if(process.getName().equals("Assign") || process.getName().equals("=")) {
      processInstance = new OPMAssignProcessInstance(process);
    } else {
      throw new IllegalStateException("Tried to create unexistent build-in process " + process.getName());
    }

    return processInstance;

  }
}