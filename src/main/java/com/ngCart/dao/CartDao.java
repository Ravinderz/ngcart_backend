package com.ngCart.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ngCart.models.CommonVo;
import com.ngCart.models.Product;
import com.ngCart.util.ApplicationUtil;
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
		String query = "INSERT INTO cart_items values (?,?,?,?)";
		String getCartIdQuery = "SELECT cart_id from cart where user_id = ?";
		Object[] inputs = new Object[] {userId};
		String cartId = jdbcTemplate.queryForObject(getCartIdQuery, inputs,String.class);

		String cartItemId = GenerateUUID.generateId();
		
		Timestamp currTime = ApplicationUtil.getCurrentTimeStamp();
		jdbcTemplate.update(query,cartItemId,cartId,product.getProductId(),currTime);
		return ApplicationUtil.composeSuccessJsonOuput("product added to cart successfully").toString();
	}
	
	@Transactional
	public List<CommonVo> displayCart(String userId){
		List<CommonVo> commonVoList = new ArrayList<CommonVo>();
		String query = "select ci.cart_item_id,p.product_id,p.product_name,p.product_desc,p.product_image,p.product_type,p.product_price,c.user_id from products p,cart_items ci , cart c where ci.cart_id = c.cart_id and p.product_id = ci.product_id and  c.user_id = ?";
		
		jdbcTemplate = new JdbcTemplate(dataSource);
		Object[] inputs = new Object[] {userId};
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query,inputs);
		for (Map row : rows) {
			CommonVo commonVo = new CommonVo();
			commonVo.setProductId((String) row.get("product_id"));
			commonVo.setCartItemId((String) row.get("cart_item_id"));
			commonVo.setProductName((String) row.get("product_name"));
			commonVo.setProductDesc((String) row.get("product_desc"));
			commonVo.setProductType((String) row.get("product_type"));
			commonVo.setProductPrice((Float) row.get("product_price"));
			commonVo.setProductImg((String) row.get("product_image"));
			commonVo.setUserId((String) row.get("user_id"));
			commonVoList.add(commonVo);
		}
		
		return commonVoList;
	}
	
	@Transactional
	public String checkout(String userId){
		List<CommonVo> commonVoList = new ArrayList<CommonVo>();
		String query = "select ci.product_id,c.user_id from cart_items ci , cart c where ci.cart_id = c.cart_id and c.user_id = ?";
		
		jdbcTemplate = new JdbcTemplate(dataSource);
		Object[] inputs = new Object[] {userId};
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query,inputs);
		for (Map row : rows) {
			CommonVo commonVo = new CommonVo();
			commonVo.setProductId((String) row.get("product_id"));
			commonVo.setUserId((String) row.get("user_id"));
			commonVoList.add(commonVo);
		}
		
		String orderId = orderDao.createOrder(commonVoList, userId);
		
		String deleteCartItemQuery = "delete from cart_items where cart_id = (select cart_id from cart where user_id = ?)";
		jdbcTemplate.update(deleteCartItemQuery, userId);

		return ApplicationUtil.composeSuccessJsonOuput(orderId).toString();
	}
	
	
}
