package com.vainolo.phd.opm.interpreter;

import com.google.inject.AbstractModule;

public class InterpreterModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(OPMObjectInstanceValueAnalyzer.class).to(OPMObjectInstanceValueAnalyzerImpl.class);
  }
}
