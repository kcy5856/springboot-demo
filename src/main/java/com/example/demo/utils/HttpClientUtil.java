package com.example.demo.utils;

import com.example.demo.common.enums.ErrorEnum;
import com.example.demo.model.common.Result;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class HttpClientUtil {
	private final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

	private int TIMEOUT = 10000;

	private String UTF_8 = "UTF-8";

	@Autowired
	private CloseableHttpClient httpClient;

	@Autowired
	private RequestConfig requestConfig;
	

	/**
	 * 发起GET请求,如果没有header和params则设置为null,如果不需要设置长时间timeout则传null
	 * @param uri
	 * @param headers
	 * @param params
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public Result get(URI uri, Map<String, Object> headers,
					  Map<String, Object> params, int timeOut) throws IOException{
		HttpClientResponse httpClientResponse = new HttpClientResponse();
		URIBuilder ub = new URIBuilder(uri);

		if (params != null) {
			ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
			ub.setParameters(pairs);
		}
		try {
			HttpGet httpGet = new HttpGet(ub.build());
			httpGet.setConfig(requestConfig);
			if (headers != null) {
				for (Entry<String, Object> param : headers.entrySet()) {
					httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
				}
			}
			
			CloseableHttpResponse response = httpClient.execute(httpGet);
			Integer statusCode = response.getStatusLine().getStatusCode();
			httpClientResponse.setStatus(statusCode);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				httpClientResponse.setBody(result);
				response.close();
				if (HttpStatusUtil.isSuccessStatus(statusCode)) {
					return ResultUtil.success(result);
				} else {
					return ResultUtil.error(result);
				}
			}
		} catch (Exception e) {
            LOGGER.warn("http请求失败，url:{}", uri, e);
			return ResultUtil.error(ErrorEnum.CONNECT_FAIL, e.getMessage());
		} finally {
			try {
				if (httpClient != null)
					httpClient.close();
			} catch (IOException e) {
				LOGGER.warn("关闭httpClient失败", e);
			}
		}
		return ResultUtil.error();
	}
	/**
	 * 发起GET请求,如果没有header和params则设置为null
	 * 
	 * @param uri
	 * @param headers
	 *            请求头
	 * @param params
	 *            参数
	 * @return
	 */
	public Result get(URI uri, Map<String, Object> headers,
			Map<String, Object> params) throws IOException{
		return get(uri,headers,params,TIMEOUT);
	}


	/**
	 * 发起POST请求,如果没有header和params则设置为null
	 * 
	 * @param uri
	 * @param headers
	 *            请求头
	 * @param params
	 *            设置参数
	 * @return
	 * @throws Exception 
	 */
	public Result post(URI uri, Map<String, Object> headers,
			Map<String, Object> params) throws Exception {
		HttpClientResponse httpClientResponse = new HttpClientResponse();
		
		try {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setConfig(requestConfig);
            if (headers != null) {
    			for (Entry<String, Object> param : headers.entrySet()) {
    				httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
    			}
    		}
			if (params != null) {
				ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
				httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
			}
			
			CloseableHttpResponse response = httpClient.execute(httpPost);
			Integer statusCode = response.getStatusLine().getStatusCode();
			httpClientResponse.setStatus(statusCode);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				httpClientResponse.setBody(result);
				response.close();
				if (HttpStatusUtil.isSuccessStatus(statusCode)) {
					return ResultUtil.success(result);
				} else {
					return ResultUtil.error(result);
				}
			}
		} catch (ClientProtocolException e) {
			LOGGER.warn("httpPostRequest失败，url:{}", uri, e);
		} catch (IOException e) {
			LOGGER.warn("httpPostRequest失败，url:{}", uri, e);
		} finally {
			try {
				if (httpClient != null)
					httpClient.close();
			} catch (IOException e) {
				LOGGER.warn("关闭httpClient失败", e);
			}
		}
		return ResultUtil.error();
	}

	/**
	 * 发起PUT请求,如果没有header和params则设置为null
	 *
	 * @param uri
	 * @param headers
	 *            请求头
	 * @param params
	 *            设置参数
	 * @return
	 * @throws Exception
	 */
	public Result put(URI uri, Map<String, Object> headers,
			Map<String, Object> params) throws Exception {
		HttpClientResponse httpClientResponse = new HttpClientResponse();

		try {
            HttpPut httpPut = new HttpPut(uri);
			httpPut.setConfig(requestConfig);
            if (headers != null) {
    			for (Entry<String, Object> param : headers.entrySet()) {
					httpPut.addHeader(param.getKey(), String.valueOf(param.getValue()));
    			}
    		}
			if (params != null) {
				ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
				httpPut.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
			}

			CloseableHttpResponse response = httpClient.execute(httpPut);
			Integer statusCode = response.getStatusLine().getStatusCode();
			httpClientResponse.setStatus(statusCode);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				httpClientResponse.setBody(result);
				response.close();
				if (HttpStatusUtil.isSuccessStatus(statusCode)) {
					return ResultUtil.success(result);
				} else {
					return ResultUtil.error(result);
				}
			}
		} catch (ClientProtocolException e) {
			LOGGER.warn("httpPostRequest失败，url:{}", uri, e);
		} catch (IOException e) {
			LOGGER.warn("httpPostRequest失败，url:{}", uri, e);
		} finally {
			try {
				if (httpClient != null)
					httpClient.close();
			} catch (IOException e) {
				LOGGER.warn("关闭httpClient失败", e);
			}
		}
		return ResultUtil.error();
	}

	public Result delete(URI uri,
            Map<String, Object> params, Map<String, Object> headers) throws IOException {
        
        CloseableHttpResponse response = null;
        HttpDelete httpDelete = null;
        try {

            httpDelete = new HttpDelete(uri);
            httpDelete.setConfig(requestConfig);
            setHeaders(httpDelete, headers);
            response = httpClient.execute(httpDelete);
            HttpEntity resentity = response.getEntity();
            String content = null;
            if (resentity != null) {
				content = EntityUtils.toString(resentity, "UTF-8");
				//}
				if (HttpStatusUtil.isSuccessStatus(response.getStatusLine().getStatusCode())) {
					return ResultUtil.success(content);
				} else {
					return ResultUtil.error(content);
				}
			}
        } catch (IOException e) {
            throw e;
        } finally {
            httpDelete.abort();
        }
        return ResultUtil.error();
    }
	
	/**
	 * 将map转换为请求中的参数
	 * 
	 * @param params
	 * @return
	 */
	private ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (Entry<String, Object> param : params.entrySet()) {
			pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
		}
		return pairs;
	}
	
	/**
	 * 设置请求头
	 * @param method
	 * @param headers
	 */
	private  void setHeaders(HttpRequestBase method, Map<String, Object> headers) {
        if (headers == null) {
            return;
        }
        for (Entry<String, Object> entry : headers.entrySet()) {
            method.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
        }
    }

	public String getHttpUrl(String protocol, String host, Integer port){
		return protocol + "://" + host + ":" + port;
	}

	private void setHeader(HttpRequestBase method, Map<String, Object> headers) {
		if (headers == null) {
			return;
		}
		for (Entry<String, Object> entry : headers.entrySet()) {
			method.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
		}
	}

	/**
	 * 判断是否是application/json方式
	 * @param request 请求
	 * @return boolean
	 */
	public boolean isApplicationJsonType(HttpServletRequest request) {
		String contentType = request.getContentType();
		if (contentType != null) {
			try {
				MediaType mediaType = MediaType.parseMediaType(contentType);
				if (MediaType.APPLICATION_JSON.includes(mediaType)) {
					return true;
				}
				return false;
			} catch (IllegalArgumentException ex) {
				return false;
			}
		} else {
			return false;
		}
	}

}
