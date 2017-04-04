package com.ngCart.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ngCart.models.CommonVo;
import com.ngCart.models.Product;
import com.ngCart.util.GenerateUUID;

@Repository
public class OrderDao {

	@Autowired
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	
	public String createOrder(List<CommonVo> CommonVoList,String userId){
		
		String orderId = GenerateUUID.generateId();
		Date date = new Date();
		Timestamp currTime = new Timestamp(date.getTime());
		String createOrderQuery = "INSERT INTO orders values(?,?,?)";
		jdbcTemplate.update(createOrderQuery,orderId,userId,currTime);
		
		String insertProductsQuery = "INSERT INTO order_items values (?,?,?,?)";
		
		for(CommonVo commonVo : CommonVoList){
			String orderItemId = GenerateUUID.generateId();
			date = new Date();
			currTime = new Timestamp(date.getTime());
			jdbcTemplate.update(insertProductsQuery,orderItemId,orderId,commonVo.getProductId(),currTime);
		}
		
		return "order created successfully";
	}
}
