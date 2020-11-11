package com.embraces.hive.service.impl;

import com.embraces.hive.annotation.ServiceCode;
import com.embraces.hive.model.HiveTableEnum;
import com.embraces.hive.model.TvDSumSkInduUrl;
import com.embraces.hive.template.AbstractTvTemplate;
import com.embraces.hive.util.JdbcUtils;

import java.util.List;

/**
 * @Author Lijl
 * @ClassName TvDUrlServiceImpl
 * @Description TODO
 * @Date 2020/10/27 16:34
 * @Version 1.0
 */
@ServiceCode(value = "d_url")
public class TvDUrlServiceImpl extends AbstractTvTemplate {

    /**
     * @Author Lijl
     * @MethodName executes
     * @Description URL事件（日）
     * @Date 11:34 2020/10/28
     * @Version 1.0
     * @param conStr
     * @param hiveTableEnum
     * @param jdbcUrl
     * @return: java.lang.String
    **/
    @Override
    protected String executes(String conStr, HiveTableEnum hiveTableEnum,String jdbcUrl,String separator) throws Exception {
        JdbcUtils jdbcUtils = new JdbcUtils();
        StringBuffer sb = new StringBuffer("SELECT SERV_NUMBER,IMEI FROM "+hiveTableEnum+" WHERE 1=1");
        sb.append(conStr);
        List<TvDSumSkInduUrl> list1 = jdbcUtils.executeQueryList(jdbcUrl, "", "", sb.toString(), TvDSumSkInduUrl.class, null);
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
                stringBuilder.append(sn+separator+imei+"\r\n");
            });
        }
        return stringBuilder.toString();
    }
}
