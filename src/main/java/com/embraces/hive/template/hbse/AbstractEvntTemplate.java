package com.embraces.hive.template.hbse;

import com.embraces.hive.model.hbase.HbaseTableEnum;
import com.embraces.hive.service.hbase.HBaseService;
import com.embraces.hive.util.BaseResult;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    /**
     * @Author Lijl
     * @MethodName execute
     * @Description 具体业务实现抽象
     * @Date 9:20 2020/11/30
     * @Version 1.0
     * @param rowKey
     * @param hiveTableEnum
     * @param methodNameType
     * @return: com.lijl.hbase.utils.BaseResult<?>
     **/
    protected abstract BaseResult<?> execute(String rowKey, HbaseTableEnum hiveTableEnum, String methodNameType) throws Exception;


    @Override
    public BaseResult<?> deal(Map<String,String> paramMap, String methodNameType) {
        try {
            StringBuilder sb = new StringBuilder();
            if (checkParam(paramMap,sb)){
                String s = sb.toString();
                return this.execute(s.substring(0,s.length()-1), HbaseTableEnum.fromTypeName(methodNameType),methodNameType);
            }
            return new BaseResult<>(500,"参数校验失败",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BaseResult<>(500,"查询失败",null);
    }

    private boolean checkParam(Map<String, String> paramMap, StringBuilder sb) {
        int mapSize = 4;
        List<String> flList = new ArrayList<>();
        flList.add("SERV_NUMBER");
        flList.add("HOME_PROV_ID");
        flList.add("HOME_AREA_ID");
        flList.add("TIME_ID");
        if (paramMap!=null && paramMap.size()==mapSize){
            List<String> list = new ArrayList<>();
            paramMap.forEach((key,val)->{
                if (key!=null && !"".equals(key) && val!=null && !"".equals(val)){
                    list.add(key);
                    sb.append(val+"_");
                }
            });
            if (list.size()==mapSize){
                return flList.containsAll(list);
            }else{
                return false;
            }
        }
        return false;
    }


    /**
     * @Author Lijl
     * @MethodName invoteSetter
     * @Description 通过反射给实体赋值
     * @Date 9:23 2020/11/30
     * @Version 1.0
     * @param cls
     * @param map
     * @return: java.lang.Object
     **/
    protected Object invoteSetter(Class cls,Map<String,Object> map) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Object obj = cls.newInstance();
        Field[] fs = cls.getDeclaredFields();
        for (Field f : fs) {
            // 属性的名字。
            String fieldname = f.getName();
            // 属性的类型的class对象。 int -- int.class
            Class<?> type = f.getType();
            String setter = "set" + fieldname.substring(0, 1).toUpperCase()
                    + fieldname.substring(1, fieldname.length());
            Method settermethod = cls.getMethod(setter, type);
            if (settermethod == null) {
                continue;// 如果方法不存在则跳过。
            }
            Object value = null;
            if (type == int.class || type == Integer.class) {
                Object o = map.get(fieldname);
                if (o!=null){
                    if (o instanceof String){
                        if (!"".equals(o)){
                            value = Integer.parseInt(o+"");
                        }
                    }else if (o instanceof Integer){
                        value = o;
                    }
                }
            } else if (type == String.class) {
                value = map.get(fieldname);
            } else if (type == Double.class || type == double.class) {
                Object o = map.get(fieldname);
                if (o!=null){
                    if (o instanceof String){
                        if (!"".equals(o)){
                            value = Double.parseDouble(o+"");
                        }
                    }else if (o instanceof Double){
                        value = o;
                    }
                }
            }//此处还应该有更多的判断。
            // 反射调用方法。setXXX方法。进行赋值。
            settermethod.invoke(obj, value);
        }
        return obj;
    }
}
