package io.bogdan0083.coffeemachine;

import io.bogdan0083.coffeemachine.coffee.*;

import java.util.Optional;
import java.util.Scanner;

public class CoffeeMachine {
    private final CoffeeMachineState state;
    private final Scanner scanner;

    private CoffeeMachine() {
        this.state = new CoffeeMachineState();
        this.scanner = new Scanner(System.in);
    }

    @SuppressWarnings("StringOperationCanBeSimplified")
    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        while (coffeeMachine.state.getChosenAction() != CoffeeMachineAction.EXIT) {
            CoffeeMachineAction action = coffeeMachine.scanAction();
            coffeeMachine.processAction(action);
            System.out.println("");
        }
    }

    private void processAction(CoffeeMachineAction action) {
        switch (action) {
            case BUY:
                this.processPurchase();
                break;
            case FILL:
                this.processFilling();
                break;
            case TAKE:
                this.takeMoney();
                break;
            case REMAINING:
                this.state.print();
                break;
            default:
                this.state.setChosenAction(CoffeeMachineAction.EXIT);
                break;
        }
    }

    private void takeMoney() {
        System.out.println("I gave you $" + this.state.getMoney());
        this.state.setMoney(0);
    }

    private void processFilling() {
        SupplyConsumablesScanner supplyScanner = new SupplyConsumablesScanner(this.scanner);
        Consumables consumables = supplyScanner.scan();
        state.setWater(state.getWater() + consumables.water);
        state.setMilk(state.getMilk() + consumables.milk);
        state.setBeans(state.getBeans() + consumables.coffeeBeans);
        state.setCups(state.getCups() + consumables.cups);
    }

    private void processPurchase() {
        Optional<Coffee> coffee = this.scanCoffee();
        if (coffee.isPresent()) {
            Coffee c = coffee.get();
            try {
                checkPurchaseStatus(state, c);
                state.setWater(state.getWater() - c.getWater());
                state.setMilk(state.getMilk() - c.getMilk());
                state.setBeans(state.getBeans() - c.getBeans());
                state.setMoney(state.getMoney() + c.getPrice());
                state.setCups(state.getCups() - 1);
                System.out.println("I have enough resources, making you a coffee!");
            } catch (CoffeePurchaseException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private CoffeeMachineAction scanAction() {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        String str = this.scanner.next();
        CoffeeMachineAction action;

        switch (str) {
            case "buy":
                action = CoffeeMachineAction.BUY;
                break;
            case "fill":
                action = CoffeeMachineAction.FILL;
                break;
            case "take":
                action = CoffeeMachineAction.TAKE;
                break;
            case "remaining":
                action = CoffeeMachineAction.REMAINING;
                break;
            case "exit":
            default:
                action = CoffeeMachineAction.EXIT;
                break;
        }

        return action;
    }

    private Optional<Coffee> scanCoffee() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String str = this.scanner.next();
        Coffee coffee;
        switch (str) {
            case "1":
                coffee = new Espresso();
                break;
            case "2":
                coffee = new Latte();
                break;
            case "3":
                coffee = new Cappuccino();
                break;
            case "back":
            default:
                coffee = null;
        }

        return Optional.ofNullable(coffee);
    }

    private void checkPurchaseStatus(CoffeeMachineState state, Coffee coffee) throws CoffeePurchaseException {
        int waterAfterPurchase = state.getWater() - coffee.getWater();
        int milkAfterPurchase = state.getMilk() - coffee.getMilk();
        int beansAfterPurchase = state.getBeans() - coffee.getBeans();
        int cupsAfterPurchase = state.getCups() - 1;

        if (waterAfterPurchase < 0) {
            throw new CoffeePurchaseException("Sorry, not enough water!");
        }

        if (milkAfterPurchase < 0) {
            throw new CoffeePurchaseException("Sorry, not enough milk!");
        }

        if (beansAfterPurchase < 0) {
            throw new CoffeePurchaseException("Sorry, not enough coffee beans!");
        }

        if (cupsAfterPurchase < 0) {
            throw new CoffeePurchaseException("Sorry, not enough disposable cups!");
        }
    }
}
