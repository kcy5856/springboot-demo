package com.example.demo.utils;


/**
 * 
 * http状态码判断
 * 
 * @author jmi
 *
 */
public class HttpStatusUtil {
    private final static int HTTP_STATUS_404 = 404;
	
	public static boolean isSuccessStatus(int statusCode){
        return String.valueOf(statusCode).startsWith("2");
    }

    public static boolean isNotFound(int statusCode){
        return statusCode == HTTP_STATUS_404;
    }


}
