package com.DeltaX.OrderManagement.Exception;

public class OrderNotFoundException extends Exception{
    public OrderNotFoundException(){
        super("Order Not Found");
    }
    public OrderNotFoundException(String message){super(message);}
}
