package bai4;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunnerBai4 {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(PaymentCalculatorTest.class);

        System.out.println("run tests: " + result.getRunCount());
        System.out.println("failed tests: " + result.getFailureCount());
        System.out.println("ignored tests: " + result.getIgnoreCount());
        System.out.println("success: " + result.wasSuccessful());

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }
}
