package com.example.demo.service.impl;

import com.example.demo.common.enums.ErrorEnum;
import com.example.demo.exception.BizException;
import com.example.demo.model.common.Result;
import com.example.demo.service.EasyService;
import com.example.demo.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class EasyServiceImpl implements EasyService {

    @Autowired
    HttpClientUtil httpClientUtil;

    @Override
    public String getService() {
        try {
            URI uri = new URI("http://www.baidu.com");
            Result result = httpClientUtil.get(uri, null, null);
            System.out.println(result.getData());
        } catch (IOException e) {
            throw new BizException(ErrorEnum.CONNECT_FAIL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return EasyServiceImpl.class.getName();
    }
}
