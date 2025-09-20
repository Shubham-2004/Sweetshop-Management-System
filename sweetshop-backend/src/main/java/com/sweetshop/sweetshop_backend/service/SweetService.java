package com.sweetshop.sweetshop_backend.service;

import org.springframework.stereotype.Service;
import com.sweetshop.sweetshop_backend.model.Sweet;
import com.sweetshop.sweetshop_backend.repository.SweetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SweetService {

    private final SweetRepository sweetRepository;

    public SweetService(SweetRepository sweetRepository) {
        this.sweetRepository = sweetRepository;
    }

    // Existing methods
    public Sweet createSweet(Sweet sweet) {
        return sweetRepository.save(sweet);
    }

    public List<Sweet> getAllSweets() {
        return sweetRepository.findAll();
    }

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

    public Sweet updateSweet(String id, Sweet sweet) {
        if (sweetRepository.existsById(id)) {
            sweet.setId(id);
            return sweetRepository.save(sweet);
        }
        return null;
    }

    public Optional<Sweet> getSweetById(String id) {
        return sweetRepository.findById(id);
    }

    public boolean deleteSweet(String id) {
        try {
            if (sweetRepository.existsById(id)) {
                sweetRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error deleting sweet with ID " + id + ": " + e.getMessage());
            return false;
        }
    }
}