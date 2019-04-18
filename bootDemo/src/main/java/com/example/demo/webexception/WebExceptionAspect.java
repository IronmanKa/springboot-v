package com.example.demo.webexception;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Aspect
@Component
public class WebExceptionAspect {
    private static final Logger logger = LoggerFactory.getLogger(WebExceptionAspect.class);
    //    @Pointcut("execution(public * com.example.demo.test.*.*(..))")
    //凡是注解了RequestMapping的方法都被拦截
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void  webPointCut(){
        
    }

    /**
     * 拦截web层异常
     * @param e
     */
    @AfterThrowing(pointcut = "webPointCut()",throwing = "e")
    public void  handleThrowing(Exception e) throws Throwable{
        if (StringUtils.isNotBlank(e.getMessage())){
            e.printStackTrace();;
            logger.error("发现异常!" + e.getMessage());
//        logger.error();
//            writeContent("系统异常");
            writeContent(e.getMessage());
        }else {
            writeContent("参数错误！！");
        }
      
    }

    private void writeContent(String content) {
        HttpServletResponse response =  ( (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        response.reset();;
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type","text/plain;charset=UTF-8");
        response.setHeader("icop-content-type", "exception");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.print(content);
        writer.flush();
        writer.close();
    }
}
