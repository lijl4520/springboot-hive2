package com.embraces.hive.template;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.embraces.hive.config.CsvFilePath;
import com.embraces.hive.config.DataSourceConfig;
import com.embraces.hive.convert.Decnew;
import com.embraces.hive.model.HiveTableEnum;
import com.embraces.hive.service.TvService;
import com.embraces.hive.util.*;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * @Author Lijl
     * @MethodName executes
     * @Description 查询具体数据
     * @Date 11:31 2020/10/28
     * @Version 1.0
     * @param conStr
     * @param hiveTableEnum
     * @param jdbcUrl
     * @exception throw Exception
     * @return: java.lang.String
    **/
    protected abstract String executes(String conStr,HiveTableEnum hiveTableEnum,String jdbcUrl,String separator) throws Exception;

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
        String dateStr = LocalDate.now().toString().replaceAll("-","");
        String decnew = Decnew.decnew(dateStr, serv_number, dataSourceConfig.authSrvUrl, "utf-8");
        if (decnew!=null && !"".equals(decnew)){
            return DesEncryptUtil.encrypt(decnew.getBytes());
        }
        return "";
    }

    /**
     * @Author Lijl
     * @MethodName deal
     * @Description 公共模板方法
     * @Date 11:32 2020/10/28
     * @Version 1.0
     * @param condition
     * @param methodNameType
     * @return: com.embraces.hive.util.BaseResult<?>
    **/
    @Override
    public BaseResult<?> deal(JSONArray condition, String methodNameType, HttpServletResponse response) throws UnsupportedEncodingException {
        HiveTableEnum hiveTableEnum = HiveTableEnum.fromTypeName(methodNameType);
        boolean checkParBol = false;
        log.info("开始校验接口[{}]参数",methodNameType);
        StringBuffer sb = new StringBuffer();
        if ("T_DIM_MD_INDUSTRY_CONT_CODE".equals(hiveTableEnum.name())){
            List<String> parList = new ArrayList<>();
            parList.add("time_id");
            checkParBol = checkParameter(condition,parList, sb);
        }else{
            checkParBol = checkParameter(condition,null, sb);
        }
        log.info("校验接口[{}]结果：{}",methodNameType,checkParBol);
        if (checkParBol){
            if (sb!=null){
                return deal(hiveTableEnum,sb.toString(),methodNameType,response);
            }
            response.addHeader("responseCode","2001");
            response.addHeader("responseMsg","fail");
            return new BaseResult<>(500,"查询条件缺失",null) ;
        }
        response.addHeader("responseCode","2001");
        response.addHeader("responseMsg","fail");
        return new BaseResult<>(500,"参数缺失",null);
    }


    private BaseResult<?> deal(HiveTableEnum hiveTableEnum,String conStr,String methodNameType, HttpServletResponse response) throws UnsupportedEncodingException {
        String repCode = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
        SFTPUtils sftpUtils = null;
        String msg = "成功";
        int code = 200;
        try {
            String restStr = this.executes(conStr,hiveTableEnum,dataSourceConfig.url,"100".equals(dataSourceConfig.separator)?"Ж":dataSourceConfig.separator);
            String dateStr = getDateStr();
            String fileName = methodNameType+"_"+dateStr+"_"+repCode+".txt";
            String filepath = csvFilePath.locaCsvPath+fileName;
            boolean ret = WriterFileUtil.createCsvFile(restStr, filepath);
            if (ret){
                if (csvFilePath.is_sftp){
                    log.info("上传文件至SFTP服务器");
                    sftpUtils = new SFTPUtils(csvFilePath.host,csvFilePath.port,csvFilePath.account,csvFilePath.password);
                    sftpUtils.uploadFile(csvFilePath.remotePath,fileName,csvFilePath.locaCsvPath,fileName);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("异常:{}",e.getMessage());
            msg = "失败";
            code = 500;
        }finally {
            if (sftpUtils!=null){
                sftpUtils.disconnect();
            }
        }
        if (code==500){
            response.addHeader("responseCode","2001");
            response.addHeader("responseMsg",  "fail");
        }else{
            response.addHeader("responseCode","0000");
            response.addHeader("responseMsg","success");
        }
        return new BaseResult<String>(code,msg,code==200?repCode:null);
    }

    /**
     * @Author Lijl
     * @MethodName checkParameter
     * @Description 校验参数是否存在
     * @Date 14:59 2020/10/29
     * @Version 1.0
     * @param conStrArr
     * @param list
     * @return: boolean
    **/
    private boolean checkParameter(JSONArray conStrArr,List<String> list,StringBuffer sb){
        List<String> dataSourList = new ArrayList<>();
        dataSourList.add("TIME_ID");
        dataSourList.add("HOME_PROV_ID");
        dataSourList.add("POI_CLS_CODE");
        List<String> paraList = new ArrayList<>();
        conStrArr.forEach(o -> {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(o);
            String key = jsonObject.getString("key");
            String operator = jsonObject.getString("operator");
            Object value = jsonObject.get("value");
            boolean flBol = false;
            if (key!=null&&!"".equals(key) && operator!=null && !"".equals(operator) && value!=null){
                flBol = true;
            }
            if (flBol){
                paraList.add(key);
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
        });
        if (list!=null){
            return paraList.containsAll(list);
        }
        return paraList.containsAll(dataSourList);
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
