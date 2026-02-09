package com.sime.components;

public class Energy implements Component {
    
    private double amount;

    public Energy(double amount) {
        this.amount = amount;

    }

    public void addEnergy(double delta) {
        this.amount += delta;
    }

    public double amount() {
        return this.amount;
    }

    public double getAmount() {
        return this.amount;
    }
}
