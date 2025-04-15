package com.example.fitness_center.service.impl;

import com.example.fitness_center.model.OrderEntity;
import com.example.fitness_center.model.Product;
import com.example.fitness_center.model.exceptions.ProductNotFoundException;
import com.example.fitness_center.repository.OrderRepository;
import com.example.fitness_center.repository.ProductRepository;
import com.example.fitness_center.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<OrderEntity> findAll() {
        return this.orderRepository.findAll();
    }

    @Override
    public OrderEntity save(Long productId, String address, String number) {

        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));

        OrderEntity order = new OrderEntity(address,number,product);
        return this.orderRepository.save(order);
    }

    @Override
    public void delete(Long id) {
        OrderEntity order = this.orderRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        this.orderRepository.deleteById(id);
    }
}
