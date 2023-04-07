package com.sherm.pfinance.services;

import com.sherm.pfinance.models.Categories;
import com.sherm.pfinance.repositories.CategoriesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    public CategoriesService(CategoriesRepository categorieRepository) {
        this.categoriesRepository = categorieRepository;
    }

    public List<Categories> findAll() {
        return categoriesRepository.findAll();
    }

    public Categories findById(Long id) {
        return categoriesRepository.findById(id).orElse(null);
    }

    public Categories save(Categories categorie) {
        return categoriesRepository.save(categorie);
    }

    public void deleteById(Long id) {
        categoriesRepository.deleteById(id);
    }
}
