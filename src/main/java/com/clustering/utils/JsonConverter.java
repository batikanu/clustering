package com.clustering.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
	
	
	public static String  convert(Object arg0) throws JsonProcessingException{		
		return new ObjectMapper().writeValueAsString(arg0);
		
	}
}
