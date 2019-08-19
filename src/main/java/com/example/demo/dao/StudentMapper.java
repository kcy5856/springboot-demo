package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper {
	public List<Map<String, Object>> listAllStudent();
}
