package com.example.fitness_center.web;

import com.example.fitness_center.model.Product;
import com.example.fitness_center.service.OrderService;
import com.example.fitness_center.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final OrderService orderService;

    public ProductController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = this.productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody Product product) {

//        if (product.getName() == null || price == null || weight == null || taste == null || type == null || manufacturer == null || image == null) {
//            return ResponseEntity.badRequest().body("All fields are required");
//        }

        // Save the image to the project directory
        String imagePath;
//        try {
//            imagePath = saveImage(image);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving image");
//        }

        // Save the product to the database
        productService.save(product);

        return new ResponseEntity<>("Product created", HttpStatus.CREATED);
    }

//    private String saveImage(MultipartFile image) throws IOException {
//        String directory = "path/to/your/directory"; // specify your directory here
//        String originalFilename = image.getOriginalFilename();
//        if (originalFilename == null) {
//            throw new IOException("Failed to get the original filename of the image");
//        }
//        String filePath = directory + File.separator + originalFilename;
//        Path path = Paths.get(filePath);
//        Files.createDirectories(path.getParent());
//        Files.write(path, image.getBytes());
//        return filePath;
//    }


    @PostMapping("/edit")
    public ResponseEntity<String> editProduct(  @RequestParam  String  name,
                                                 @RequestParam  Long    id,
                                                 @RequestParam  Long    price,
                                                 @RequestParam  String  weight,
                                                 @RequestParam  String  taste,
                                                 @RequestParam  String  type,
                                                 @RequestParam  String  manufacturer,
                                                 @RequestParam  String image){

        if (id == null ||name == null || price == null || weight == null || taste == null || type == null || manufacturer == null || image == null){
            return ResponseEntity.badRequest().body("All fields are required");
        }

        try{
            this.productService.edit(id,name,price,weight,taste,type,manufacturer,image);
            return new ResponseEntity<>("Product edited", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        try{
            this.productService.delete(id);
            return new ResponseEntity<>("Product Deleted", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/image")
    public ResponseEntity<String> savePicture(@RequestParam("image") MultipartFile image) {
        try {
            this.productService.saveImage(image);
            return new ResponseEntity<>("Image Added", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/buyProduct")
    public ResponseEntity<Map<String, String>> buyProduct(@RequestParam Long userId, @RequestParam Long creditCost, @RequestParam Long productId, @RequestParam String address, @RequestParam String number) {
        Map<String, String> response = new HashMap<>();

        try {
            boolean isSuccessful = this.productService.buy(userId, creditCost);

            if (isSuccessful) {
                this.orderService.save(productId,address,number);
                response.put("message", "Purchase successful");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("message", "Not enough credits");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            response.put("message", "An error occurred");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
