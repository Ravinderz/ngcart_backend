package com.ngCart.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngCart.dao.ProductDao;
import com.ngCart.models.Product;

@Service
public class ProductService {

	@Autowired
	ProductDao productDao;
	
	public List<Product> getAllProducts(){
		return productDao.getAllProducts();
	}
	
	public String addProduct(Product product){
		return productDao.addProduct(product);
	}
}
