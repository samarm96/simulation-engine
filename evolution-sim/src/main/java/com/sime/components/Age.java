package com.sime.components;

public class Age implements Component {
    private int age;

    public Age(int age) {
        this.age = age;
    }

    public int age() {
        return this.age;
    }

    public void addAge() {
        this.age += 1;
    }

    public int getAge() {
        return this.age;
    }
}
