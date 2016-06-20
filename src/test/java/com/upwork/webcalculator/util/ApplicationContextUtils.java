package com.upwork.webcalculator.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Util class used only by test classes to access objects that live in Spring's application context.
 * Implements Spring's {@link ApplicationContextAware}
 * http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/
 * ApplicationContextAware.html
 * 
 * @author fbonecco
 *
 */
public class ApplicationContextUtils implements ApplicationContextAware {

  private static ApplicationContext applicationContext;

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  @Override
  public void setApplicationContext(final ApplicationContext applicationContext)
      throws BeansException {
    ApplicationContextUtils.applicationContext = applicationContext;
  }
}
