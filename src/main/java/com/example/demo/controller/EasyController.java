package com.example.demo.controller;

import com.example.demo.service.EasyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "easy api")
@RestController
public class EasyController {

    @Autowired
    EasyService easyService;

    @ApiOperation(value = "easy hello", notes = "")
    @GetMapping("/easy/hello")
    public String getServiceName(){
        return easyService.getService();
    }

    @GetMapping("/easy/hello/aa")
    public String getNewName(){
        return easyService.getService();
    }
}
