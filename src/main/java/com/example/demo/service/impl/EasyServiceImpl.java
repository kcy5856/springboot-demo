package com.example.demo.service.impl;

import com.example.demo.service.EasyService;
import com.example.demo.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
}
