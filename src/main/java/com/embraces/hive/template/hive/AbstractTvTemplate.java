package com.embraces.hive.template.hive;

import com.embraces.hive.config.CsvFilePath;
import com.embraces.hive.config.DataSourceConfig;
import com.embraces.hive.config.ReqTimeOutConfig;
import com.embraces.hive.convert.Decnew;
import com.embraces.hive.model.enumentity.EventEnum;
import com.embraces.hive.model.enumentity.EventWhereEnum;
import com.embraces.hive.service.hive.TvService;
import com.embraces.hive.util.*;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author Lijl
 * @ClassName AbstractTvTemplate
 * @Description TODO
 * @Date 2020/10/27 15:54
 * @Version 1.0
 */
public abstract class AbstractTvTemplate implements TvService {

    private static Logger log = LoggerFactory.getLogger(AbstractTvTemplate.class);

    @Resource
    private CsvFilePath csvFilePath;
    @Resource
    private DataSourceConfig dataSourceConfig;
    @Autowired
    private Executor asyncServiceExecutor;
    @Resource
    private ReqTimeOutConfig reqTimeOutConfig;

    /**
     * @Author Lijl
     * @MethodName executes
     * @Description 查询具体数据
     * @Date 11:31 2020/10/28
     * @Version 1.0
     * @param conStr
     * @param tabName
     * @param jdbcUrl
     * @exception throw Exception
     * @return: java.lang.String
    **/
    protected abstract String executes(String conStr, String tabName, String jdbcUrl, String separator) throws Exception;

    /**
     * @Author Lijl
     * @MethodName bdiDecnew
     * @Description bdi解密
     * @Date 10:54 2020/10/29
     * @Version 1.0
     * @param serv_number
     * @return: java.lang.String
    **/
    protected String bdiDecnew(String serv_number) throws Exception {
        if (serv_number!=null && !"".equals(serv_number)){
            LocalDate now = LocalDate.now();
            DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyyMMdd");
            String dateStr = now.format(ofPattern);
            String decnew = Decnew.decnew(dateStr, serv_number, dataSourceConfig.authSrvUrl, "utf-8");
            if (decnew!=null && !"".equals(decnew)){
                //DES加密
                return DesEncryptUtil.encrypt(decnew.getBytes());
            }
        }
        return "";
    }


    /**
     * des加密
     * @param iemi
     * @return
     * @throws Exception
     */
    protected String bdiEncrypt(String iemi) throws Exception {
        if (iemi!=null && !"".equals(iemi)){
            return DesEncryptUtil.encrypt(iemi.getBytes());
        }
        return "";
    }

    /**
     * @Author Lijl
     * @MethodName deal
     * @Description 公共模板方法
     * @Date 11:32 2020/10/28
     * @Version 1.0
     * @param paramMap
     * @param methodNameType
     * @return: com.embraces.hive.util.BaseResult<?>
    **/
    @Override
    public BaseResult deal(Map<String,Object> paramMap, String methodNameType) throws InterruptedException {
        boolean checkParBol = false;
        log.info("开始校验接口[{}]参数",methodNameType);
        StringBuilder sb = new StringBuilder();
        String methodType = "api0007";
        if (methodType.equals(methodNameType)){
            checkParBol = checkParameter(paramMap,MandatoryParamUtils.getTvDimParam(), sb);
        }else{
            Object event = paramMap.get("EVENT");
            if (event == null || "0".equals(event)){
                checkParBol = checkParameter(paramMap,null, sb);
            }else{
                Object paramlist = paramMap.get("PARAMLIST");
                String et = (String) event;
                checkParBol = dealEventSql(et,sb,paramlist);
            }
        }
        log.info("校验接口[{}]结果：{}",methodNameType,checkParBol);
        if (checkParBol){
            if (sb!=null){
                String tableName = TableNamePropertiesUtils.getTableName(methodNameType);
                if (tableName!=null){
                    return deal(tableName,sb.toString(),methodNameType);
                }
                return BaseResult.error("未匹配到要查询的模型");
            }
            return BaseResult.error("查询条件缺失") ;
        }
        return BaseResult.error("参数缺失");
    }

    private boolean dealEventSql(String et, StringBuilder sb, Object obj) {
        try {
            List<String> paraList = new ArrayList<>();
            EventEnum eventEnum = EventEnum.fromEvent(et);
            EventWhereEnum eventWhereEnum = EventWhereEnum.fromventWhereEnum(et);
            String eventType = "CALLING";
            if (!eventEnum.name().equals(eventType)){
                sb.append(" AND "+eventWhereEnum.name()+" > 0");
            }else{
                sb.append(" AND (CALLING_NUM > 0 OR CALLED_NUM > 0)");
            }
            if (obj!=null){
                List<Map<String,Object>> list = (List<Map<String, Object>>) obj;
                for (Map<String, Object> map : list) {
                    String key = (String) map.get("key");
                    if (key.indexOf(eventEnum.name())!= -1 && key.indexOf("CALLING")==-1){
                        if (!key.equals(eventWhereEnum.name())){
                            String operator = (String) map.get("operator");
                            Object value = map.get("value");
                            if (operator!=null && value!=null){
                                joinWhereSql(sb, key, operator, value);
                            }
                        }

                    }else if ("CALLING".equals(eventEnum.name())){
                        if (!key.equals("CALLING_NUM") && !key.equals("CALLED_NUM")){
                            if (key.indexOf("CALLING")!=-1 || (key.indexOf("CALLED")!=-1 && key.indexOf("CALLED_SMS")==-1)){
                                String operator = (String) map.get("operator");
                                Object value = map.get("value");
                                if (operator!=null && value!=null){
                                    joinWhereSql(sb, key, operator, value);
                                }
                            }
                        }
                    }
                    if ("TIME_ID".equals(key)){
                        String operator = (String) map.get("operator");
                        Object value = map.get("value");
                        if (operator!=null && value!=null){
                            paraList.add(key);
                            joinWhereSql(sb, key, operator, value);
                        }
                    }else if ("PROV_ID".equals(key)){
                        String operator = (String) map.get("operator");
                        Object value = map.get("value");
                        if (operator!=null && value!=null){
                            paraList.add(key);
                            joinWhereSql(sb, key, operator, value);
                        }
                    }else if("POI_CLS_CODE".equals(key)){
                        String operator = (String) map.get("operator");
                        Object value = map.get("value");
                        if (operator!=null && value!=null){
                            paraList.add(key);
                            joinWhereSql(sb, key, operator, value);
                        }
                    }
                }
                return paraListMandatoryParam(paraList,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @Author Lijl
     * @MethodName joinWhereSql
     * @Description 拼接条件sql
     * @Date 10:40 2020/12/4
     * @Version 1.0
     * @param sb
     * @param key
     * @param operator
     * @param value
     * @return: void
    **/
    private void joinWhereSql(StringBuilder sb, String key, String operator, Object value) {
        if (!"between".equals(operator)){
            if (value instanceof String){
                sb.append(" AND "+key+operator+"\""+value+"\"");
            }else {
                sb.append(" AND "+key+operator+value);
            }
        }else{
            String[] val = ((String) value).split(",");
            sb.append(" AND "+key+">"+Integer.parseInt(val[0])+" AND "+Integer.parseInt(val[1])+">"+key);
        }
    }


    /**
     * @Author Lijl
     * @MethodName deal
     * @Description 执行相关业务
     * @Date 10:41 2020/12/4
     * @Version 1.0
     * @param tabName
     * @param conStr
     * @param methodNameType
     * @return: com.embraces.hive.util.BaseResult<?>
    **/
    private BaseResult deal(String tabName,String conStr,String methodNameType) throws InterruptedException {
        AtomicBoolean flagBol = new AtomicBoolean(true);
        String repCode = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
        String msg = "成功";
        int code = 200;
        asyncServiceExecutor.execute(()->{
            try {
                String restStr = this.executes(conStr,tabName,dataSourceConfig.url,"100".equals(dataSourceConfig.separator)?"Ж":dataSourceConfig.separator);
                String dateStr = getDateStr();
                String fileName = methodNameType+"_"+dateStr+"_"+repCode+".txt";
                String path = "";
                if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
                    path = csvFilePath.winCsvPath;
                } else {
                    path = csvFilePath.locaCsvPath;
                }
                String filepath = path+fileName;
                boolean ret = WriterFileUtil.createCsvFile(restStr, filepath);
                if (!ret){
                    log.info("接口{}写文件失败",methodNameType);
                    flagBol.set(false);
                }
            }catch (Exception e){
                e.printStackTrace();
                log.error("接口{}异常:{}",methodNameType,e.getMessage());
                flagBol.set(false);
            }
        });
        Thread.sleep(reqTimeOutConfig.time_out * 1000);
        return new BaseResult(flagBol.get() ==true?code:500, flagBol.get() ==true?msg:"失败", flagBol.get() ==true?repCode:null);
    }

    /**
     * @Author Lijl
     * @MethodName checkParameter
     * @Description 校验参数是否存在
     * @Date 14:59 2020/10/29
     * @Version 1.0
     * @param paramMap
     * @param list
     * @return: boolean
    **/
    private boolean checkParameter(Map<String,Object> paramMap,List<String> list,StringBuilder sb){
        List<String> paraList = new ArrayList<>();
        Object obj = paramMap.get("PARAMLIST");
        if (obj!=null){
            List<Map<String,Object>> param = (List<Map<String, Object>>) obj;
            param.forEach(map -> {
                String key = (String) map.get("key");
                String operator = (String) map.get("operator");
                Object value = map.get("value");
                boolean flBol = false;
                if (key!=null&&!"".equals(key) && operator!=null && !"".equals(operator) && value!=null){
                    flBol = true;
                }
                if (flBol){
                    paraList.add(key);
                    joinWhereSql(sb, key, operator, value);
                }
            });
            return paraListMandatoryParam(paraList,list);
        }
        return false;
    }



    public boolean paraListMandatoryParam(List<String> paraList,List<String> list){
        if (list!=null){
            return paraList.containsAll(list);
        }
        return paraList.containsAll(MandatoryParamUtils.getTvEventParam());
    }

    /**
     * @Author Lijl
     * @MethodName getDateStr
     * @Description 获取时间串
     * @Date 14:21 2020/10/29
     * @Version 1.0
     * @param
     * @return: java.lang.String
    **/
    private String getDateStr(){
        String dataStr = "";
        LocalDateTime dateTime = LocalDateTime.now();
        String[] dataTimeArr = dateTime.toString().split("T");
        String[] data = dataTimeArr[0].split("-");
        dataStr += data[0]+data[1]+data[2];
        String[] timeArr = dataTimeArr[1].split(":");
        dataStr += timeArr[0]+timeArr[1]+timeArr[2].split("\\.")[0];
        return dataStr;
    }
}
