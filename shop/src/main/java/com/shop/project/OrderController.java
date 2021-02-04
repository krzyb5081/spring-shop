package com.shop.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

	@Autowired
	OrderService orderService;
	@Autowired
	UserService userService;
	
	@GetMapping("/showOrders")
	public String showOrders(Model model) {
		if(userService.getSessionUserName()==null) {
			return "redirect:/login";
		}
		model.addAttribute("orderEntrySet", orderService.getMyOrders().entrySet());
		model.addAttribute("productMap", orderService.getProductMap());
		model.addAttribute("userMap", orderService.getUserMap());
		return "showOrders";
	}
}
