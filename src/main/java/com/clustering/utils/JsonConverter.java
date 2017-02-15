package com.clustering.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {

	/**
	 * Converts any object to json string
	 * 
	 * @param arg0
	 *            Object to be converted
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String convert(Object arg0) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(arg0);

	}
}
