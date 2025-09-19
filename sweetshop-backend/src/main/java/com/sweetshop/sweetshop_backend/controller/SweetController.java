package com.sweetshop.sweetshop_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sweetshop.sweetshop_backend.model.Sweet;
import com.sweetshop.sweetshop_backend.service.SweetService;

@RestController
@RequestMapping("/api/sweets")
public class SweetController {

    private final SweetService sweetService;

    public SweetController(SweetService sweetService) {
        this.sweetService = sweetService;
    }

    @PostMapping
    public ResponseEntity<Sweet> createSweet(@RequestBody Sweet sweet) {
        try {
            Sweet createdSweet = sweetService.createSweet(sweet);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSweet);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Sweet>> getAllSweets() {
        try {
            List<Sweet> sweets = sweetService.getAllSweets();
            return ResponseEntity.ok(sweets);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/search")
    public ResponseEntity<List<Sweet>> searchSweets(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        
        try {
            if (name == null && category == null && minPrice == null && maxPrice == null) {
                return ResponseEntity.badRequest().build();
            }

            if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
                return ResponseEntity.badRequest().build();
            }

            List<Sweet> results;
            if (name != null && category == null && minPrice == null && maxPrice == null) {
                results = sweetService.searchSweetsByName(name);
            } else if (name == null && category != null && minPrice == null && maxPrice == null) {
                results = sweetService.searchSweetsByCategory(category);
            } else if (name == null && category == null && minPrice != null && maxPrice != null) {
                results = sweetService.searchSweetsByPriceRange(minPrice, maxPrice);
            } else {
                results = sweetService.searchSweets(name, category, minPrice, maxPrice);
            }

            return ResponseEntity.ok(results);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}