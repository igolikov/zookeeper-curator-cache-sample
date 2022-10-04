package org.example;

public class SomeConfiguration {
    private final String value1;

    private final String value2;

    private SomeConfiguration() {
        this.value1 = null;
        this.value2 = null;
    }

    public SomeConfiguration(String value1, String value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public String getValue1() {
        return value1;
    }

    public String getValue2() {
        return value2;
    }

    @Override
    public String toString() {
        return "SomeConfiguration{" + "value1='" + value1 + '\'' + ", value2='" + value2 + '\'' + '}';
    }
}
