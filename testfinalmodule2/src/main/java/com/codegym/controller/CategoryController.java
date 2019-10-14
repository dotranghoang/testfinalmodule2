package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.model.Category;
import com.codegym.service.ProductService;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @RequestMapping("/")
    public ModelAndView home(){
        return listCategory();
    }

    @GetMapping("/category")
    public ModelAndView listCategory(){
        Iterable<Category> categories = categoryService.findAll();

        ModelAndView modelAndView = new ModelAndView("/category/list");
        modelAndView.addObject("categories",categories);

        return modelAndView;
    }

    @GetMapping("/create-category")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category",new Category());

        return modelAndView;
    }

    @PostMapping("/save-category")
    public ModelAndView saveCategory(@ModelAttribute("category") Category category){
        categoryService.save(category);

        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category",new Category());
        modelAndView.addObject("message","Created category successful");

        return modelAndView;
    }

    @GetMapping("/edit-category/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Category category = categoryService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/category/edit");
        modelAndView.addObject("category",category);

        return modelAndView;
    }

    @PostMapping("/update-category")
    public ModelAndView updateCategory(@ModelAttribute("category") Category category) {
        categoryService.save(category);

        ModelAndView modelAndView = new ModelAndView("/category/edit");
        modelAndView.addObject("category",category);
        modelAndView.addObject("message","Updated category successful");

        return modelAndView;
    }

    @GetMapping("/remove-category/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Category category = categoryService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/category/remove");
        modelAndView.addObject("category",category);

        return modelAndView;
    }

    @PostMapping("/remove-category")
    public ModelAndView removeCategory(@ModelAttribute("category") Category category){
        categoryService.remove(category.getId());

        return listCategory();
    }

    @GetMapping("/view-category/{id}")
    public ModelAndView viewMore(@PathVariable ("id") Long id){
        Category category = categoryService.findById(id);

        Iterable<Product> products = productService.findAllByCategory(category);

        ModelAndView modelAndView = new ModelAndView("/category/view");
        modelAndView.addObject("category",category);
        modelAndView.addObject("products",products);

        return modelAndView;
    }
}

