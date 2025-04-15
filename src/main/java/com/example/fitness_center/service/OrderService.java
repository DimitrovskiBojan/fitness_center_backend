package com.example.fitness_center.service;


import com.example.fitness_center.model.OrderEntity;

import java.util.List;

public interface OrderService {

    List<OrderEntity> findAll();

    OrderEntity save(Long productId, String address, String number);

    void delete(Long id);
}
