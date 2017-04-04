package com.ngCart.util;

import java.util.UUID;

public class GenerateUUID {

	public static String generateId(){
		return UUID.randomUUID().toString();
	}

}
