package com.embraces.hive.config;

import com.embraces.hive.util.BaseResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @Author Lijl
 * @ClassName TvResponAspect
 * @Description TODO
 * @Date 2020/11/12 15:00
 * @Version 1.0
 */
@Component
@Aspect
public class RestResponAspect {

    @Pointcut("execution(* com.embraces.hive.controller..*(..))")
    public void executePointcut(){}

    @AfterReturning(value = "executePointcut()",returning = "returnVal")
    public void respMethodCall(JoinPoint jp,Object returnVal){
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest){
                request = (HttpServletRequest) arg;
            }else if (arg instanceof HttpServletResponse){
                response = (HttpServletResponse) arg;
            }
        }
        BaseResult ret = (BaseResult) returnVal;
        if (request!=null && response!=null){
            String requestRefId = request.getParameter("requestRefId");
            int code = ret.getCode();
            response.addHeader("requestRefId",requestRefId==null?"":requestRefId);
            response.addHeader("responseRefId","TSRESP_"+getDateStr()+getRandom());
            if (code==200){
                response.addHeader("responseCode","0000");
                response.addHeader("responseMsg","success");
            }else{
                response.addHeader("responseCode","2001");
                response.addHeader("responseMsg","fail");
            }
        }
    }

    private String getDateStr() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String dateStr = dateTime.format(formatter);
        return dateStr;
    }

    private String getRandom(){
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 9; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
