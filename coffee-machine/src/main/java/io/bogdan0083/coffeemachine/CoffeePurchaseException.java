package io.bogdan0083.coffeemachine;

class CoffeePurchaseException extends Exception {
    public CoffeePurchaseException() {
        super();
    }

    public CoffeePurchaseException(String message) {
        super(message);
    }

    public CoffeePurchaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoffeePurchaseException(Throwable cause) {
        super(cause);
    }
}

