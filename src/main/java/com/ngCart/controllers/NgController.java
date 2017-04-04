package com.ngCart.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ngCart.models.Product;
import com.ngCart.models.User;
import com.ngCart.services.CartService;
import com.ngCart.services.ProductService;
import com.ngCart.services.UserService;

@RestController
public class NgController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CartService cartService;
	
	@RequestMapping(value = "test" , method=RequestMethod.GET)
	public String testApi(){
		return "this is testing";
	}

	@RequestMapping(value = "register" , method = RequestMethod.POST)
	public String registerUser(@RequestBody User user){
		return userService.registerUser(user);
	}
	
	@RequestMapping(value ="login" , method = RequestMethod.POST)
	public User login(@RequestBody User user){
		return userService.login(user);
	}
	
	@RequestMapping(value = "getAllProducts" , method=RequestMethod.GET)
	public List<Product> getAllProducts(){
		return productService.getAllProducts();
	}
	
	@RequestMapping(value = "addProduct" , method = RequestMethod.POST)
	public String addProduct(@RequestBody Product product){
		return productService.addProduct(product);
	}
	
	@RequestMapping(value = "addToCart/{userId}" , method = RequestMethod.POST)
	public String addToCart(@PathVariable String userId,@RequestBody Product product){
		return cartService.addToCart(product,userId);
	}
	
	@RequestMapping(value = "checkout/{userId}" , method = RequestMethod.GET)
	public String checkout(@PathVariable String userId){
		return cartService.checkout(userId);
	}
	
}
