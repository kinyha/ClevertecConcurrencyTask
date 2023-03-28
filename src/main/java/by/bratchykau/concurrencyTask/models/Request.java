package by.bratchykau.concurrencyTask.models;

public class Request {
    private int value;

    public Request(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Request{" +
                "value=" + value +
                '}';
    }
}
