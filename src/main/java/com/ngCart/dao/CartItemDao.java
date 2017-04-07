package com.ngCart.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CartItemDao {

	@Autowired
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	
	public String removeFromCart(String cartItemId){
		jdbcTemplate =  new JdbcTemplate(dataSource);
		String query = "delete from cart_items where cart_item_id = ?";
		int result = jdbcTemplate.update(query, cartItemId);
		System.out.println("result form delete cart query"+result);
		return "success";
	}
}
