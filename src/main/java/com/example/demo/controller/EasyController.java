package com.example.demo.controller;

import com.example.demo.service.EasyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EasyController {

    @Autowired
    EasyService easyService;

    @GetMapping("/easy/hello")
    public String getServiceName(){
        return easyService.getService();
    }

    @GetMapping("/easy/hello/aa")
    public String getNewName(){
        return easyService.getService();
    }
}
