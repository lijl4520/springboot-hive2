package com.embraces.hive.config;

import com.embraces.hive.annotation.ServiceCode;
import com.embraces.hive.service.hbase.HBaseService;
import com.embraces.hive.util.BaseResult;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Author Lijl
 * @ClassName HBaseServiceFactory
 * @Description TODO
 * @Date 2020/11/24 20:06
 * @Version 1.0
 */
@Component
public class HbaseEvntServiceFactory {
    private static Map<String, HBaseService> beanMap = new ConcurrentHashMap<>();
    public static Map<String,String> serviceCode = new ConcurrentHashMap<>();

    public HbaseEvntServiceFactory(List<HBaseService> hbServiceList){
        beanMap = hbServiceList.stream().collect(Collectors.toMap(hbService ->
                        //获取当前实例的注解作为key
                        AnnotationUtils.findAnnotation(hbService.getClass(), ServiceCode.class).value(),
                //将当前对象作为value
                v -> v,
                //解决key重复,判断若已存在该key则使用已存在的
                (v1,v2) -> v1));
    }



    private static HBaseService getHBaseService(String code){
        String beanId = getBeansCode(code);
        HBaseService hBaseService = beanMap.get(beanId);
        if (hBaseService!=null){
            return hBaseService;
        }
        return null;
    }

    public static BaseResult<?> handle(String methodNameType, Map<String,String> paramMap) throws InterruptedException {
        return getHBaseService(methodNameType).deal(paramMap,methodNameType);
    }


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
