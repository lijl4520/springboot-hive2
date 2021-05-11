package com.embraces.hive.service.hbase;

import com.embraces.hive.util.BaseResult;

import java.util.Map;

/**
 * @Author Lijl
 * @InterfaceName HBaseService
 * @Description TODO
 * @Date 2020/11/24 20:00
 * @Version 1.0
 */
public interface HBaseService {
    BaseResult deal(Map<String,String> paramMap, String methodNameType);
}
