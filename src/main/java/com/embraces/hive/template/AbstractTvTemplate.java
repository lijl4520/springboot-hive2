package com.embraces.hive.template;

import com.embraces.hive.config.CsvFilePath;
import com.embraces.hive.config.DataSourceConfig;
import com.embraces.hive.convert.Decnew;
import com.embraces.hive.model.HiveTableEnum;
import com.embraces.hive.service.TvService;
import com.embraces.hive.util.BaseResult;
import com.embraces.hive.util.DesEncryptUtil;
import com.embraces.hive.util.SFTPUtils;
import com.embraces.hive.util.WriterFileUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
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
     * @param conStrArr
     * @param hiveTableEnum
     * @param jdbcUrl
     * @exception throw Exception
     * @return: java.lang.String
    **/
    protected abstract String executes(String[] conStrArr,HiveTableEnum hiveTableEnum,String jdbcUrl,String separator) throws Exception;

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
    public BaseResult<?> deal(String condition, String methodNameType) {
        String repCode = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
        HiveTableEnum hiveTableEnum = HiveTableEnum.fromTypeName(methodNameType);
        String[] conStrArr = condition.split("&");
        boolean checkParBol = false;
        log.info("开始校验接口[{}]参数",methodNameType);
        if ("T_DIM_MD_INDUSTRY_CONT_CODE".equals(hiveTableEnum.name())){
            List<String> parList = new ArrayList<>();
            parList.add("time_id");
            checkParBol = checkParameter(conStrArr,parList);
        }else{
            checkParBol = checkParameter(conStrArr,null);
        }
        log.info("校验接口[{}]结果：{}",methodNameType,checkParBol);
        if (checkParBol){
            return deal(repCode,hiveTableEnum,conStrArr,methodNameType);
        }
        return new BaseResult<>(400,"参数缺失",repCode);
    }


    private BaseResult<?> deal(String repCode,HiveTableEnum hiveTableEnum,String[] conStrArr,String methodNameType){
        SFTPUtils sftpUtils = null;
        String msg = "成功";
        int code = 200;
        try {
            String restStr = this.executes(conStrArr,hiveTableEnum,dataSourceConfig.url,"100".equals(dataSourceConfig.separator)?"Ж":dataSourceConfig.separator);
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
        return new BaseResult<>(code,msg,repCode);
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
    private boolean checkParameter(String[] conStrArr,List<String> list){
        List<String> dataSourList = new ArrayList<>();
        dataSourList.add("TIME_ID");
        dataSourList.add("HOME_PROV_ID");
        dataSourList.add("POI_CLS_CODE");
        List<String> paraList = new ArrayList<>();
        for (String con : conStrArr) {
            try {
                String p = con.substring(0, con.indexOf("="));
                paraList.add(p);
            }catch (Exception e){
                log.info("过滤非校验条件");
            }
        }
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
