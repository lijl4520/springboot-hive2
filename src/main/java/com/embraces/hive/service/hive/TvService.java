package com.embraces.hive.service.hive;

import com.alibaba.fastjson.JSONArray;
import com.embraces.hive.util.BaseResult;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author Lijl
 * @InterfaceName TvService
 * @Description TODO
 * @Date 2020/10/27 15:52
 * @Version 1.0
 */
public interface TvService {

    BaseResult<?> deal(Map<String,Object> paramMap, String methodNameType, HttpServletResponse response) throws InterruptedException;
}
