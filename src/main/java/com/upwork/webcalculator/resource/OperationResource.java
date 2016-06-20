package com.upwork.webcalculator.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.upwork.webcalculator.domain.OperationResult;
import com.upwork.webcalculator.domain.OperationType;
import com.upwork.webcalculator.service.MathService;

/**
 * Central resource handler. The basic four operations supported by this application -add, subtract,
 * multiply and divide- are mapped to this resource.
 * 
 * @author fbonecco
 *
 */
@Component
@Path("/")
public class OperationResource {

  /**
   * An instance of an object implementing {@link MathService}
   */
  @Autowired
  @Qualifier("mathService")
  private MathService mathService;

  /**
   * A method to handle the operations add, subtract, and multiply. The {@link Path} annotation
   * allows to define whether a request to one of this resources is either valid or not. As these
   * three operations handle the same number/type of parameters, they were grouped under the same
   * handler.
   */
  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("{operation: add|subtract|multiply}/{num1}/{num2}{p:/?}{num3: ([\\.\\-0-9]+)?}")
  public OperationResult doCommon(@Context UriInfo ui, @PathParam("num1") double num1,
      @PathParam("num2") double num2, @PathParam("num3") double num3) {
    // the expression defined in the Path annotation let us mark the third parameter as optional (as
    // these resources can handle up to three numbers in the same call.

    List<Double> nums = new ArrayList<Double>();
    nums.addAll(Arrays.asList(num1, num2));
    // if three numbers are supplied,
    // then we have 4 path segments: add, num1, num2, num3
    if (ui.getPathSegments().size() >= 4) {
      nums.add(num3);
    }
    double result = 0D;
    // the first segment determines the operation type.
    String operation = ui.getPathSegments().get(0).getPath();
    OperationResult operationResult = new OperationResult();
    OperationType operationType = null;
    if (OperationType.ADD.name().equalsIgnoreCase(operation)) {
      result = mathService.add(nums);
      operationType = OperationType.ADD;
    } else if (OperationType.SUBTRACT.name().equalsIgnoreCase(operation)) {
      result = mathService.subtract(nums);
      operationType = OperationType.SUBTRACT;
    } else {
      result = mathService.multiply(nums);
      operationType = OperationType.MULTIPLY;
    }

    operationResult.setType(operationType);
    operationResult.setResult(result);
    return operationResult;
  }

  /**
   * This method handles division operations. Note that this resource support exactly two numbers
   * per call.
   */
  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("divide/{num1}/{num2}")
  public OperationResult divide(@Context UriInfo ui, @PathParam("num1") double num1,
      @PathParam("num2") double num2) {
    // Note: this logic might also be merged with the method doCommon but it'd probably ending up in
    // a dirty an hard-to-read code.

    // we can't divide by zero so we treat this as a "resource not found error". despite this can be
    // handle in a different fashion such as returning a HTTP 400, I prefer to keep the API as
    // simple as possible. After all, we are dealing with resources.
    if (num2 == 0) {
      throw new NotFoundException();
    }

    double result = mathService.divide(num1, num2);
    OperationResult operationResult = new OperationResult();
    operationResult.setResult(result);
    operationResult.setType(OperationType.DIVIDE);

    return operationResult;
  }
}
