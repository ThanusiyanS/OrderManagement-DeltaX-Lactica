package com.DeltaX.OrderManagement.Service;

import com.DeltaX.OrderManagement.Entity.Order;
import com.DeltaX.OrderManagement.Exception.OrderNotFoundException;
import com.DeltaX.OrderManagement.Exception.OutOfStockException;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderService {
    public Order createOrder(Order order) throws OutOfStockException;
    public List<Order> getAllOrder();
    public Order getAnOrder(String orderId) throws OrderNotFoundException;
    public Order updateOrderCount(String orderId,Order order) throws OrderNotFoundException, OutOfStockException;
    public String deleteOrder(String orderId) throws OrderNotFoundException;


}
