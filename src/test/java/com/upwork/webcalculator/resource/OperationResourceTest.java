package com.upwork.webcalculator.resource;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.upwork.webcalculator.application.WebCalculatorApplication;
import com.upwork.webcalculator.domain.ErrorMessage;
import com.upwork.webcalculator.domain.OperationResult;
import com.upwork.webcalculator.domain.OperationType;

/**
 * Integration test for the class {@link OperationResource}
 * 
 * This class introduces several test methods that check the different end-points exposed by this
 * API.
 * 
 * @author fbonecco
 *
 */
public class OperationResourceTest extends JerseyTest {

  /**
   * Load an application context used only for testing purposes.
   */
  @Override
  protected Application configure() {
    return new WebCalculatorApplication().property("contextConfigLocation",
        "classpath:spring/test-applicationContext.xml");
  }

  /**
   * Tests the end-point add by passing two values. As this represents a valid operation, a 200
   * response plus a JSON object with the result of the operation are expected.
   */
  @Test
  public void testAdd1() {
    // configures the client that will be used to query the API
    Builder request = prepareForRequest("/add/1/2");

    // checks the response's status
    Response response = request.get();
    assertEquals(200, response.getStatus());

    // check the information contained in the response
    OperationResult actualResult = response.readEntity(new GenericType<OperationResult>() {});
    assertEquals(OperationType.ADD, actualResult.getType());
    assertEquals(3D, actualResult.getResult(), 0D);
  }

  /**
   * Tests the end-point add by passing only one value. The API does not support operations based on
   * a single number
   */
  @Test
  public void testAdd2() {
    Builder request = prepareForRequest("/add/1");

    Response response = request.get();
    // checks the response is a HTTP 404
    assert404Response(response);
  }

  /**
   * Tests the end-point add by passing a non-numeric values.
   */
  @Test
  public void testAdd3() {
    Builder request = prepareForRequest("/add/1/a/2");

    Response response = request.get();
    assert404Response(response);
  }

  /**
   * Tests the end-point add by passing both integer and decimal values.
   */
  @Test
  public void testAdd4() {
    Builder request = prepareForRequest("/add/1/20.5/2");

    Response response = request.get();
    assertEquals(200, response.getStatus());

    OperationResult actualResult = response.readEntity(new GenericType<OperationResult>() {});
    assertEquals(OperationType.ADD, actualResult.getType());
    assertEquals(23.5D, actualResult.getResult(), 0D);
  }

  /**
   * Tests the end-point subtract by passing two values.
   */
  @Test
  public void testSubtract1() {
    Builder request = prepareForRequest("/subtract/1/-2");

    Response response = request.get();
    assertEquals(200, response.getStatus());

    OperationResult actualResult = response.readEntity(new GenericType<OperationResult>() {});
    assertEquals(OperationType.SUBTRACT, actualResult.getType());
    assertEquals(3D, actualResult.getResult(), 0D);
  }

  /**
   * Tests the end-point subtract by passing only one value. The API does not support operations
   * based on a single number
   */
  @Test
  public void testSubtract2() {
    Builder request = prepareForRequest("/subtract/-1");

    Response response = request.get();
    assert404Response(response);
  }

  /**
   * Tests the end-point subtract by passing 0 values. The API does not support operations based on
   * a single number
   */
  @Test
  public void testSubtract3() {
    Builder request = prepareForRequest("/subtract/");

    Response response = request.get();
    assert404Response(response);
  }

  /**
   * Tests the end-point multiply by passing two values.
   */
  @Test
  public void testMultiply1() {
    Builder request = prepareForRequest("/multiply/10/-0.9");

    Response response = request.get();
    assertEquals(200, response.getStatus());

    OperationResult actualResult = response.readEntity(new GenericType<OperationResult>() {});
    assertEquals(OperationType.MULTIPLY, actualResult.getType());
    assertEquals(-9D, actualResult.getResult(), 0D);
  }

  /**
   * Tests the end-point multiply by passing only one value. The API does not support operations
   * based on a single number
   */
  @Test
  public void testMultiply2() {
    Builder request = prepareForRequest("/multiply/0");

    Response response = request.get();
    assert404Response(response);
  }

  /**
   * Tests the end-point multiply by passing three values.
   */
  @Test
  public void testMultiply3() {
    Builder request = prepareForRequest("/multiply/-1/2/0.5");

    Response response = request.get();
    assertEquals(200, response.getStatus());

    OperationResult actualResult = response.readEntity(new GenericType<OperationResult>() {});
    assertEquals(OperationType.MULTIPLY, actualResult.getType());
    assertEquals(-1D, actualResult.getResult(), 0D);
  }

  /**
   * Tests the end-point divide by passing two values.
   */
  @Test
  public void testDivide1() {
    Builder request = prepareForRequest("/divide/10.5/3");

    Response response = request.get();
    assertEquals(200, response.getStatus());

    OperationResult actualResult = response.readEntity(new GenericType<OperationResult>() {});
    assertEquals(OperationType.DIVIDE, actualResult.getType());
    assertEquals(3.5D, actualResult.getResult(), 0D);
  }

  /**
   * Tests the end-point divide by passing 0 as the divisor. We treat this scenario as it we are
   * looking for a non-existing resource.
   */
  @Test
  public void testDivide2() {
    Builder request = prepareForRequest("/divide/2/0");

    Response response = request.get();
    assert404Response(response);
  }

  /**
   * Tests the end-point divide by passing three values which is not supported by this end-point.
   */
  @Test
  public void testDivide3() {
    Builder request = prepareForRequest("/divide/2/0/4");

    Response response = request.get();
    assert404Response(response);
  }

  /**
   * Helper method to parse and validate 404 responses
   */
  private void assert404Response(Response response) {
    assertEquals(404, response.getStatus());
    ErrorMessage errorMessage = response.readEntity(new GenericType<ErrorMessage>() {});
    assertEquals("Resource not found.", errorMessage.getMessage());
    assertEquals(404, errorMessage.getStatus());
  }


  /**
   * Configures the client for accessing the API
   */
  private Builder prepareForRequest(String path) {
    ClientConfig clientConfig = new ClientConfig();
    clientConfig.register(JacksonFeature.class);

    WebTarget target = target(path);
    Builder request = target.request();
    request.header("Content-type", MediaType.APPLICATION_JSON);
    return request;
  }
}
