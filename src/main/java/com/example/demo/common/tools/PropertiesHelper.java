package com.example.demo.common.tools;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 配置帮助类
 * @author harmonyCloud
 *
 */
public class PropertiesHelper {
	
	private static final String CONFIG_PATH = "/config";
	private static final String COFNIG_EXT_NAME = ".properties";
	private static Map<String, String> configMap = new HashMap<String, String>();
	
	private PropertiesHelper() {}
	
	private synchronized static void loadProperties() {
		configMap.clear();
		String basepath = PropertiesHelper.class.getResource(CONFIG_PATH).getPath();
		File filePath = new File(basepath);
		if (!filePath.isDirectory() ||filePath.listFiles().length == 0) {
			return;
		}
		
		Properties property = new Properties();
		for (File file : filePath.listFiles()) {
			if (!file.getName().endsWith(COFNIG_EXT_NAME)) {
				continue;
			}
			try {
				property.load(new FileReader(file));
				for(Map.Entry<Object, Object> entry:property.entrySet()) {
					configMap.put(entry.getKey().toString(), entry.getValue().toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static String getProperty(String key) {
		if (configMap.isEmpty()) {
			loadProperties();
		}
		
		return configMap.get(key);
	}
	
}
