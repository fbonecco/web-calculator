package com.upwork.webcalculator.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for the class {@link MathServiceImpl}
 * 
 * @author fbonecco
 *
 */
public class MathServiceImplTest {

  /**
   * Class under testing
   */
  private MathService target;

  @Before
  public void setUp() {
    target = new MathServiceImpl();
  }

  /**
   * Tests the method add when only one value is passed. The result of this call should be getting
   * the same value.
   */
  @Test
  public void testAdd1() {
    double actual = target.add(Arrays.asList(100D));
    double expected = 100L;

    assertEquals(expected, actual, 0D);
  }

  /**
   * Tests the sum of two values.
   */
  @Test
  public void testAdd2() {
    double actual = target.add(Arrays.asList(3D, 5D));
    double expected = 8D;

    assertEquals(expected, actual, 0D);
  }

  /**
   * Tests the sum of several values (integers, decimal and negative ones) included in the list
   */
  @Test
  public void testAdd3() {
    double actual = target.add(Arrays.asList(2.5D, 3D, 5D, -1D));
    double expected = 9.5D;

    assertEquals(expected, actual, 0D);
  }

  /**
   * Checks an exception is thrown when an empty list is passed.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAdd4() {
    target.add(new ArrayList<Double>());
  }

  /**
   * Checks an exception is thrown if at most one of the values contained in the list is null.
   */
  @Test(expected = NullPointerException.class)
  public void testAdd5() {
    List<Double> nums = Arrays.asList(2.5D, null);
    target.add(nums);
  }

  /**
   * Checks an exception is thrown if the value contained in the list is null.
   */
  @Test(expected = NullPointerException.class)
  public void testAdd6() {
    List<Double> nums = new ArrayList<Double>();
    nums.add(null);
    target.add(nums);
  }

  /**
   * Tests the method subtract when only one value is passed. The result of this call should be
   * getting the same value.
   */
  @Test
  public void testSubtract1() {
    double actual = target.subtract(Arrays.asList(-1D));
    double expected = -1D;

    assertEquals(expected, actual, 0D);
  }

  /**
   * Tests the method subtract when only one value is passed. The result of this call should be
   * getting the same value.
   */
  @Test
  public void testSubtract2() {
    double actual = target.subtract(Arrays.asList(1D));
    double expected = 1D;

    assertEquals(expected, actual, 0D);
  }

  /**
   * Tests the subtraction of several values included in the list
   */
  @Test
  public void testSubtract3() {
    double actual = target.subtract(Arrays.asList(-1D, 2D, -4D));
    double expected = 1D;

    assertEquals(expected, actual, 0D);
  }

  /**
   * Tests the subtraction of several values included in the list
   */
  @Test
  public void testSubtract4() {
    double actual = target.subtract(Arrays.asList(10D, -2D, -4D));
    double expected = 16D;

    assertEquals(expected, actual, 0D);
  }

  /**
   * Checks an exception is thrown when an empty list is passed.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSubtract5() {
    target.subtract(new ArrayList<Double>());
  }

  /**
   * Checks an exception is thrown if at most one of the values contained in the list is null.
   */
  @Test(expected = NullPointerException.class)
  public void testSubtract6() {
    List<Double> nums = Arrays.asList(2.5D, null);
    target.subtract(nums);
  }

  /**
   * Checks an exception is thrown if the value contained in the list is null.
   */
  @Test(expected = NullPointerException.class)
  public void testSubtract7() {
    List<Double> nums = new ArrayList<Double>();
    nums.add(null);
    target.subtract(nums);
  }


  /**
   * Tests the method multiply when only one value is passed. The result of this call should be
   * getting the same value.
   */
  @Test
  public void testMultiply1() {
    double actual = target.multiply(Arrays.asList(10D));
    double expected = 10D;

    assertEquals(expected, actual, 0D);
  }

  /**
   * Tests the multiplication of two values.
   */
  @Test
  public void testMultiply2() {
    double actual = target.multiply(Arrays.asList(-10D, 10D));
    double expected = -100D;

    assertEquals(expected, actual, 0D);
  }

  /**
   * Checks an exception is thrown when an empty list is passed.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMultiply3() {
    target.multiply(new ArrayList<Double>());
  }

  /**
   * Checks an exception is thrown if at most one of the values contained in the list is null.
   */
  @Test(expected = NullPointerException.class)
  public void testMultiply4() {
    List<Double> nums = Arrays.asList(2.5D, null);
    target.multiply(nums);
  }

  /**
   * Checks an exception is thrown if the value contained in the list is null.
   */
  @Test(expected = NullPointerException.class)
  public void testMultiply5() {
    List<Double> nums = new ArrayList<Double>();
    nums.add(null);
    target.multiply(nums);
  }

  /**
   * Tests the result of dividing two values
   */
  @Test
  public void testDivide1() {
    double actual = target.divide(0D, -10D);
    double expected = 0D;

    assertEquals(expected, actual, 0D);
  }

  /**
   * Tests the result of dividing two values
   */
  @Test
  public void testDivide2() {
    double actual = target.divide(-10D, 10D);
    double expected = -1D;

    assertEquals(expected, actual, 0D);
  }

  /**
   * Checks an exception is thrown if the divisor is 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDivide3() {
    target.divide(-10D, 0D);
  }

  /**
   * Checks an exception is thrown if one of the values passed is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDivide4() {
    target.divide(null, 2D);
  }
}
