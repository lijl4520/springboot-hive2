package com.embraces.hive.service;

import com.embraces.hive.util.BaseResult;

/**
 * @Author Lijl
 * @InterfaceName TvService
 * @Description TODO
 * @Date 2020/10/27 15:52
 * @Version 1.0
 */
public interface TvService {

    BaseResult<?> deal(String condition, String methodNameType);

/*    String getCode();*/
}
