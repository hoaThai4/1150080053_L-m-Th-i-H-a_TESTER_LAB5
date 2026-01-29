package bai3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class JunitAnnotationsExample {

    private ArrayList<String> list;

    @BeforeClass
    public static void m1() {
        System.out.println("Using @BeforeClass , executed before all test cases");
    }

    @Before
    public void m2() {
        list = new ArrayList<>();
        System.out.println("Using @Before annotations , executed before each test case");
    }

    @AfterClass
    public static void m3() {
        System.out.println("Using @AfterClass , executed after all test cases");
    }

    @After
    public void m4() {
        list.clear();
        System.out.println("Using @After , executed after each test case");
    }

    @Test
    public void m5() {
        list.add("test");
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
    }

    @Ignore
    @Test
    public void m6() {
        System.out.println("Using @Ignore , this execution is ignored");
    }

    @Test(timeout = 10)
    public void m7() {
        // timeout demo: chạy rất nhanh, không bị timeout
        assertEquals(0, list.size());
    }

    @Test(expected = ArithmeticException.class)
    public void m8() {
        // expected demo
        int a = 1 / 0;
        System.out.println(a);
    }
}
