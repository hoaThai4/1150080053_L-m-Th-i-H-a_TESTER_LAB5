package bai2;

public class JunitMessage {

    private String message;

    public JunitMessage(String message) {
        this.message = message;
    }

    public void printMessage() {
        System.out.println(message);
        int a = 1 / 0; // cố tình gây lỗi để test exception
    }

    public String printHiMessage() {
        return "Hi! " + message;
    }
}
