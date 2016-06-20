package com.upwork.webcalculator.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Implementation of {@link MathService}
 * 
 * @author fbonecco
 *
 */
@Service(value = "mathService")
public class MathServiceImpl implements MathService {

  @Cacheable("add")
  @Override
  public double add(List<Double> num) {
    Assert.notEmpty(num, "num can't be empty");
    // a fixed size array to workaround lambda limitations
    // around using final/ef. final variables
    double[] result = {0L};
    num.forEach(n -> result[0] = result[0] + n);

    return result[0];

    // another approach of this but
    // using an object of type AtomicLong:
    //
    // AtomicLong sum = new AtomicLong();
    // num.forEach(n -> {
    // sum.updateAndGet(i -> i + n);
    // });
    // return sum.get();
  }

  @Cacheable("subtract")
  @Override
  public double subtract(List<Double> num) {
    Assert.notEmpty(num, "num can't be empty");
    if (num.size() == 1) {
      if (num.get(0) == null) {
        throw new NullPointerException();
      }
      return num.get(0);
    }

    double[] result = {num.get(0)};
    num.subList(1, num.size()).forEach(n -> result[0] = result[0] - n);

    return result[0];
  }

  @Cacheable("multiply")
  @Override
  public double multiply(List<Double> num) {
    Assert.notEmpty(num, "num can't be empty");
    if (num.size() == 1) {
      if (num.get(0) == null) {
        throw new NullPointerException();
      }
      return num.get(0);
    }

    double[] result = {num.get(0)};
    num.subList(1, num.size()).forEach(n -> result[0] = result[0] * n);
    return result[0];
  }

  @Cacheable("divide")
  @Override
  public double divide(Double num1, Double num2) {
    Assert.notNull(num1, "dividend can't be null");
    Assert.notNull(num2, "divisor can't be null");
    Assert.isTrue(num2 != 0, "divisor can't be 0");

    return num1 / num2;
  }

}
