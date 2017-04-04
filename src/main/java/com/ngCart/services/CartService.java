package com.ngCart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngCart.dao.CartDao;
import com.ngCart.models.Product;

@Service
public class CartService {

	@Autowired
	CartDao cartDao;
	
	public String addToCart(Product product,String userId){
		return cartDao.addToCart(product,userId);
	}
	
	public String checkout(String userId){
		return cartDao.checkout(userId);
	}
}
