package com.embraces.hive.controller;

import com.embraces.hive.config.TvServiceBaseFactory;
import com.embraces.hive.util.BaseResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * @param paramMap
     * @return: com.embraces.hive.util.BaseResult<?>
    **/
    @PostMapping(value = "/{methodNameType}")
    public BaseResult induMethod(@PathVariable String methodNameType, @RequestBody Map<String,Object> paramMap, HttpServletRequest request,HttpServletResponse response){
        if (paramMap!=null){
            try {
                return TvServiceBaseFactory.handle(methodNameType, paramMap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return BaseResult.error("无效参数");
    }
}
