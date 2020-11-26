package com.embraces.hive.template;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.embraces.hive.config.DataSourceConfig;
import com.embraces.hive.model.HiveTableEnum;
import com.embraces.hive.model.TvMEvntNlInduAll;
import com.embraces.hive.service.HBaseService;
import com.embraces.hive.util.BaseResult;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Lijl
 * @ClassName AbstractEvntTemplate
 * @Description TODO
 * @Date 2020/11/24 20:20
 * @Version 1.0
 */
public abstract class AbstractEvntTemplate implements HBaseService {

    protected abstract BaseResult<?> execute(List<Map<String,String>> condition, HiveTableEnum hiveTableEnum, String methodNameType) throws Exception;


    @Override
    public BaseResult<?> deal(List<Map<String,String>> condition, String methodNameType) {
        try {
            if (checkParam(condition)){
                return this.execute(condition,HiveTableEnum.fromTypeName(methodNameType),methodNameType);
            }
            return new BaseResult<>(500,"参数校验失败",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BaseResult<>(500,"查询失败",null);
    }

    private boolean checkParam(List<Map<String, String>> condition) {
        List<String> flList = new ArrayList<>();
        flList.add("SERV_NUMBER");
        flList.add("HOME_PROV_ID");
        flList.add("HOME_AREA_ID");
        flList.add("TIME_ID");
        if (condition!=null && condition.size()==4){
            List<String> list = new ArrayList<>();
            for (Map<String, String> map : condition) {
                String key = map.get("key");
                String value = map.get("value");
                if (key!=null && !"".equals(key) && value!=null && !"".equals(value)){
                    list.add(key);
                }
            }
            if (list.size()==4){
                return flList.contains(list);
            }else{
                return false;
            }
        }
        return false;
    }


    protected Object invoteSetter(Class cls,Map<String,Object> map) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Object obj = cls.newInstance();
        Field[] fs = cls.getDeclaredFields();
        for (Field f : fs) {
            String fieldname = f.getName();// 属性的名字。
            Class<?> type = f.getType();// 属性的类型的class对象。 int -- int.class
            String setter = "set" + fieldname.substring(0, 1).toUpperCase()
                    + fieldname.substring(1, fieldname.length());
            Method settermethod = cls.getMethod(setter, type);
            if (settermethod == null) {
                continue;// 如果方法不存在则跳过。
            }
            Object value = null;
            if (type == int.class || type == Integer.class) {
                value = map.get(fieldname);
            } else if (type == String.class) {
                value = map.get(fieldname);
            } else if (type == Double.class || type == double.class) {
                value = map.get(fieldname);
            }//此处还应该有更多的判断。
            // 反射调用方法。setXXX方法。进行赋值。
            settermethod.invoke(obj, value);
        }
        return obj;
    }
}
