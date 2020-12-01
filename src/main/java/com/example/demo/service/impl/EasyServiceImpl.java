package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.EasyService;
import com.example.demo.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Encoder;

@Service
public class EasyServiceImpl implements EasyService {

    @Autowired
    HttpClientUtil httpClientUtil;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public String getService() {
        return doGet("http://www.baidu.com");

        /*try {
            URI uri = new URI("http://www.baidu.com");
            Result result = httpClientUtil.get(uri, null, null);
            System.out.println(result.getData());
        } catch (IOException e) {
            throw new BizException(ErrorEnum.CONNECT_FAIL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return EasyServiceImpl.class.getName();*/
    }

    private String doGet(String url){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
        return responseEntity.getBody();
    }

    public void regist() {

        try {
            String base64_auth_string = new BASE64Encoder().encode("150da48c337a17a64f7b9939:1b24897a66a43afd9364c4fd".getBytes());
            System.out.println(base64_auth_string);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization","Basic "+ base64_auth_string.trim());
            headers.add("Content-Type", "application/json");
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", "zhuangrui");
                jsonObject.put("password", "123456");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.add(jsonObject);
            HttpEntity<String> formEntity = new HttpEntity<String>(jsonArray.toString(), headers);
            String data = restTemplate.postForObject("https://api.im.jpush.cn/v1/users/", formEntity, String.class);
            System.out.println(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
