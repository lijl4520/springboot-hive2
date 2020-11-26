package com.embraces.hive.service;

import com.alibaba.fastjson.JSONArray;
import com.embraces.hive.util.BaseResult;

import java.util.List;
import java.util.Map;

/**
 * @Author Lijl
 * @InterfaceName HBaseService
 * @Description TODO
 * @Date 2020/11/24 20:00
 * @Version 1.0
 */
public interface HBaseService {
    BaseResult<?> deal(List<Map<String,String>> condition, String methodNameType);
}
