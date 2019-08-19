package com.example.demo.mytest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.enums.ExcelVersion;
import com.example.demo.common.tools.PropertiesHelper;
import com.example.demo.model.ExcelSheetPO;
import com.example.demo.utils.ExcelUtil;
import com.example.demo.utils.FileUtil;

public class TestMain {
	public static void main(String[] args) {
		System.out.println((int)Math.floor(1/2));
				
		System.out.println(PropertiesHelper.getProperty("ks.value"));
		float a = 1f;
		System.out.println(a/100);
		
		File file = new File("E:/test.xls");
		System.out.println(FileUtil.getFileExtName(file));
		
		try {
			List<ExcelSheetPO> readExcel = ExcelUtil.readExcel(file, null, null);
			List<ExcelSheetPO> nex = new ArrayList<ExcelSheetPO>();
			int i = 0;
			for (ExcelSheetPO excelSheetPO : readExcel) {
				excelSheetPO.setSheetName("pac");
				excelSheetPO.setHeaders(new String[] {"vs","士大夫","现场v下"});
				nex.add(excelSheetPO);
				break;
			}
			
			
			ExcelUtil.createWorkbookAtDisk(ExcelVersion.V2003, nex, "E:/as.xls");
			System.out.println(JSON.toJSON(readExcel).toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}	
