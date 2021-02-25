package com.example.demo.dot;

import com.example.demo.utils.JsonUtil;

public class JsonTest {


    public static void main(String[] args){
        TenantResourceDto dto = new TenantResourceDto();
        dto.setClusterId("集群id");
        dto.setClusterName("集群名称");
        dto.setCpuTotal(100.0);
        dto.setCpuUsed(10.0);
        dto.setMemoryTotal(100.0);
        dto.setMemoryUsed(10.0);
        dto.setGpuCoreTotal(10.0);
        dto.setGpuCoreUsed(2.0);
        dto.setGpuMemoryTotal(100.0);
        dto.setGpuMemoryUsed(20.0);
        ResultDto resultDto = new ResultDto();
        resultDto.setSuccess("true");
        resultDto.setData(dto);
        String s = JsonUtil.objectToJson(resultDto);
        System.out.println(s);
    }

}
