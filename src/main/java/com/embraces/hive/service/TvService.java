package com.embraces.hive.service;

import com.alibaba.fastjson.JSONArray;
import com.embraces.hive.util.BaseResult;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @Author Lijl
 * @InterfaceName TvService
 * @Description TODO
 * @Date 2020/10/27 15:52
 * @Version 1.0
 */
public interface TvService {

    BaseResult<?> deal(JSONArray condition, String methodNameType, HttpServletResponse response) throws UnsupportedEncodingException;
}
