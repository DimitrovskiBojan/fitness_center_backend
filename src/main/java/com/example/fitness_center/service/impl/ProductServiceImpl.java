package com.example.fitness_center.service.impl;

import com.example.fitness_center.model.Client;
import com.example.fitness_center.model.MyUser;
import com.example.fitness_center.model.OrderEntity;
import com.example.fitness_center.model.Product;
import com.example.fitness_center.model.exceptions.ClientNotFoundException;
import com.example.fitness_center.model.exceptions.ProductNotFoundException;
import com.example.fitness_center.repository.ClientRepository;
import com.example.fitness_center.repository.MyUserRepository;
import com.example.fitness_center.repository.OrderRepository;
import com.example.fitness_center.repository.ProductRepository;
import com.example.fitness_center.service.ProductService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final MyUserRepository myUserRepository;

    private final OrderRepository orderRepository;

    public ProductServiceImpl(ProductRepository productRepository, ClientRepository clientRepository, MyUserRepository myUserRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.myUserRepository = myUserRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Product> findAll() {
        return this.productRepository.findAllByActiveTrue();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return this.productRepository.findById(id);
    }

    @Override
    public Optional<Product> save(Product product) {

        String imageUrl = "assets/images/" + product.getImage();
        product.setImage(imageUrl);

        Product productToSave = new Product(product.getName(),product.getPrice(),product.getWeight(),product.getTaste(),product.getType(),product.getManufacturer(),product.getImage());

        this.productRepository.save(product);
        return Optional.of(product);
    }

    @Override
    public Optional<Product> edit(Long id, String name, Long price, String weight, String taste, String type, String manufacturer, String  image) {



        Product product = this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(name);
        product.setType(type);
        product.setWeight(weight);
        product.setImage(image);
        product.setTaste(taste);
        product.setManufacturer(manufacturer);
        product.setPrice(price);

        this.productRepository.save(product);

        return Optional.of(product);
    }

    @Override
    public void delete(Long id) {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        List<OrderEntity> orders = this.orderRepository.findAll();

        boolean flag = false;

        for (OrderEntity order : orders) {
            if (order.getProduct().equals(product)) {
                flag = true;
                break;
            }
        }

        if (flag) {
            product.setActive(false);
            this.productRepository.save(product);
        } else {
            this.productRepository.deleteById(id);
        }
    }

    @Override
    public void saveImage(MultipartFile image) {
        try {
            // Get the original filename
            String originalFilename = image.getOriginalFilename();

            // Create the path to save the file
            Path path = Paths.get("C:\\Users\\Bojan\\AngularProjects\\fitness_center\\src\\assets\\images" + File.separator + originalFilename);

            // Save the file to the specified path
            Files.write(path, image.getBytes());

            System.out.println("ZACUVANOOOO");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean buy(Long userId, Long creditCost) {

        MyUser myUser = this.myUserRepository.findMyUserById(userId).orElseThrow(() -> new ClientNotFoundException(userId));
        Client client = this.clientRepository.findMyUserByUsername(myUser.getUsername()).orElseThrow(() -> new UsernameNotFoundException(myUser.getUsername()));

        if(client.getCredits() < creditCost){
            return false;
        }
        else {
            Integer clientCredits = client.getCredits();
            client.setCredits((int) (clientCredits - creditCost));
            this.clientRepository.save(client);
            return true;
        }
    }


}
