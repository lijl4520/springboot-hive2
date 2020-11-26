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
public class TvResponAspect {

    @Pointcut("execution(* com.embraces.hive.controller.TvRestController.*(..))")
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
        String dataStr = "";
        LocalDateTime dateTime = LocalDateTime.now();
        String[] dataTimeArr = dateTime.toString().split("T");
        String[] data = dataTimeArr[0].split("-");
        dataStr += data[0]+data[1]+data[2];
        String[] timeArr = dataTimeArr[1].split(":");
        dataStr += timeArr[0]+timeArr[1]+timeArr[2].split("\\.")[0];
        return dataStr;
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
