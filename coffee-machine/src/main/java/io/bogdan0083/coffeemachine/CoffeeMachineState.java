package io.bogdan0083.coffeemachine;

public class CoffeeMachineState {

    private int water = 400;
    private int milk = 540;
    private int beans = 120;
    private int cups = 9;
    private int money = 550;

    private CoffeeMachineAction chosenAction = CoffeeMachineAction.NONE;

    public CoffeeMachineState() {
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

    public CoffeeMachineAction getChosenAction() {
        return chosenAction;
    }

    public void setChosenAction(CoffeeMachineAction chosenAction) {
        this.chosenAction = chosenAction;
    }

    public void print() {
    }
}

