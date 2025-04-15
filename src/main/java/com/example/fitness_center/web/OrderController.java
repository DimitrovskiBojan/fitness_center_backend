package com.example.fitness_center.web;

import com.example.fitness_center.model.OrderEntity;
import com.example.fitness_center.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/findAll")
    private ResponseEntity<List<OrderEntity>> findAllOrders(){
        List<OrderEntity> orders = this.orderService.findAll();

        return new ResponseEntity<>(orders, HttpStatus.OK);

    }

    @PostMapping("/createOrder")
    private ResponseEntity<Map<String, String>> create(@RequestParam Long productId, @RequestParam String address, @RequestParam String number){
        Map<String, String> response = new HashMap<>();
        try {
            this.orderService.save(productId,address,number);
            response.put("message", "Created");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (Error error){
            response.put("message", "Error");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id){
        try{
            this.orderService.delete(id);
            return new ResponseEntity<>("Order Deleted", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
