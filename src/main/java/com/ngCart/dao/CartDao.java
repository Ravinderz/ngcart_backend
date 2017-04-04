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

import com.ngCart.models.CommonVo;
import com.ngCart.models.Product;
import com.ngCart.util.GenerateUUID;

@Repository
public class CartDao {

	@Autowired
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	OrderDao orderDao;
	
	@Transactional
	public String addToCart(Product product,String userId){
		jdbcTemplate = new JdbcTemplate(dataSource);
		String query = "INSERT INTO cart_items values (?,?,?)";
		String getCartIdQuery = "SELECT cart_id from cart where userId = ?";
		Object[] inputs = new Object[] {userId};
		String cartId = jdbcTemplate.queryForObject(getCartIdQuery, inputs,String.class);

		String cartItemId = GenerateUUID.generateId();
		Date date = new Date();
		Timestamp currTime = new Timestamp(date.getTime());
		jdbcTemplate.update(query,cartItemId,cartId,product.getProductId(),currTime);
		return "product added to cart successfully";
	}
	
	@Transactional
	public String checkout(String userId){
		List<CommonVo> commonVoList = new ArrayList<CommonVo>();
		String query = "select ci.product_id,c.user_id from cart_items ci , cart c where ci.cart_id = c.cart_id and c.user_id = ?";
		
		jdbcTemplate = new JdbcTemplate(dataSource);
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
		for (Map row : rows) {
			CommonVo commonVo = new CommonVo();
			commonVo.setProductId((String) row.get("product_id"));
			commonVo.setUserId((String) row.get("user_id"));
			commonVoList.add(commonVo);
		}
		
		orderDao.createOrder(commonVoList, userId);
		
		String deleteCartItemQuery = "delete from cart_items where cart_id = (select cart_id from cart where user_id = ?)";
		jdbcTemplate.update(deleteCartItemQuery, userId);

		return "done";
	}
}
