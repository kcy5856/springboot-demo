package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.EasyService;
import com.example.demo.service.ElasticSearchService;
import com.example.demo.utils.JsonUtil;
import com.example.demo.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;

@Api(value = "easy api")
@RestController
public class EasyController {
    Logger logger = LoggerFactory.getLogger(EasyController.class);

    @Autowired
    EasyService easyService;

    @Autowired
    ElasticSearchService elasticSearchService;

    @ApiOperation(value = "easy hello", notes = "")
    @GetMapping("/easy/hello")
    public String getServiceName(){
        return easyService.getService();
    }

    @GetMapping("/easy/hello/aa")
    public String getNewName(){
        return easyService.getService();
    }

    @GetMapping("/test/es")
    public String testEs(){
        String index = "test_my_index";
        String id = StringUtils.getUUID();
        System.out.println(id);
        Student student = new Student();
        student.setId("123456");
        student.setName("mksi");
        student.setSex(1);
        student.setBirthday(LocalDateTime.now());
        String data = JsonUtil.objectToJson(student);
        String result = null;
        try {
            IndexResponse document = elasticSearchService.createDocument(index, id, data);
            if (RestStatus.CREATED.equals(document.status())){
                logger.info(document.toString());
            }else {
                logger.error(document.toString());
            }
            String document1 = elasticSearchService.getDocument(index, "64aa6e5a5a2b4ce5b1676ae66fe1a7d5");
            logger.info(document1);
            result = elasticSearchService.SearchDocument(index, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
