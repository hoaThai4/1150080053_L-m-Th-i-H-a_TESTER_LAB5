package bai1;

import static org.junit.Assert.*;
import org.junit.*;

public class MathFuncTest {

    private MathFunc math;

    // Test Fixture
    @Before
    public void init() {
        math = new MathFunc();
    }

    @After
    public void tearDown() {
        math = null;
    }

    // Test số lần gọi hàm
    @Test
    public void calls() {
        assertEquals(0, math.getCalls());

        math.factorial(1);
        assertEquals(1, math.getCalls());

        math.factorial(1);
        assertEquals(2, math.getCalls());
    }

    // Test hàm factorial
    @Test
    public void factorial() {
        assertTrue(math.factorial(0) == 1);
        assertTrue(math.factorial(1) == 1);
        assertTrue(math.factorial(5) == 120);
    }

    // Test factorial với số âm
    @Test(expected = IllegalArgumentException.class)
    public void factorialNegative() {
        math.factorial(-1);
    }

    // Bỏ qua test case này
    @Ignore
    @Test
    public void todo() {
        assertTrue(math.plus(1, 1) == 3);
    }
}
