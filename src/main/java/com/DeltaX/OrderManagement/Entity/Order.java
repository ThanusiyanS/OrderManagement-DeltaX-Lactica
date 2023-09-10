package com.DeltaX.OrderManagement.Entity;

import org.hibernate.annotations.DialectOverride;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Document
public class Order {
    @Id
    private String id;
    private Long userId;
    private Date orderTime;
    private List<OrderProduct> orderProducts;
    private String userName;
    private double totalAmount = 0;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime() {
        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDateStr = dateFormat.format(currentDate);
        Date formattedDate;
        try {
            formattedDate = dateFormat.parse(formattedDateStr);
        } catch (java.text.ParseException e) {
            // Handle parsing exceptions if needed
            formattedDate = null;
        }
        this.orderTime = formattedDate;

    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }
}






