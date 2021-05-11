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
import java.time.format.DateTimeFormatter;
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

    private final int MAP_SIZE = 3;

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
    protected abstract BaseResult execute(String rowKey, String tabName, String methodNameType,org.apache.hadoop.conf.Configuration conf) throws Exception;


    @Override
    public BaseResult deal(Map<String,String> paramMap, String methodNameType) {
        try {
            StringBuilder sb = new StringBuilder();
            boolean checkB;
            if (methodNameType.equals("s_0005")){
                checkB = checkToMParam(paramMap,sb);
            }else if (methodNameType.equals("s_0006")){
                checkB = checkAppYnParam(paramMap,sb);
            }else if(methodNameType.equals("s_0007")){
                checkB = checkSearchYnParam(paramMap,sb);
            }else {
                checkB = checkParam(paramMap, sb);
            }
            /*if (!methodNameType.equals("s_0005")){

            }else if (methodNameType.equals("s_0006")){

            } else{

            }*/
            if (checkB){
                String s = sb.toString();
                String tableName = TableNamePropertiesUtils.getTableName(methodNameType);
                if (tableName!=null){
                    org.apache.hadoop.conf.Configuration configuration =  authentication();
                    return this.execute(s.substring(0,s.length()-1), tableName,methodNameType,configuration);
                }
                return BaseResult.error("未匹配到要查询的模型");
            }
            return BaseResult.error("参数校验失败");
        } catch (Exception e) {
            log.error("查询异常：{}",e.getMessage());
        }
        return BaseResult.error("查询失败");
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
            log.error("{}, detail:{}",e1.getMessage(), e1);
        }
    }

    /**
     * @Author lijiale
     * @MethodName checkParam
     * @Description 全行业参数校验
     * @Date 9:24 2021/3/12
     * @Version 1.0
     * @param paramMap
     * @param sb
     * @return: boolean
    **/
    private boolean checkParam(Map<String, String> paramMap, StringBuilder sb) {
        log.info("开始操作校验,校验的参数：{}",paramMap.toString());
        AtomicBoolean flBol = new AtomicBoolean(true);
        if (paramMap!=null && paramMap.size()>=MAP_SIZE){
            List<String> list = new ArrayList<>();
            MandatoryParamUtils.tvEvntParams.forEach(key -> {
                try {
                    String val = paramMap.get(key);
                    if (val!=null && !"".equals(val)){
                        String servNumber = "SERV_NUMBER";
                        if (servNumber.equals(key)){
                            String s = bdiEcnew(val);
                            if (s != null && !"".equals(s)){
                                sb.append(s).append("_");
                            }else{
                                flBol.set(false);
                            }
                        }else{
                            sb.append(val).append("_");
                        }
                        list.add(key);
                    }
                }catch (Exception e){
                    log.error("参数校验异常:{}",e.getMessage());
                }
            });
            if (!flBol.get()){
                log.error("参数加密失败，连接不通");
                return false;
            }
            if (list.size()>=MAP_SIZE){
                return list.containsAll(MandatoryParamUtils.getTvEvntParam());
            }else{
                return false;
            }
        }
        return false;
    }

    /**
     * @Author lijiale
     * @MethodName checkToMParam
     * @Description 检查青海同步接口参数
     * @Date 17:50 2021/3/11
     * @Version 1.0
     * @param paramMap
     * @param sb
     * @return: boolean
    **/
    private boolean checkToMParam(Map<String, String> paramMap, StringBuilder sb) {
        log.info("开始操作校验,校验的参数：{}",paramMap.toString());
        StringBuilder stDate = new StringBuilder();
        StringBuilder enDate = new StringBuilder();
        AtomicBoolean flBol = new AtomicBoolean(true);
        try {
            if (paramMap!=null && paramMap.size()>=MAP_SIZE){
                List<String> list = new ArrayList<>();
                List<String> tvEvntParams = MandatoryParamUtils.toMEventParam;
                for (int i = 0; i < tvEvntParams.size(); i++) {
                    String key = tvEvntParams.get(i);
                    String val = paramMap.get(key);
                    if (val!=null && !"".equals(val)){
                        String servNumber = "SERV_NUMBER";
                        if (servNumber.equals(key)){
                            String s = bdiEcnew(val);
                            if (s != null && !"".equals(s)){
                                stDate.append(s).append("_");
                                enDate.append(s).append("_");
                            }else{
                                flBol.set(false);
                            }
                        }else{
                            if (i%2!=0){
                                stDate.append(val);
                            }else{
                                enDate.append(val).append("_");
                            }
                        }
                        list.add(key);
                    }
                }
                if (!flBol.get()){
                    log.error("参数加密失败，加密访问失败");
                    return false;
                }
                if (list.size()>=MAP_SIZE){
                    boolean ctB = list.containsAll(MandatoryParamUtils.getToMEventParam());
                    if (ctB){
                        sb.append(stDate).append(",").append(enDate);
                    }
                    return ctB;
                }else{
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @Author lijiale
     * @MethodName checkAppYnParam
     * @Description APPtop10参数校验
     * @Date 14:39 2021/5/7
     * @Version 1.0
     * @param paramMap
     * @param sb
     * @return: boolean
    **/
    private boolean checkAppYnParam(Map<String, String> paramMap, StringBuilder sb) {
        log.info("APPtop10校验,校验的参数：{}",paramMap.toString());
        if (paramMap!=null && paramMap.size()>=4){
            List<String> list = new ArrayList<>();
            List<String> twAppYnParam = MandatoryParamUtils.getTwAppYnParam();
            addPValue(paramMap, sb, list, twAppYnParam);
            if (list.size()>=4){
                return list.containsAll(twAppYnParam);
            }else{
                return false;
            }
        }
        return false;
    }

    /**
     * @Author lijiale
     * @MethodName checkSearchYnParam
     * @Description 关键词搜索参数校验
     * @Date 15:51 2021/5/7
     * @Version 1.0
     * @param paramMap
     * @param sb
     * @return: boolean
    **/
    private boolean checkSearchYnParam(Map<String, String> paramMap, StringBuilder sb) {
        log.info("关键词搜索校验,校验的参数：{}",paramMap.toString());
        if (paramMap!=null && paramMap.size()==2){
            List<String> list = new ArrayList<>();
            List<String> twSearchYnParam = MandatoryParamUtils.getTwSearchYnParam();
            addPValue(paramMap, sb, list, twSearchYnParam);
            if (list.size()==2){
                return list.containsAll(twSearchYnParam);
            }else{
                return false;
            }
        }
        return false;
    }

    private void addPValue(Map<String, String> paramMap, StringBuilder sb, List<String> list, List<String> pList) {
        pList.forEach(key -> {
            try {
                String val = paramMap.get(key);
                if (val!=null && !"".equals(val)){
                    sb.append(val);
                    list.add(key);
                }
            }catch (Exception e){
                log.error("参数校验异常:{}",e.getMessage());
            }
        });
        sb.append("_");
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
    private String bdiEcnew(String serv_number) {
        if (serv_number!=null && !"".equals(serv_number)){
            LocalDate now = LocalDate.now();
            DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyyMMdd");
            String dateStr = now.format(ofPattern);
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
            //属性的名字。
            String fieldname = f.getName();
            // 属性的类型的class对象。 int -- int.class
            Class<?> type = f.getType();
            String setter = "set" + fieldname.substring(0, 1).toUpperCase()
                    + fieldname.substring(1, fieldname.length());
            Method settermethod = cls.getMethod(setter, type);
            if (settermethod == null) {
                continue;//如果方法不存在则跳过。
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
