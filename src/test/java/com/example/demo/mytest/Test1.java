package com.example.demo.mytest;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test1 {

	public static void main(String[] args) {
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 22);
		cal.set(Calendar.MINUTE, 20);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		System.out.println(cal.getTime());
		
//		BigDecimal big = new BigDecimal(0.2223333);
//		String doubleValue = big.divide(BigDecimal.valueOf(1000)).setScale(4, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
//		System.out.println(doubleValue);
//		
//		
//		String reg1 = "/clusters/([^//]+)/ingressController/([^//]+)_DELETE";
//		String reg2 = "/loadbalance/cluster/([^//]+)/ingressController/([^//]+)_DELETE";
//		
//		Pattern p1 = Pattern.compile(reg1);
//		Pattern p2 = Pattern.compile(reg2);
//		
//		String url = "/loadbalance/cluster/ijsff/ingressController/asdfads_DELETE";
//		
//		System.out.println(p1.matcher(url).find());
//		System.out.println(p2.matcher(url).find());
		
		String content = "server {\r\n" + 
				"	listen 8162;\r\n" + 
				"	server_name max.aa;\r\n" + 
				"	location /usr {\r\n" + 
				"	#	access_log #/var/log/nginx/xx.log;\r\n" + 
				"		proxy_pass http://$svc_upstream;\r\n" + 
				"	}\r\n" + 
				"	\r\n" + 
				"	location ~* *.(aa|bb|cc){\r\n" + 
				"				access_log /var/log/nginx/xx.log;\r\n" + 
				"		proxy_pass http://$fasdfafdsf;\r\n" + 
				"	}\r\n" + 
				"		\r\n" + 
				"}";
		
		String regex = "location[\\s]+[\\S]+[\\s]+([\\S]+)[\\s]*\\{";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			System.out.println(matcher.group(0));
			System.out.println(matcher.group(1));
		}
		
	}

}
