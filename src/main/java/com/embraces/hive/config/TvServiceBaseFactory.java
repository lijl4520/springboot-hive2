package com.embraces.hive.config;

import com.alibaba.fastjson.JSONArray;
import com.embraces.hive.annotation.ServiceCode;
import com.embraces.hive.service.TvService;
import com.embraces.hive.util.BaseResult;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Author Lijl
 * @ClassName TvServiceBaseFactory
 * @Description 策略工厂
 * @Date 2020/10/27 17:49
 * @Version 1.0
 */
@Component
public class TvServiceBaseFactory {
    private static Map<String, TvService> beanMap = new ConcurrentHashMap<>();
    public static Map<String,String> serviceCode = new ConcurrentHashMap<>();

    /**
     * @Author Lijl
     * @MethodName TvServiceBaseFactory
     * @Description 实例化策略工厂
     * @Date 10:18 2020/11/2
     * @Version 1.0
     * @param tvServiceList
     * @return: null
    **/
    public TvServiceBaseFactory(List<TvService> tvServiceList){
        beanMap = tvServiceList.stream().collect(Collectors.toMap(tvService ->
                //获取当前实例的注解作为key
                AnnotationUtils.findAnnotation(tvService.getClass(),ServiceCode.class).value(),
                //将当前对象作为value
                v -> v,
                //解决key重复,判断若已存在该key则使用已存在的
                (v1,v2) -> v1));
    }

    /**
     * @Author Lijl
     * @MethodName getTvService
     * @Description 获取接口实例
     * @Date 11:04 2020/11/2
     * @Version 1.0
     * @param code
     * @return: com.embraces.hive.service.TvService
    **/
    private static TvService getTvService(String code){
        String beanId = getBeansCode(code);
        TvService tvService = beanMap.get(beanId);
        if (tvService!=null){
            return tvService;
        }
        return null;
    }

    /**
     * @Author Lijl
     * @MethodName handle
     * @Description 策略处理
     * @Date 11:04 2020/11/2
     * @Version 1.0
     * @param methodNameType
     * @param condition
     * @return: com.embraces.hive.util.BaseResult<?>
    **/
    public static BaseResult<?> handle(String methodNameType, JSONArray condition, HttpServletResponse response) throws UnsupportedEncodingException {
        return getTvService(methodNameType).deal(condition,methodNameType,response);
    }

    /**
     * @Author Lijl
     * @MethodName getBeansCode
     * @Description 获取接口ID
     * @Date 11:05 2020/11/2
     * @Version 1.0
     * @param code
     * @return: java.lang.String
    **/
    private static String getBeansCode(String code) {
        if (serviceCode!=null){
            String beanId = serviceCode.get(code);
            if (!beanId.isEmpty()){
                return beanId;
            }
        }
        return null;
    }
}
