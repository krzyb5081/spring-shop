package com.shop.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {
	
	@Autowired
	private ShoppingCart shoppingCart;
	@Autowired
	private ProductRepository productRepository;
	
	public void setOrderProductByProductId(long productId, int quantity) {
		
		List<OrderProduct> orderProductList = shoppingCart.getList();
		
		for(OrderProduct orderProductFromList : orderProductList) {
			if(orderProductFromList.getProductId()==productId) {
				orderProductList.remove(orderProductFromList);
				break;
			}
		}
		
		OrderProduct orderProduct = new OrderProduct();
		orderProduct.setProductId(productId);
		orderProduct.setQuantity(quantity);
		
		orderProductList.add(orderProduct);
		shoppingCart.setList(orderProductList);
		
	}
	
	public void removeOrderProductByProductId(long productId) {
		
		List<OrderProduct> orderProductList = shoppingCart.getList();
		
		for(OrderProduct orderProductFromList: orderProductList) {
			if(orderProductFromList.getProductId()==productId) {
				orderProductList.remove(orderProductFromList);
				break;
			}
		}
		shoppingCart.setList(orderProductList);
		
	}
	
	public List<OrderProduct> getOrderProductList() {
		return shoppingCart.getList();
	}
	
	public Map<Long,Product> getCurrentProductMap() {
		
		List<OrderProduct> shoppingCartProducts = shoppingCart.getList();
		Map<Long,Product> productMap = new HashMap<Long,Product>();
		
		for(OrderProduct productsFromCart: shoppingCartProducts) {
			
			long productId = productsFromCart.getProductId();
			Product product = productRepository.findById(productId).get();
			
			productMap.put(productId, product);
		}
		return productMap;
		
	}
	
}
