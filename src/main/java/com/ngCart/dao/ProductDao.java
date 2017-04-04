package com.ngCart.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ngCart.models.Product;
import com.ngCart.util.GenerateUUID;

@Repository
public class ProductDao {
	
	@Autowired
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;

	public List<Product> getAllProducts(){
		List<Product> productList = new ArrayList<Product>();
		String query = "select * from products";
		
		jdbcTemplate = new JdbcTemplate(dataSource);
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
		for (Map row : rows) {
			Product product = new Product();
			product.setProductId((String) row.get("product_id"));
			product.setProductName((String) row.get("product_name"));
			product.setProductDesc((String) row.get("product_desc"));
			product.setProductType((String) row.get("product_type"));
			product.setProductPrice((Float) row.get("product_price"));
			product.setCreatedTime((Timestamp) row.get("created_time"));
			productList.add(product);
		}
		return productList;
	}
	
	@Transactional
	public String addProduct(Product product){
		
		jdbcTemplate = new JdbcTemplate(dataSource);
		Date date = new Date();
		Timestamp currTime = new Timestamp(date.getTime());
		String productId = GenerateUUID.generateId();
		String query = "INSERT INTO products values(?,?,?,?,?,?)";
		jdbcTemplate.update(query, productId,product.getProductName(),product.getProductDesc(),product.getProductType(),product.getProductPrice(),currTime);
		
		return "product added successfully";
	}
}

