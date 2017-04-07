package com.ngCart.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngCart.dao.CartDao;
import com.ngCart.dao.CartItemDao;
import com.ngCart.models.CommonVo;
import com.ngCart.models.Product;
import com.ngCart.util.ApplicationUtil;

@Service
public class CartService {

	@Autowired
	CartDao cartDao;
	
	@Autowired
	CartItemDao cartItemDao;
	
	public String addToCart(Product product,String userId){
		return cartDao.addToCart(product,userId);
	}
	
	public String checkout(String userId){
		return cartDao.checkout(userId);
	}
	
	public List<CommonVo> displayCart(String userId){
		return cartDao.displayCart(userId);
	}

	public String removeFromCart(String cartItemId) {
		String response = cartItemDao.removeFromCart(cartItemId);
		if(response.equalsIgnoreCase("success")){
			return ApplicationUtil.composeSuccessJsonOuput("The product has been successfully removed").toString();
		}else{
			return ApplicationUtil.composeFailureJsonOuput("error while removing product from cart").toString();
		}
	}
}
