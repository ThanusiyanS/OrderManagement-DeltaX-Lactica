package com.DeltaX.OrderManagement.Repository;

import com.DeltaX.OrderManagement.Entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order,String> {
}
