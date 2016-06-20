package com.upwork.webcalculator.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.springframework.aop.target.HotSwappableTargetSource;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.cache.support.SimpleCacheManager;

import com.upwork.webcalculator.application.WebCalculatorApplication;
import com.upwork.webcalculator.util.ApplicationContextUtils;


/**
 * This class main's intent is to test the cache mechanism used by this application. As we leverage
 * caching to Spring OOTB cache, it's worth to check it works as expected.
 * 
 * @author fbonecco
 *
 */
public class OperationResourceCachingTest extends JerseyTest {

  /**
   * Load an application context used only for testing purposes.
   */
  @Override
  protected Application configure() {
    return new WebCalculatorApplication().property("contextConfigLocation",
        "classpath:spring/test-applicationContext.xml");
  }

  /**
   * Tests that several calls to the an end-point with the same values are not recalculated but read
   * from a cache.
   */
  @Test
  public void testAdd() {
    // As mocking Spring's cache objects can be tricky, I created a spy class that keep track of the
    // calls made to its methods (see the implementatio below).
    ConcurrentMapCacheSpy cache = (ConcurrentMapCacheSpy) prepareCaches().getCache("add");

    List<Double> key = Arrays.asList(5.0, 2.0, 3.0);

    Builder request = prepareForRequest("/add/5/2/3");
    request.get();
    request.get();
    request.get();

    // we access the cache object directly to check our object is stored
    assertTrue(cache.getNativeCache().containsKey(key));
    Double value = (Double) cache.getNativeCache().get(key);
    assertEquals(10D, value, 0D);
    // we performed 3 requests so the cache should be checked 3 times
    assertEquals(3, cache.getHits());
    // the subsequent calls to the cache returned the object
    assertEquals(2, cache.getReadFromCache());
  }

  /**
   * Tests that several calls to the end-point subtract with the same values are not recalculated
   * but instead read from a cache.
   */
  @Test
  public void testSubtract() {
    ConcurrentMapCacheSpy cache = (ConcurrentMapCacheSpy) prepareCaches().getCache("subtract");

    List<Double> key = Arrays.asList(10.0D, -5.5D);

    Builder request = prepareForRequest("/subtract/10/-5.5");
    request.get();
    request.get();

    assertTrue(cache.getNativeCache().containsKey(key));
    Double value = (Double) cache.getNativeCache().get(key);
    assertEquals(15.5, value, 0D);
    assertEquals(2, cache.getHits());
    assertEquals(1, cache.getReadFromCache());
  }

  /**
   * Tests that several calls to the end-point multiply with the same values are not recalculated
   * but instead read from a cache.
   */
  @Test
  public void testMultiply() {
    ConcurrentMapCacheSpy cache = (ConcurrentMapCacheSpy) prepareCaches().getCache("multiply");

    List<Double> key = Arrays.asList(100.0D, -5.5D);

    Builder request = prepareForRequest("/multiply/100/-5.5");
    request.get();
    request.get();
    request.get();
    request.get();
    request.get();

    assertTrue(cache.getNativeCache().containsKey(key));
    Double value = (Double) cache.getNativeCache().get(key);
    assertEquals(-550D, value, 0D);
    assertEquals(5, cache.getHits());
    assertEquals(4, cache.getReadFromCache());
  }

  /**
   * Tests that several calls to the end-point divide with the same values are not recalculated but
   * instead read from a cache.
   */
  @Test
  public void testDivide() {
    ConcurrentMapCacheSpy cache = (ConcurrentMapCacheSpy) prepareCaches().getCache("divide");

    SimpleKey key = new SimpleKey(new Object[] {40D, 2D});

    Builder request = prepareForRequest("/divide/40/2");
    request.get();
    request.get();

    assertTrue(cache.getNativeCache().containsKey(key));
    Double value = (Double) cache.getNativeCache().get(key);
    assertEquals(20D, value, 0D);
    assertEquals(2, cache.getHits());
    assertEquals(1, cache.getReadFromCache());
  }

  /**
   * Sets up the spy caches. A cache manager is created with four caches to store the result of the
   * operations. It ends calling the method swapMock which replaces the cache manager that is
   * already defined in the context with the custom object.
   */
  private SimpleCacheManager prepareCaches() {
    SimpleCacheManager cacheManager = new SimpleCacheManager();

    cacheManager.setCaches(Arrays.asList(new ConcurrentMapCacheSpy("add"),
        new ConcurrentMapCacheSpy("subtract"), new ConcurrentMapCacheSpy("multiply"),
        new ConcurrentMapCacheSpy("divide")));
    cacheManager.initializeCaches();

    swapMock("cacheTargetSource", cacheManager);

    return cacheManager;
  }

  /**
   * Find and replace a bean based on the parameters. This is needed to inject our objects within
   * Spring's context after it's initialized.
   */
  private void swapMock(String beanId, Object object) {
    HotSwappableTargetSource source =
        (HotSwappableTargetSource) ApplicationContextUtils.getApplicationContext().getBean(beanId);
    source.swap(object);
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

  /**
   * Spy for the class {@link ConcurrentMapCache} The intention of this class is to spy the calls to
   * the methods lookup and get. This is purely for testing purposes.
   * 
   * @author fbonecco
   *
   */
  class ConcurrentMapCacheSpy extends ConcurrentMapCache {
    private int hits = 0;
    private int readFromCache = 0;

    public ConcurrentMapCacheSpy(String name) {
      super(name);
    }

    /**
     * This method is called when looking for a an object by its key.
     */
    protected Object lookup(Object key) {
      hits++;
      return super.lookup(key);
    }

    /**
     * This methods is called when getting an object from the cache. If the object is already stored
     * in the cache, we count this as a hit.
     */
    public Cache.ValueWrapper get(Object key) {

      if (getNativeCache().containsKey(key)) {
        readFromCache++;
      }
      return super.get(key);
    }

    public int getHits() {
      return hits;
    }

    public int getReadFromCache() {
      return readFromCache;
    }
  }
}
