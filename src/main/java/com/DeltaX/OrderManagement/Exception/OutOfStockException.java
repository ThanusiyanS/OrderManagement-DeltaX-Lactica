package com.DeltaX.OrderManagement.Exception;

public class OutOfStockException extends Exception {
    public OutOfStockException() {
        super("Item is out of stock.");
    }

    public OutOfStockException(String message) {
        super(message);
    }
}
