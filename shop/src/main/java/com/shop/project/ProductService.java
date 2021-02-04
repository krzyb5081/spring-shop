package com.shop.project;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	public Product getProductById(long productId) {
		return productRepository.findById(productId).get();
	}
	
	public void decreaseProductQuantity(long productId, int decreaseBy) {
		Product product = getProductById(productId);
		product.setQuantityAvailable(product.getQuantityAvailable()-decreaseBy);
		productRepository.save(product);
	}
	
	public Map<Long,Product> getProductMap(){
		Map<Long,Product> productMap = new HashMap<Long,Product>();
		
		productRepository.findAll().forEach(product->{
			productMap.put(product.getId(), product);
		});
		return productMap;
	}
}
