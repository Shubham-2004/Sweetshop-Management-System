package com.sweetshop.sweetshop_backend.service;

import org.springframework.stereotype.Service;
import com.sweetshop.sweetshop_backend.model.Sweet;
import com.sweetshop.sweetshop_backend.repository.SweetRepository;

import java.util.List;

@Service
public class SweetService {

    private final SweetRepository sweetRepository;

    public SweetService(SweetRepository sweetRepository) {
        this.sweetRepository = sweetRepository;
    }

    public Sweet createSweet(Sweet sweet) {
        return sweetRepository.save(sweet);
    }

    public List<Sweet> getAllSweets() {
        return sweetRepository.findAll();
    }

    // New search methods
    public List<Sweet> searchSweetsByName(String name) {
        return sweetRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Sweet> searchSweetsByCategory(String category) {
        return sweetRepository.findByCategoryContainingIgnoreCase(category);
    }

    public List<Sweet> searchSweetsByPriceRange(double minPrice, double maxPrice) {
        return sweetRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Sweet> searchSweets(String name, String category, Double minPrice, Double maxPrice) {
        return sweetRepository.findByMultipleCriteria(name, category, minPrice, maxPrice);
    }
}