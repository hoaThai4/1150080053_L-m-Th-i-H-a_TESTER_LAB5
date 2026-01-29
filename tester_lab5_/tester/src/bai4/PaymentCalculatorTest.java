package bai4;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PaymentCalculatorTest {

    // CHILD: 0..17 => 50
    @Test
    public void childBoundaries() {
        assertEquals(50, PaymentCalculator.calculate(0, GenderGroup.CHILD));
        assertEquals(50, PaymentCalculator.calculate(17, GenderGroup.CHILD));
    }

    // MALE: 18-35 => 100, 36-50 => 120, 51-145 => 140
    @Test
    public void maleBoundaries() {
        assertEquals(100, PaymentCalculator.calculate(18, GenderGroup.MALE));
        assertEquals(100, PaymentCalculator.calculate(35, GenderGroup.MALE));
        assertEquals(120, PaymentCalculator.calculate(36, GenderGroup.MALE));
        assertEquals(120, PaymentCalculator.calculate(50, GenderGroup.MALE));
        assertEquals(140, PaymentCalculator.calculate(51, GenderGroup.MALE));
        assertEquals(140, PaymentCalculator.calculate(145, GenderGroup.MALE));
    }

    // FEMALE: 18-35 => 80, 36-50 => 110, 51-145 => 140
    @Test
    public void femaleBoundaries() {
        assertEquals(80, PaymentCalculator.calculate(18, GenderGroup.FEMALE));
        assertEquals(80, PaymentCalculator.calculate(35, GenderGroup.FEMALE));
        assertEquals(110, PaymentCalculator.calculate(36, GenderGroup.FEMALE));
        assertEquals(110, PaymentCalculator.calculate(50, GenderGroup.FEMALE));
        assertEquals(140, PaymentCalculator.calculate(51, GenderGroup.FEMALE));
        assertEquals(140, PaymentCalculator.calculate(145, GenderGroup.FEMALE));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidAgeNegative() {
        PaymentCalculator.calculate(-1, GenderGroup.MALE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidAgeTooHigh() {
        PaymentCalculator.calculate(146, GenderGroup.FEMALE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void missingGroup() {
        PaymentCalculator.calculate(20, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void childSelectedButAgeAdult() {
        PaymentCalculator.calculate(18, GenderGroup.CHILD);
    }

    @Test(expected = IllegalArgumentException.class)
    public void adultSelectedButAgeChild() {
        PaymentCalculator.calculate(17, GenderGroup.MALE);
    }
}
