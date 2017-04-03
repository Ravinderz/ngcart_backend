package com.ngCart.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController

public class NgController {
	
	@RequestMapping(value = "test" , method=RequestMethod.GET)
	public String testApi(){
		return "this is testing";
	}

	@RequestMapping(value = "register" , method = RequestMethod.GET)
	public String registerUser(){
		return "send data to the api to register the user once registered a success message is returned back";
	}
}
