package com.embraces.hive.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.embraces.hive.config.TvServiceBaseFactory;
import com.embraces.hive.util.BaseResult;
import com.embraces.hive.util.HBaseServiceUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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

    @Resource
    private HBaseServiceUtil hBaseServiceUtil;

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

    @PostMapping(value = "/api_{methodNameType}")
    public BaseResult<?> syncInduMethod(@PathVariable String methodNameType, @RequestBody List<Map<String,String>> jsonArray, HttpServletRequest request,HttpServletResponse response){
        if (jsonArray!=null){
            try {
                System.out.println(methodNameType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new BaseResult<>(500,"无效参数",null);
    }

    @PostMapping(value = "/queryTest")
    public BaseResult<?> queryTest(){
        List<String> allTableNames = hBaseServiceUtil.getAllTableNames();
        System.out.println("-----遍历查询全表内容-----");
        allTableNames.forEach(k -> {
            System.out.println("tableName---->"+k);
        });
        return new BaseResult<>(200,"成功",allTableNames);
    }

    @PostMapping(value = "/queryTests")
    public BaseResult<?> queryTests(){
        try {
            String tableName = "DWA_HW:NETWORK_RETENTION_DATA_DATA_BILL";
            List<Map<String,String>> list = new ArrayList<Map<String, String>>();
            Map<String,String> map = new HashMap<>(1);
            map.put("IMEI","e47738b6a0a45ad49875c4d7d005f6b5988f2be79788108197fbcf9f28aa52ed");
            list.add(map);
            Map<String,String> map1 = new HashMap<>(1);
            map1.put("StartTime","20201017151645");
            list.add(map1);
            List<Map<String, Object>> mapList = hBaseServiceUtil.selectTableDataByFilter(tableName, "F", list, true);
            if (mapList!=null && mapList.size()>0){
                for (Map<String, Object> stringObjectMap : mapList) {
                    System.out.println(stringObjectMap.toString());
                }
            }
            return new BaseResult<>(200,"成功",mapList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BaseResult<>(500,"失败",null);
    }
}
