package com.upwork.webcalculator.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.ParamException;

import com.upwork.webcalculator.domain.ErrorMessage;

/**
 * Custom {@link ExceptionMapper} that handles exceptions that are thrown when a parameter can't be
 * read from a URI path. Instead of returning plain HTML, this provides a more descriptive (and user
 * friendly) message.
 * 
 * @author fbonecco
 *
 */
@Provider
public class ParamExceptionMapper implements ExceptionMapper<ParamException> {

  @Override
  public Response toResponse(ParamException exception) {
    ErrorMessage errorMessage = new ErrorMessage(404, "Resource not found.");
    return Response.status(404).entity(errorMessage).type(MediaType.APPLICATION_JSON).build();
  }

}
