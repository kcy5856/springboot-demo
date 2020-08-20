package com.example.demo.service.impl;

import com.example.demo.common.enums.ErrorEnum;
import com.example.demo.exception.BizException;
import com.example.demo.model.common.Result;
import com.example.demo.service.EasyService;
import com.example.demo.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EasyServiceImpl implements EasyService {

    @Autowired
    HttpClientUtil httpClientUtil;

    @Override
    public String getService() {
        try {
            Result result = httpClientUtil.get("http://www.baidu.com", null, null);
            System.out.println(result.getData());
        } catch (IOException e) {
            throw new BizException(ErrorEnum.CONNECT_FAIL);
        }
        return EasyServiceImpl.class.getName();
    }
}
