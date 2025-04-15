package com.example.fitness_center.service;

import com.example.fitness_center.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Optional<Product> save(Product product);

    Optional<Product> edit(Long id, String name, Long price, String weight, String taste, String type, String manufacturer, String image);

    void delete(Long id);

    void saveImage(MultipartFile image);

    Boolean buy(Long userId, Long creditCost);

}
