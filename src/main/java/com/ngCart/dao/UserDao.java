package com.ngCart.dao;

import java.sql.Timestamp;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ngCart.models.User;
import com.ngCart.models.UserRowMapper;
import com.ngCart.util.ApplicationUtil;
import com.ngCart.util.GenerateHash;
import com.ngCart.util.GenerateUUID;

@Repository
public class UserDao {
	
	@Autowired
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;

	@Transactional
	public String registerUser(User user){
		
		jdbcTemplate = new JdbcTemplate(dataSource);
		
		
		Timestamp currTime = ApplicationUtil.getCurrentTimeStamp();
		String userId = GenerateUUID.generateId();
		String cartId = GenerateUUID.generateId();
		String insertUserQuery = "INSERT INTO users (user_id,first_name,last_name,email,password,created_time) values (?,?,?,?,?,?)";
		String insertCartQuery = "INSERT INTO cart(cart_id,user_id,created_time) values (?,?,?)";
		jdbcTemplate.update(insertUserQuery, userId,user.getFirstName(),user.getLastName(),user.getEmail(),GenerateHash.getHash(user.getPassword()),currTime);
		jdbcTemplate.update(insertCartQuery, cartId,userId,currTime);
		
		
		return ApplicationUtil.composeSuccessJsonOuput("User has been registered successfully").toString();
	}
	
	public User login(User user){
		jdbcTemplate = new JdbcTemplate(dataSource);
		String getUserQuery = "SELECT * from users where email = ?";
		User userFromDb = (User)jdbcTemplate.queryForObject(getUserQuery, new Object[] { user.getEmail() }, new UserRowMapper());
		if(GenerateHash.getHash(user.getPassword()).equals(userFromDb.getPassword())){
			return userFromDb;
		}else{
			userFromDb = null;
			return userFromDb;
		}
	}
}
