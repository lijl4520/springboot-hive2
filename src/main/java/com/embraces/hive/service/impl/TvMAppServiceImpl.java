package com.embraces.hive.service.impl;

import com.embraces.hive.annotation.ServiceCode;
import com.embraces.hive.model.HiveTableEnum;
import com.embraces.hive.model.TvMSumSkInduApp;
import com.embraces.hive.template.AbstractTvTemplate;
import com.embraces.hive.util.JdbcUtils;

import java.util.List;

/**
 * @Author Lijl
 * @ClassName TvMAppServiceImpl
 * @Description TODO
 * @Date 2020/10/28 10:11
 * @Version 1.0
 */
@ServiceCode(value = "m_app")
public class TvMAppServiceImpl extends AbstractTvTemplate {

    /**
     * @Author Lijl
     * @MethodName executes
     * @Description APP事件（月）
     * @Date 11:35 2020/10/28
     * @Version 1.0
     * @param conStr
     * @param hiveTableEnum
     * @param jdbcUrl
     * @return: java.lang.String
    **/
    @Override
    protected String executes(String conStr, HiveTableEnum hiveTableEnum, String jdbcUrl, String separator) throws Exception {
        JdbcUtils jdbcUtils = new JdbcUtils();
        StringBuffer sb = new StringBuffer("SELECT SERV_NUMBER,IMEI FROM "+hiveTableEnum+" WHERE 1=1");
        sb.append(conStr);
        List<TvMSumSkInduApp> list1 = jdbcUtils.executeQueryList(jdbcUrl, "", "", sb.toString(), TvMSumSkInduApp.class, null);
        StringBuffer stringBuilder = new StringBuffer("SERV_NUMBER"+separator+"IMEI\r\n");
        if (list1!=null&&list1.size()>0){
            list1.forEach(td -> {
                String serv_number = td.getSERV_NUMBER();
                String sn = "";
                if (serv_number!=null && !"".equals(serv_number)){
                    try {
                        sn = bdiDecnew(serv_number);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                String imei = td.getIMEI() != null ? td.getIMEI() : "";
                String im = "";
                if (imei != null && !"".equals(imei)){
                    try {
                        im = bdiDecnew(imei);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                stringBuilder.append(sn+separator+im+"\r\n");
            });
        }
        return stringBuilder.toString();
    }
}
