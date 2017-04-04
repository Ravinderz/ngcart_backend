package com.ngCart.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngCart.dao.OrderDao;
import com.ngCart.models.CommonVo;

@Service
public class OrderService {

	@Autowired
	OrderDao orderDao;
	
	public List<CommonVo> displayUserOrders(String userId){
		return orderDao.displayUserOrders(userId);
	}
}
