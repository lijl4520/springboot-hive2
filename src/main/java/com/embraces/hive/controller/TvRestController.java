package com.embraces.hive.controller;

import com.alibaba.fastjson.JSONArray;
import com.embraces.hive.config.TvServiceBaseFactory;
import com.embraces.hive.util.BaseResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public BaseResult<?> induMethod(@PathVariable String methodNameType,@RequestBody JSONArray jsonArray){
        try {
            if (jsonArray!=null){
                return TvServiceBaseFactory.handle(methodNameType, jsonArray);
            }else {
                return new BaseResult<>(500,"参数异常",null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new BaseResult<>(500,"无业务实现",null);
    }
}
