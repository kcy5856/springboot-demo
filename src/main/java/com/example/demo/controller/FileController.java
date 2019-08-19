package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {
	Logger logger = LoggerFactory.getLogger(FileController.class);

	@RequestMapping(value="/upload", method = RequestMethod.POST)
	private String uploadFile(@RequestParam("file") MultipartFile file) {
		logger.debug("filename: {}", file.getOriginalFilename());
		return "ok";
	}

}
