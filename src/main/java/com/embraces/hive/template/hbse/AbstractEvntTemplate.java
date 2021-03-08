package com.embraces.hive.template.hbse;

import com.embraces.hive.config.DataSourceConfig;
import com.embraces.hive.convert.Encrypter;
import com.embraces.hive.service.hbase.HBaseService;
import com.embraces.hive.util.BaseResult;
import com.embraces.hive.util.MandatoryParamUtils;
import com.embraces.hive.util.TableNamePropertiesUtils;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author Lijl
 * @ClassName AbstractEvntTemplate
 * @Description TODO
 * @Date 2020/11/24 20:20
 * @Version 1.0
 */
public abstract class AbstractEvntTemplate implements HBaseService {

    private static Logger log = LoggerFactory.getLogger(AbstractEvntTemplate.class);

    @Resource
    private DataSourceConfig dataSourceConfig;

    /**
     * @Author Lijl
     * @MethodName execute
     * @Description 具体业务实现抽象
     * @Date 9:20 2020/11/30
     * @Version 1.0
     * @param rowKey
     * @param tabName
     * @param methodNameType
     * @return: com.lijl.hbase.utils.BaseResult<?>
     **/
    protected abstract BaseResult<?> execute(String rowKey, String tabName, String methodNameType,org.apache.hadoop.conf.Configuration conf) throws Exception;


    @Override
    public BaseResult<?> deal(Map<String,String> paramMap, String methodNameType) {
        try {
            StringBuilder sb = new StringBuilder();
            if (checkParam(paramMap,sb)){
                log.info("参数校验通过");
                String s = sb.toString();
                String tableName = TableNamePropertiesUtils.getTableName(methodNameType);
                if (tableName!=null){
                    org.apache.hadoop.conf.Configuration configuration =  authentication();
                    return this.execute(s.substring(0,s.length()-1), tableName,methodNameType,configuration);
                }
                return new BaseResult<>(500,"未匹配到要查询的模型",null);
            }
            return new BaseResult<>(500,"参数校验失败",null);
        } catch (Exception e) {
            log.error("查询异常：{}",e.getMessage());
        }
        return new BaseResult<>(500,"查询失败",null);
    }



    private org.apache.hadoop.conf.Configuration authentication(){
        loginUserFromKeyTab();
        org.apache.hadoop.conf.Configuration conf = org.apache.hadoop.hbase.HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", dataSourceConfig.zookeeperQuorum);
        conf.set("hbase.zookeeper.property.clientPort", dataSourceConfig.zookeeperClientPort);
        conf.set("zookeeper.znode.parent", dataSourceConfig.zookeeperZnodeParent);
        conf.addResource(new Path(dataSourceConfig.sitePath));
        return conf;
    }

    private void loginUserFromKeyTab() {
        org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
        conf.set("hadoop.security.authentication", dataSourceConfig.authenticationType);
        try {
            UserGroupInformation.setConfiguration(conf);
            UserGroupInformation.loginUserFromKeytab(dataSourceConfig.principal, dataSourceConfig.keytab);
        } catch (IOException e1) {
            log.error(e1.getMessage() + ", detail:{}", e1);
        }
    }




    private boolean checkParam(Map<String, String> paramMap, StringBuilder sb) {
        log.info("开始操作校验,校验的参数：{}",paramMap.toString());
        AtomicBoolean flBol = new AtomicBoolean(true);
        int mapSize = 3;
        if (paramMap!=null && paramMap.size()>=mapSize){
            List<String> list = new ArrayList<>();
            MandatoryParamUtils.tvEvntParams.forEach(key -> {
                try {
                    String val = paramMap.get(key);
                    if (val!=null && !"".equals(val)){
                        String servNumber = "SERV_NUMBER";
                        if (servNumber.equals(key)){
                            String s = bdiEcnew(val);
                            if (s != null && !"".equals(s)){
                                sb.append(s+"_");
                            }else{
                                flBol.set(false);
                            }
                        }else{
                            sb.append(val+"_");
                        }
                        list.add(key);
                    }
                }catch (Exception e){
                    log.error("参数校验异常:{}",e.getMessage());
                }
            });
            if (list.size()>=mapSize){
                return list.containsAll(MandatoryParamUtils.getTvEvntParam());
            }else{
                return false;
            }
        }
        return false;
    }


    /**
     * @Author Lijl
     * @MethodName bdiEcnew
     * @Description 服务号码加密
     * @Date 14:34 2020/12/14
     * @Version 1.0
     * @param serv_number
     * @return: java.lang.String
    **/
    private String bdiEcnew(String serv_number) throws Exception {
        if (serv_number!=null && !"".equals(serv_number)){
            String dateStr = LocalDate.now().toString().replaceAll("-","");
            return Encrypter.encrypt(dateStr, serv_number, dataSourceConfig.authSrvUrl);
        }
        return "";
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
