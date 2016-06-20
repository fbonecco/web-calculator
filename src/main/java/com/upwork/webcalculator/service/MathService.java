package com.upwork.webcalculator.service;

import java.util.List;

/**
 * Declares methods for performing the basic numeric operations: add, subtract, multiply and divide.
 * 
 * @author fbonecco
 *
 */
public interface MathService {

  /**
   * <p>
   * Returns the sum of the numbers contained in a {@link List}. If only one number is provided, the
   * method simply returns that number.
   * </p>
   * <p>
   * Implementations of this method must ensure that the following exceptions are thrown:
   * </p>
   * <ul>
   * <li>{@link IllegalArgumentException} - if the passed {@link List} is empty</li>
   * <li>{@link NullPointerException} - if one of the items contained in the passed {@link List} is
   * null</li>
   * </ul>
   * 
   * @param num a {@link List} of numbers
   * @return double the result of the operation
   */
  public double add(List<Double> num);

  /**
   * <p>
   * Returns the subtraction of the numbers contained in a {@link List}. If only one number is
   * provided, the method simply returns that number.
   * </p>
   *
   * <p>
   * Implementations of this method must ensure that the following exceptions are thrown:
   * </p>
   * <ul>
   * <li>{@link IllegalArgumentException} - if the passed {@link List} is empty</li>
   * <li>{@link NullPointerException} - if one of the items contained in the passed {@link List} is
   * null</li>
   * </ul>
   * 
   * @param num a {@link List} of numbers
   * @return double the result of the operation
   */
  public double subtract(List<Double> num);


  /**
   * <p>
   * Returns the result of multiplying the numbers contained in a {@link List}. If only one number
   * is provided, the method simply returns that number.
   * </p>
   * <p>
   * Implementations of this method must ensure that the following exceptions are thrown:
   * </p>
   * <ul>
   * <li>{@link IllegalArgumentException} - if the passed {@link List} is empty</li>
   * <li>{@link NullPointerException} - if one of the items contained in the passed {@link List} is
   * null</li>
   * </ul>
   * 
   * @param num a {@link List} of numbers
   * @return double the result of the operation
   */
  public double multiply(List<Double> num);


  /**
   * <p>
   * Returns the result of dividing the numbers passed as arguments. Values can't be null.
   * </p>
   * <p>
   * Implementations of this method must ensure that the following exceptions are thrown:
   * </p>
   * <ul>
   * <li>{@link IllegalArgumentException} - passed values are null or if the divisor is 0</li>
   * <li>{@link NullPointerException} - if one of the items contained in the passed {@link List} is
   * null</li>
   * </ul>
   * 
   * @param num1 dividend
   * @param num2 divisor
   * @return double the result of the operation
   */
  public double divide(Double num1, Double num2);

}
