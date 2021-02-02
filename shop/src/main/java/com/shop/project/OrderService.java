package com.shop.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
	
	@Autowired
	ProductRepository productRepository;
	@Autowired
	OrderRepository orderRepo;
	@Autowired
	OrderProductRepository orderProductRepo;
	@Autowired
	ShoppingCartService shoppingCartService;
	@Autowired
	UserService userService;
	
	public void makeOrder() {
		
		List<OrderProduct> orderProduct = shoppingCartService.getOrderProductList();
		
		Order order = new Order();
		order.setUserId(userService.getSessionUserId());
		order.setStatus("Waiting to pay");
		
		Order savedOrder = orderRepo.save(order);
		orderProduct.forEach(ordPro -> ordPro.setOrderId(savedOrder.getId()));
		
		try {
			orderProductRepo.saveAll(orderProduct);
		} catch(IllegalArgumentException e) {
			System.out.println("Some orderProduct is null");
		}
	}
	
	public boolean checkout() {
		List<OrderProduct> shoppingCartProducts = shoppingCartService.getOrderProductList();
		
		for(OrderProduct productsFromCart: shoppingCartProducts) {
			
			long productId = productsFromCart.getProductId();
			int quantity = productsFromCart.getQuantity();
			
			int quantityAvailable = productRepository.findById(productId).get().getQuantityAvailable();
			
			if(quantityAvailable < quantity) {
				return false;
			}
		}
		
		return true;
	}
	
	public double getOrderCost() {
		double cost = 0;
		
		List<OrderProduct> shoppingCartProducts = shoppingCartService.getOrderProductList();
		
		for(OrderProduct productsFromCart: shoppingCartProducts) {
			double price = productRepository.findById(productsFromCart.getProductId()).get().getPrice();
			int quantity = productsFromCart.getQuantity();
			
			cost += price*quantity;
		}
		return cost;
		
	}
	
}
