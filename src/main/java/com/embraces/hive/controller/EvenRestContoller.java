package com.embraces.hive.controller;

import com.embraces.hive.config.HbaseEvntServiceFactory;
import com.embraces.hive.util.BaseResult;
import com.embraces.hive.util.HBaseServiceUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author Lijl
 * @ClassName EvenRestContoller
 * @Description TODO
 * @Date 2020/11/30 21:29
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/evnt")
public class EvenRestContoller {

    @Resource
    private HBaseServiceUtil hBaseServiceUtil;

    @PostMapping(value = "/api_{methodNameType}")
    public BaseResult syncInduMethod(@PathVariable String methodNameType, @RequestBody Map<String,String> paramMap, HttpServletRequest request, HttpServletResponse response){
        if (paramMap!=null){
            try {
                BaseResult baseResult = HbaseEvntServiceFactory.handle(methodNameType, paramMap);
                if (baseResult!=null){
                    return baseResult;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return BaseResult.error("无效参数");
    }

    @PostMapping(value = "/selectOneRowDataMap")
    public BaseResult selectOneRowDataMap(){
        String tableName = "DWA_HW:NETWORK_RETENTION_DATA_DATA_BILL";
        String rowNumber  = "00000007618aed85fb3608d1c9183857_20201017151645";
        Map<String, Object> map = hBaseServiceUtil.selectOneRowDataMap(tableName, rowNumber);
        return BaseResult.ok("成功",map);
    }
}
