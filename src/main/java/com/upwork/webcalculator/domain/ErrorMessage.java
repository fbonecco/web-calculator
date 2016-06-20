package com.upwork.webcalculator.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Models a error message that will be shown to final users.
 * 
 * @author fbonecco
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorMessage {

  @XmlElement(name = "status")
  private int status;

  @XmlElement(name = "message")
  private String message;

  public ErrorMessage() {}

  public ErrorMessage(int status, String message) {
    super();
    this.status = status;
    this.message = message;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
