package com.sherm.pfinance.controllers;

import com.sherm.pfinance.models.Categories;
import com.sherm.pfinance.services.CategoriesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    private final CategoriesService categorieService;

    public CategoriesController(CategoriesService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping
    public List<Categories> getAllCategories() {
        return categorieService.findAll();
    }

    @GetMapping("/{id}")
    public Categories getCategorieById(@PathVariable Long id) {
        return categorieService.findById(id);
    }

    @PostMapping
    public Categories createCategorie(@RequestBody Categories categorie) {
        return categorieService.save(categorie);
    }
/*
    @PutMapping("/{id}")
    public ResponseEntity<Categories> updateCategorie(@PathVariable Long id, @RequestBody Categories categorieDetails) {
        Optional<Categories> optionalCategorie = Optional.ofNullable(categorieService.findById(id));

        if (optionalCategorie.isPresent()) {
            Categories categorie = optionalCategorie.get();
            categorie.setName(categorieDetails.getName());
            categorie.setBalance(categorieDetails.getBalance());
            categorie.setCurrency(categorieDetails.getCurrency());
	    categorie.setUser(categorieDetails.getUser());
            Categories updatedCategorie = categorieService.save(categorie);
            return ResponseEntity.ok(updatedCategorie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
*/
    @DeleteMapping("/{id}")
    public void deleteCategorie(@PathVariable Long id) {
        categorieService.deleteById(id);
    }
}
