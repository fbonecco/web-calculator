package com.upwork.webcalculator.application;

import org.glassfish.jersey.server.ResourceConfig;

import com.upwork.webcalculator.exception.NotFoundExceptionMapper;
import com.upwork.webcalculator.exception.ParamExceptionMapper;
import com.upwork.webcalculator.resource.OperationResource;


/**
 * Resource configuration for the web-calculator application. Registers the resource handler
 * {@link OperationResource} and two custom exception mappers.
 * 
 * @author fbonecco
 */
public class WebCalculatorApplication extends ResourceConfig {
  public WebCalculatorApplication() {
    register(OperationResource.class);
    register(NotFoundExceptionMapper.class);
    register(ParamExceptionMapper.class);
  }
}
