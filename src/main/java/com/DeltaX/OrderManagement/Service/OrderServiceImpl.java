package com.DeltaX.OrderManagement.Service;

import com.DeltaX.OrderManagement.Entity.Order;
import com.DeltaX.OrderManagement.Entity.OrderProduct;
import com.DeltaX.OrderManagement.Entity.Product;
import com.DeltaX.OrderManagement.Entity.User;
import com.DeltaX.OrderManagement.Exception.OrderNotFoundException;
import com.DeltaX.OrderManagement.Exception.OutOfStockException;
import com.DeltaX.OrderManagement.Repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    private static final String USER_SERVICE_URL = "http://localhost:8080/users/";
    private static final String PRODUCT_SERVICE_URL = "http://localhost:8081/products/";

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public Order createOrder(Order order) throws OutOfStockException {
        Long userId = order.getUserId();
        User user = restTemplate.getForObject(USER_SERVICE_URL+userId,User.class);
        order.setUserName(user.getFirstName());

        List<OrderProduct> orderProducts = order.getOrderProducts();
        double totalAmount=0;
        List<Product> productList=new ArrayList<>();
        for (OrderProduct item : orderProducts) {
            Product product = restTemplate.getForObject(PRODUCT_SERVICE_URL+"get/"+item.getId(),Product.class);
            Long orderCount = item.getOrderCount();
            Long stockQuantity = product.getStockQuantity();
            if(!(orderCount<stockQuantity)){
                throw new OutOfStockException();
            }else {
                totalAmount+= orderCount* product.getPrice();
                product.setStockQuantity(stockQuantity - orderCount);
                productList.add(product);
            }
        }
        for (Product item: productList) {
            restTemplate.postForObject(PRODUCT_SERVICE_URL+"update/"+item.getId(),item,String.class);
        }
        order.setTotalAmount(totalAmount);
        order.setOrderTime();
        return orderRepository.save(order);
    }
    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Order getAnOrder(String orderId) throws OrderNotFoundException {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public Order updateOrderCount(String orderId, Order newOrder) throws OrderNotFoundException, OutOfStockException {
        if(orderRepository.existsById(orderId)){
            Order old = getAnOrder(orderId);

            List<OrderProduct> items = newOrder.getOrderProducts();
            List<OrderProduct> oldItems = old.getOrderProducts();
            double totalAmount=0;
            for(int i=0;i<items.size();i++){
                Long oldCount = oldItems.get(i).getOrderCount();
                Long newCount = items.get(i).getOrderCount();
                if((items.get(i).getId().equals(oldItems.get(i).getId())) && (newCount != oldCount)){
                    Product product = restTemplate.getForObject(PRODUCT_SERVICE_URL+"get/"+oldItems.get(i).getId(), Product.class);
                    if(product.getStockQuantity()+oldCount-newCount >0){
                        product.setStockQuantity(product.getStockQuantity()+oldCount-newCount);
                        totalAmount+= newCount* product.getPrice();
                        oldItems.get(i).setOrderCount(newCount);
                        restTemplate.postForObject(PRODUCT_SERVICE_URL+"update/"+oldItems.get(i).getId(),product,String.class);
                    }else {
                        throw new OutOfStockException();
                    }

                }
            }
            old.setOrderTime();
            old.setTotalAmount(totalAmount);
            return orderRepository.save(old);
        }else{
            throw new OrderNotFoundException();
        }
    }

    @Override
    public String deleteOrder(String orderId) throws OrderNotFoundException {
        if(orderRepository.existsById(orderId)){
            orderRepository.deleteById(orderId);
            return "Order Deleted";
        }
        else {
            throw new OrderNotFoundException();
        }
    }
}



