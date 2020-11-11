package com.embraces.hive.controller;

import com.alibaba.fastjson.JSONArray;
import com.embraces.hive.config.ReqTimeOutConfig;
import com.embraces.hive.config.TvServiceBaseFactory;
import com.embraces.hive.util.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.Executor;

/**
 * @Author Lijl
 * @ClassName TvRestController
 * @Description Rest控制层
 * @Date 2020/10/20 15:09
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/indu")
public class TvRestController {

    private static Logger log = LoggerFactory.getLogger(TvRestController.class);
    @Autowired
    private Executor asyncServiceExecutor;

    @Resource
    private ReqTimeOutConfig reqTimeOutConfig;

    /**
     * @Author Lijl
     * @MethodName induMethod
     * @Description 集团通用接口
     * @Date 9:26 2020/10/29
     * @Version 1.0
     * @param methodNameType
     * @param jsonArray
     * @return: com.embraces.hive.util.BaseResult<?>
    **/
    @PostMapping(value = "/{methodNameType}")
    public DeferredResult<?> induMethod(@PathVariable String methodNameType, @RequestBody JSONArray jsonArray, HttpServletRequest request,HttpServletResponse response){
        DeferredResult<BaseResult> result = new DeferredResult<>(reqTimeOutConfig.time_out*1000L);
        String requestRefId = request.getHeader("requestRefId");
        if (requestRefId==null){
            requestRefId = "";
        }
        response.addHeader("requestRefId",requestRefId);
        response.addHeader("responseRefId","TSRESP_"+getDateStr()+getRandom());
        result.onTimeout(() -> {
            response.addHeader("responseCode","5000");
            response.addHeader("responseMsg", "fail");
            result.setResult(new BaseResult(503,"请求超时",null));
        });
        result.onCompletion(() ->{
            log.info("API{}调用结束",methodNameType);
        });
        asyncServiceExecutor.execute(()->{
            if (jsonArray!=null){
                try {
                    result.setResult(TvServiceBaseFactory.handle(methodNameType, jsonArray,response));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }else{
                response.addHeader("responseCode","2001");
                response.addHeader("responseMsg","fail");
                result.setResult(new BaseResult<>(500,"参数异常",null));
            }
        });
        return result;
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
