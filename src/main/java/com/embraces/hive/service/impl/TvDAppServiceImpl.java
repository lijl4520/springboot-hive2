package com.embraces.hive.service.impl;

import com.embraces.hive.annotation.ServiceCode;
import com.embraces.hive.model.HiveTableEnum;
import com.embraces.hive.model.TvDSumSkInduApp;
import com.embraces.hive.template.AbstractTvTemplate;
import com.embraces.hive.util.JdbcUtils;

import java.util.List;

/**
 * @Author Lijl
 * @ClassName TvDAppServiceImpl
 * @Description TODO
 * @Date 2020/10/28 9:38
 * @Version 1.0
 */
@ServiceCode(value = "d_app")
public class TvDAppServiceImpl extends AbstractTvTemplate {

    /**
     * @Author Lijl
     * @MethodName executes
     * @Description APP事件（日）
     * @Date 11:33 2020/10/28
     * @Version 1.0
     * @param conStr
     * @param hiveTableEnum
     * @param jdbcUrl
     * @return: java.lang.String
    **/
    @Override
    protected String executes(String conStr, HiveTableEnum hiveTableEnum, String jdbcUrl,String separator) throws Exception {
        JdbcUtils jdbcUtils = new JdbcUtils();
        StringBuilder sb = new StringBuilder("SELECT SERV_NUMBER,IMEI FROM "+hiveTableEnum+" WHERE 1=1");
        sb.append(conStr);
        List<TvDSumSkInduApp> list1 = jdbcUtils.executeQueryList(jdbcUrl, "", "", sb.toString(), TvDSumSkInduApp.class, null);
        StringBuilder stringBuilder = new StringBuilder("SERV_NUMBER"+separator+"IMEI\r\n");
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
                stringBuilder.append(sn+separator+imei+"\r\n");
            });
        }
        return stringBuilder.toString();
    }
}
