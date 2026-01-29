package bai2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SuiteTest2 {

    @Test
    public void testJunitHiMessage2() {
        String message = "Fpoly";
        JunitMessage junitMessage = new JunitMessage(message);
        assertEquals("Hi! " + message, junitMessage.printHiMessage());
    }
}
