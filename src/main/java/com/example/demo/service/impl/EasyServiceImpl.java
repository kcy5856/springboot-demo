package com.example.demo.service.impl;

import com.example.demo.service.EasyService;
import org.springframework.stereotype.Service;

@Service
public class EasyServiceImpl implements EasyService {
    @Override
    public String getService() {
        return EasyServiceImpl.class.getName();
    }
}
