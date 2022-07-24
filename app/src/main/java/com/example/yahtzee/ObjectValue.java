package com.example.yahtzee;

public class ObjectValue {
    public ObjectValue(String name) {
        this.name = name;
        this.value = 0;
    }

    String name;
    Integer value;

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
