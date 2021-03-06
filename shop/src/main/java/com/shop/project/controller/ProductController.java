package com.shop.project.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.shop.project.model.Product;
import com.shop.project.repository.ProductRepository;

@Controller
public class ProductController {
	
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("/addProduct")
	public String addProductForm(Product product, Model model) {
		model.addAttribute("product", product);
		return "AddProduct";
	}
	
	@PostMapping("/addProduct")
	public String addProduct(@Valid Product product, BindingResult results) {
		if(results.hasErrors()) {
			return "AddProduct";
		}
		
		productRepository.save(product);
		
		return "index";
	}
	
	@GetMapping("/show")
	public String showProducts(Model model) {
		
		List<Product> productList = new ArrayList<Product>();
		productRepository.findAll().forEach(product -> productList.add(product));
		
		model.addAttribute("productList", productList);
		return "ShowProducts";
	}
}
