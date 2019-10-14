package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.model.Category;
import com.codegym.service.ProductService;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    private  ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories(){
        return categoryService.findAll();
    }

    @RequestMapping("/list")
    public ModelAndView getAllProduct(@RequestParam("s")Optional<String> s, Pageable pageable) {

        Page<Product> productList;
        if(s.isPresent()){
            productList = productService.findAllByNameContaining(s.get(),pageable);
        } else {
            productList = productService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products",productList);

        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createForm(){
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", new Product());

        return modelAndView;
    }

    @PostMapping("/save-product")
    public ModelAndView saveProduct(@ModelAttribute Product product) {
        productService.save(product);

        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product",new Product());
        modelAndView.addObject("message","Created product successful");
        return modelAndView;
    }

    @GetMapping("/read/{id}")
    public ModelAndView readDescription(@PathVariable Long id) {
        Product product = productService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/product/read");
        modelAndView.addObject("product",product);

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Product product = productService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/product/edit");
        modelAndView.addObject("product",product);

        return modelAndView;
    }

    @PostMapping("/update-product")
    public ModelAndView updateProduct(@ModelAttribute Product product) {
        productService.save(product);

        ModelAndView modelAndView = new ModelAndView("/product/edit");
        modelAndView.addObject("product", product);
        modelAndView.addObject("message","Updated product successful");

        return modelAndView;
    }

    @GetMapping("/remove/{id}")
    public ModelAndView showRemoveForm(@PathVariable Long id){
        Product product = productService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/product/remove");
        modelAndView.addObject("product",product);

        return modelAndView;
    }

    @PostMapping("/delete-product")
    public String removeProduct(@ModelAttribute ("product") Product product) {
        productService.remove(product.getId());
        return "redirect:list";
    }
}

