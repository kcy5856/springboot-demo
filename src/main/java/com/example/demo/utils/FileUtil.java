package com.example.demo.utils;

import java.io.File;
import java.util.Objects;

public class FileUtil {
	
	/**
	 *  获取文件后缀名。带点
	 * @param file
	 * @return
	 */
	public static String getFileExtName(File file) {
		if (Objects.isNull(file)) {
			return null;
		}
		String path = file.getAbsolutePath();
		return path.substring(path.lastIndexOf("."));
	}
	
	
}
