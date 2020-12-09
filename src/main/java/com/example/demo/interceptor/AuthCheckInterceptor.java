package com.example.demo.interceptor;

import com.alibaba.fastjson.JSON;
import com.example.demo.service.EasyService;
import com.example.demo.utils.ResultUtil;
import com.example.demo.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 可注入其他服务的拦截器
 */
@Component
public class AuthCheckInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private EasyService easyService;

    @Autowired
    public AuthCheckInterceptor(EasyService easyService) {
        this.easyService = easyService;
    }

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        logger.info("postHandle is ok");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj)
            throws Exception {
        String key = request.getParameter("key");

        System.out.println(request.getServletPath());
        System.out.println(request.getMethod());

        HandlerMethod handlerMethod = (HandlerMethod) obj;
        System.out.println(handlerMethod.getMethodAnnotation(ResponseBody.class));

        if (StringUtils.isEmpty(key)) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(ResultUtil.error("error prehandle")));
            return false;
        } else {
            logger.info("test interceptor prehandle is ok");
            // TODO 验证逻辑
            return true;
        }
    }

}

