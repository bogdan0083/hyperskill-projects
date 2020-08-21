package io.bogdan0083.coffeemachine.cli;

import io.bogdan0083.coffeemachine.*;
import io.bogdan0083.coffeemachine.coffee.Cappuccino;
import io.bogdan0083.coffeemachine.coffee.Coffee;
import io.bogdan0083.coffeemachine.coffee.Espresso;
import io.bogdan0083.coffeemachine.coffee.Latte;

import java.util.Optional;
import java.util.Scanner;

enum Status {RUNNING, EXIT}

// @NOTE should I implement runnable here?
public class CoffeeMachineCLI implements Runnable {
    private final Scanner scanner = new Scanner(System.in);
    private final CoffeeMachine coffeeMachine;
    private Status status = Status.RUNNING;

    public CoffeeMachineCLI(CoffeeMachine machine) {
        coffeeMachine = machine;
        this.run();
    }

    @Override
    public void run() {
        while (status != Status.EXIT) {
            CoffeeMachineAction action = scanAction();

            processAction(action);
            System.out.println();
        }
    }

    private CoffeeMachineAction scanAction() {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        String str = scanner.next();
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

    private void processAction(CoffeeMachineAction action) {
        switch (action) {
            case BUY:
                processPurchase();
                break;
            case FILL:
                processFilling();
                break;
            case TAKE:
                takeMoney();
                break;
            case REMAINING:
                printRemaining();
                break;
            default:
                status = Status.EXIT;
                break;
        }
    }

    private void processPurchase() {
        Optional<Coffee> coffee = this.scanCoffee();
        if (coffee.isPresent()) {
            Coffee c = coffee.get();
            try {
                coffeeMachine.buy(c);
                System.out.println("I have enough resources, making you a coffee!");
            } catch (CoffeePurchaseException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void takeMoney() {
        int money = coffeeMachine.getMoneyAmount();
        System.out.println("I gave you $" + money);
        coffeeMachine.takeMoney();
    }

    private void printRemaining() {
        CoffeeMachineState state = coffeeMachine.getState();
        System.out.println("The coffee machine has:");
        System.out.println(state.getWater() + " of water");
        System.out.println(state.getMilk() + " of milk");
        System.out.println(state.getBeans() + " of coffee beans");
        System.out.println(state.getCups() + " of disposable cups");
        System.out.println("$" + state.getMoney() + " of money");
        System.out.println();
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

    private void processFilling() {
        SupplyConsumablesScanner supplyScanner = new SupplyConsumablesScanner(this.scanner);
        Consumables consumables = supplyScanner.scan();
        coffeeMachine.fill(consumables);
    }

}
