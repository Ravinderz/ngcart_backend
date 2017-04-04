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

import com.ngCart.models.CommonVo;
import com.ngCart.util.GenerateUUID;

@Repository
public class OrderDao {

	@Autowired
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	
	public String createOrder(List<CommonVo> CommonVoList,String userId){
		jdbcTemplate = new JdbcTemplate(dataSource);
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
	
	public List<CommonVo> displayUserOrders(String userId){
		List<CommonVo> commonVoList = new ArrayList<CommonVo>();
		String query = "select oi.order_item_id,o.order_id,p.product_id,p.product_name,p.product_desc,p.product_type,p.product_price,o.user_id from products p,order_items oi ,orders o where oi.order_id = o.order_id and p.product_id = oi.product_id and  o.user_id = ?";
		
		jdbcTemplate = new JdbcTemplate(dataSource);
		Object[] inputs = new Object[] {userId};
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query,inputs);
		for (Map row : rows) {
			CommonVo commonVo = new CommonVo();
			commonVo.setProductId((String) row.get("product_id"));
			commonVo.setOrderItemId((String) row.get("order_item_id"));
			commonVo.setOrderId((String) row.get("order_id"));
			commonVo.setProductName((String) row.get("product_name"));
			commonVo.setProductDesc((String) row.get("product_desc"));
			commonVo.setProductType((String) row.get("product_type"));
			commonVo.setProductPrice((Float) row.get("product_price"));
			commonVo.setUserId((String) row.get("user_id"));
			commonVoList.add(commonVo);
		}
		
		
		return commonVoList;
	}
}
