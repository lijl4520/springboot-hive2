package com.embraces.hive.controller;

import com.alibaba.fastjson.JSONArray;
import com.embraces.hive.config.ReqTimeOutConfig;
import com.embraces.hive.config.TvServiceBaseFactory;
import com.embraces.hive.util.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public BaseResult<?> induMethod(@PathVariable String methodNameType, @RequestBody JSONArray jsonArray, HttpServletRequest request,HttpServletResponse response){
        if (jsonArray!=null){
            try {
                return TvServiceBaseFactory.handle(methodNameType, jsonArray,response);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new BaseResult<>(500,"无效参数",null);
    }
}
