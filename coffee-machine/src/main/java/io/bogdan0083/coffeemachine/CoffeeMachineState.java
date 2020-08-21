package io.bogdan0083.coffeemachine;

import java.util.Objects;

public class CoffeeMachineState implements Cloneable {

    private int water;
    private int milk;
    private int beans;
    private int cups;
    private int money;

    public CoffeeMachineState(int water, int milk, int beans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cups = cups;
        this.money = money;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getMilk() {
        return milk;
    }

    public void setMilk(int milk) {
        this.milk = milk;
    }

    public int getBeans() {
        return beans;
    }

    public void setBeans(int beans) {
        this.beans = beans;
    }

    public int getCups() {
        return cups;
    }

    public void setCups(int cups) {
        this.cups = cups;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoffeeMachineState state = (CoffeeMachineState) o;
        return water == state.water &&
                milk == state.milk &&
                beans == state.beans &&
                cups == state.cups &&
                money == state.money;
    }

    @Override
    public int hashCode() {
        return Objects.hash(water, milk, beans, cups, money);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

