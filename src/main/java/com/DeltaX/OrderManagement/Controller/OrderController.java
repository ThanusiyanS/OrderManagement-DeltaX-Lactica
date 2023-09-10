package com.DeltaX.OrderManagement.Controller;

import com.DeltaX.OrderManagement.Entity.Order;
import com.DeltaX.OrderManagement.Exception.OrderNotFoundException;
import com.DeltaX.OrderManagement.Exception.OutOfStockException;
import com.DeltaX.OrderManagement.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public Order addOrder(@RequestBody Order order) throws OutOfStockException {
        return orderService.createOrder(order);
    }

    @GetMapping("/getAll")
    public List<Order> getAllOrders(){
        return orderService.getAllOrder();
    }

    @GetMapping("/get/{orderId}")
    public Order getAnOrder(@PathVariable String orderId) throws OrderNotFoundException {
        return orderService.getAnOrder(orderId);
    }

    @PostMapping("/update/{orderId}")
    public String updateOrder(@PathVariable String orderId,@RequestBody Order order) throws OrderNotFoundException, OutOfStockException {
        orderService.updateOrderCount(orderId,order);
        return "Order Updated";
    }

    @DeleteMapping("/delete/{orderId}")
    public String deleteOrder(@PathVariable String orderId) throws OrderNotFoundException {
        return orderService.deleteOrder(orderId);
    }



}
