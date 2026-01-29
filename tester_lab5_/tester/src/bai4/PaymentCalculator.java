package bai4;

public final class PaymentCalculator {

    private PaymentCalculator() {
    }

    public static int calculate(int age, GenderGroup group) {
        if (group == null) {
            throw new IllegalArgumentException("Group must be selected");
        }
        if (age < 0 || age > 145) {
            throw new IllegalArgumentException("Age must be in range 0..145");
        }

        if (group == GenderGroup.CHILD) {
            if (age <= 17) {
                return 50;
            }
            throw new IllegalArgumentException("Child group only applies to age 0..17");
        }

        if (age <= 17) {
            throw new IllegalArgumentException("Age 0..17 must use Child group");
        }

        switch (group) {
            case MALE:
                if (age <= 35)
                    return 100;
                if (age <= 50)
                    return 120;
                return 140;
            case FEMALE:
                if (age <= 35)
                    return 80;
                if (age <= 50)
                    return 110;
                return 140;
            default:
                throw new IllegalArgumentException("Unsupported group: " + group);
        }
    }
}
