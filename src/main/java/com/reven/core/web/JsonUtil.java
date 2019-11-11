package com.reven.core.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @author reven
 */
public class JsonUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
	private static final ObjectMapper MAPPER;

	static {
		MAPPER = new ObjectMapper();
		MAPPER.setSerializationInclusion(Include.NON_EMPTY);
		MAPPER.setDateFormat(new SimpleDateFormat("yyyyMMddHHmmss"));
	}

	public static <T> T json2ObjectByTr(String str, TypeReference<T> tr)
			throws JsonParseException, JsonMappingException, IOException {
		return MAPPER.readValue(str, tr);
	}

	public static String object2Json(Object obj) throws JsonProcessingException {
		return MAPPER.writeValueAsString(obj);
	}

	public static Map<String, Object> toMap(String jsonString) {
		Map<String, Object> result = new HashMap<>();
		try {
			String key = null;
			String value = null;
			JSONObject jsonObject = JSONObject.parseObject(jsonString);

			@SuppressWarnings("rawtypes")
			Iterator iterator = (Iterator) jsonObject.keySet();

			while (iterator.hasNext()) {
				key = (String) iterator.next();
				value = jsonObject.getString(key);
				result.put(key, value);
			}
		} catch (Exception e) {
			LOGGER.error("错误提示信息： " + e.getMessage());
		}
		return result;
	}

	public static Gson getGson() {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		return gson;
	}

	public static Map<String, Object> fromJson(String json) {
		return getGson().fromJson(json, new TypeToken<HashMap<String, Object>>() {
		}.getType());

	}

	public static String toJson(Object object) {
		return getGson().toJson(object);
	}

}