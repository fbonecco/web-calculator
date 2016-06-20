package com.upwork.webcalculator.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represent the result of a mathematical operation.
 * 
 * @author fbonecco
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OperationResult {

  @XmlElement(name = "type")
  private OperationType type;

  @XmlElement(name = "result")
  private double result;

  public OperationType getType() {
    return type;
  }

  public void setType(OperationType type) {
    this.type = type;
  }

  public double getResult() {
    return result;
  }

  public void setResult(double result) {
    this.result = result;
  }
}
