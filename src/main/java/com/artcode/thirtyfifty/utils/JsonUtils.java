package com.artcode.thirtyfifty.utils;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class JsonUtils {

	@Autowired
	private ObjectMapper mapper;

	private static final String SUCCESS = "success";
	private static final String DATA = "data";
	private static final String MESSAGE = "message";
	private static final String ERROR = "Error while creating json response.";

	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	public String objectMapperListToJson(List<?> listObject, String message) {

		Map<String, Object> mapObject = new HashMap<>();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapObject.put(SUCCESS, true);
		mapObject.put(MESSAGE, message);
		mapObject.put(DATA, listObject);

		try {
			Writer strWriter = new StringWriter();
			mapper.writeValue(strWriter, mapObject);
			return strWriter.toString();
		} catch (Exception e) {
			logger.error(ERROR, e);
		}
		return null;
	}

	public String objectMapperError(String msg) {
		Map<String, Object> mapObject = new HashMap<>();
		mapObject.put(SUCCESS, false);
		mapObject.put(MESSAGE, msg);
		try {
			Writer strWriter = new StringWriter();
			mapper.writeValue(strWriter, mapObject);
			return strWriter.toString();
		} catch (Exception e) {
			logger.error(ERROR, e);
		}
		return null;
	}
	
	public String objectMapperError(Object object,String msg) {
		Map<String, Object> mapObject = new HashMap<>();
		mapObject.put(SUCCESS, false);
		mapObject.put(DATA, object);
		mapObject.put(MESSAGE, msg);
		try {
			Writer strWriter = new StringWriter();
			mapper.writeValue(strWriter, mapObject);
			return strWriter.toString();
		} catch (Exception e) {
			logger.error(ERROR, e);
		}
		return null;
	}

	public String objectMapperSuccess(String msg) {
		Map<String, Object> mapObject = new HashMap<>();
		mapObject.put(SUCCESS, true);
		mapObject.put(MESSAGE, msg);
		try {
			Writer strWriter = new StringWriter();
			mapper.writeValue(strWriter, mapObject);
			return strWriter.toString();
		} catch (Exception e) {
			logger.error(ERROR, e);
		}
		return null;
	}

	public String objectMapperSuccess(Object object, String msg) {
		Map<String, Object> mapObject = new HashMap<>();
		mapObject.put(SUCCESS, true);
		mapObject.put(DATA, object);
		mapObject.put(MESSAGE, msg);
		try {
			Writer strWriter = new StringWriter();
			mapper.writeValue(strWriter, mapObject);
			return strWriter.toString();
		} catch (Exception e) {
			logger.error(ERROR, e);
		}
		return null;
	}
	
	public  String convertListToString(List<Long> idList) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(idList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public  List<Long> convertStringToList(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonString, objectMapper.getTypeFactory().constructCollectionType(List.class, Long.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public  String convertListToStringOfString(List<String> idList) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(idList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public  List<String> convertStringToListOfString(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonString, objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	 public  boolean createFolder(String FILE) {
	   	   
	   	   boolean flag=false;
	   	   File file = new File(FILE);
	   	   if (!file.exists()) {
	              if (file.mkdir()) {
	                  System.out.println("Directory is created!");
	                  flag=true;
	              } else {
	                  System.out.println("Failed to create directory!");
	              }
	          }else {flag=true;}
	   	   
	   	   
	   	   return flag;
	      }


	
}