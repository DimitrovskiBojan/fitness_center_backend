package com.example.fitness_center.web;

import com.example.fitness_center.model.Client;
import com.example.fitness_center.model.MealPlan;
import com.example.fitness_center.service.MealPlanService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mealPlans")
public class MealPlanController {

    private final MealPlanService mealPlanService;
    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

    public MealPlanController(MealPlanService mealPlanService) {
        this.mealPlanService = mealPlanService;
        try {
            Files.createDirectories(fileStorageLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    @GetMapping
    public ResponseEntity<List<MealPlan>> getAllMealPlans() {
        List<MealPlan> mealPlans = this.mealPlanService.findAll();
        return new ResponseEntity<>(mealPlans, HttpStatus.OK);
    }

    @GetMapping("/getForTrainer/{id}")
    public ResponseEntity<List<MealPlan>> getAllMealPlansByCreated_By(@PathVariable Long id) {
        List<MealPlan> mealPlans = this.mealPlanService.findAllByCreated_by(id);
        return new ResponseEntity<>(mealPlans, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<MealPlan> createMealPlan(@RequestParam Long price,
                                                   @RequestParam String type,
                                                   @RequestParam String description,
                                                   @RequestParam Long created_by,
                                                   @RequestParam MultipartFile data) {

        // Save file to the uploads directory
        String fileName = saveFile(data);

        // Save meal plan with reference to file
        MealPlan mealPlan = this.mealPlanService.save(price, type, created_by, description, fileName);
        return new ResponseEntity<>(mealPlan, HttpStatus.CREATED);
    }

    @PostMapping("/buyPlan")
    public ResponseEntity<Map<String, String>> buyPlan(@RequestParam String planId, @RequestParam String clientUsername) {
        Map<String, String> response = new HashMap<>();
        try {
            if(!this.mealPlanService.canBuy(planId, clientUsername)){
                response.put("message", "No enough credits");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Long planIdLong = Long.parseLong(planId);
            this.mealPlanService.addPlanToClient(planIdLong, clientUsername);
            response.put("message", "Meal plan purchased");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NumberFormatException e) {
            response.put("error", "Invalid plan ID");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<MealPlan> edit(@RequestParam(required = false) Long id,
                                         @RequestParam String type,
                                         @RequestParam Long price,
                                         @RequestParam Long created_by,
                                         @RequestParam MultipartFile data,
                                         @RequestParam String description) {

        // Save file to the uploads directory
        String fileName = saveFile(data);

        // Update meal plan with reference to file
        MealPlan mealPlan = this.mealPlanService.edit(id, price, type, created_by, description, fileName);
        return new ResponseEntity<>(mealPlan, HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            this.mealPlanService.deleteById(id);
            return new ResponseEntity<>("Meal plan deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = "Failed to delete meal plan with ID: " + id + ". Error message: " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            // Sanitize the filename: Remove trailing spaces and encode the filename
            filename = UriUtils.decode(filename.trim(), StandardCharsets.UTF_8);

            // Construct the file path
            Path filePath = fileStorageLocation.resolve(filename).normalize();

            // Load the file as a resource
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                // Determine content type
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                // Return the resource with proper headers
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (InvalidPathException ex) {
            return ResponseEntity.badRequest().body(null); // Return a 400 error for invalid path
        }
    }

    private String saveFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + file.getOriginalFilename() + ". Please try again!", ex);
        }
    }


}
