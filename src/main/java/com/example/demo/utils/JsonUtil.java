package com.example.demo.utils;

import com.example.demo.service.impl.DemoServiceImpl;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {
    static Logger logger = LoggerFactory.getLogger(JsonUtil.class);


    /**
     * @Author AlphaJunS
     * @Date 19:06 2020/3/27
     * @Description json转map
     * @param json
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    public static Map<String,Object> jsonToMap(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> map = null;
        try {
            map = (Map<String,Object>)objectMapper.readValue(json, Map.class);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("jsonToMap", e);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("jsonToMap", e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("jsonToMap", e);
        }
        return map;
    }

    /**
     * @Author AlphaJunS
     * @Date 19:07 2020/3/27
     * @Description json转List<Map<String,?>>
     * @param json
     * @return java.util.List<java.util.Map<java.lang.String,?>>
     */
    public static List<Map<String,?>> jsonToMapList(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, Map.class);
        List<Map<String, ?>> mapList = null;
        try {
            mapList = (List<Map<String,?>>)objectMapper.readValue(json, javaType);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("jsonToMapList", e);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("jsonToMapList", e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("jsonToMapList", e);
        }
        return mapList;
    }

    /**
     * @Author AlphaJunS
     * @Date 19:09 2020/3/27
     * @Description map转json
     * @param map
     * @return java.lang.String
     */
    public static String mapToJson(Map<String,Object> map) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(map);
        return jsonString;
    }

    /**
     * @Author AlphaJunS
     * @Date 19:10 2020/3/27
     * @Description 对象转json
     * @param object
     * @return java.lang.String
     */
    public static String objectToJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonList = null;
        try {
            jsonList = objectMapper.writeValueAsString(object);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("objectToJson", e);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("objectToJson", e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("objectToJson", e);
        }
        return jsonList;
    }

    /**
     * @Author AlphaJunS
     * @Date 20:52 2020/3/27
     * @Description List<Map<String,?>>转json
     * @param cardInfoList
     * @return java.lang.String
     */
    public static String mapListToJson(List<Map<String, Object>> cardInfoList) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonList = null;
        try {
            jsonList = objectMapper.writeValueAsString(cardInfoList);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("mapListToJson", e);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("mapListToJson", e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("mapListToJson", e);
        }
        return jsonList;
    }

    /**
     * @Author AlphaJunS
     * @Date 20:57 2020/3/27
     * @Description 数组转json
     * @param args
     * @return java.lang.String
     */
    public static String arrayToJson(String[] args) throws Exception{
        // 先讲数组转化为map，然后map转json
        Map<String,String> map = new HashMap<String,String>();
        for(int i=0; i<args.length; i++){
            map.put(i+"", args[i]);
        }
        String json = JsonUtil.objectToJson(map);
        return json;
    }

    public static Map<String, Object> convertJsonToMap(String json) {
        Map<String, Object> map = new HashMap<>();
        if (json != null){
             ObjectMapper objectMapper = new ObjectMapper();
             try {
                 map = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
             }catch (IOException e){
                 logger.error("convertJsonToMap", e);
             }
        }

        return map;
    }


    public static <T> T jsonToPojo(String json, Class<T> beanType){
        if (json == null || "".equals(json)){
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        T t = null;
        try {
            t = objectMapper.readValue(json, beanType);
        } catch (IOException e) {
            logger.error("jsonToPojo", e);
        }
        return t;

    }

    public static <T> List<T> jsonToList(String json, Class<T> beanType){
        if (json == null || "".equals(json)){
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = objectMapper.readValue(json, javaType);
            return list;
        } catch (IOException e) {
            logger.error("jsonToPojo", e);
        }
        return null;
    }

}