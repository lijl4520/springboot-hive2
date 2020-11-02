package com.embraces.hive.service.impl;

import com.embraces.hive.annotation.ServiceCode;
import com.embraces.hive.model.HiveTableEnum;
import com.embraces.hive.model.TvMSumSkInduUrl;
import com.embraces.hive.template.AbstractTvTemplate;
import com.embraces.hive.util.JdbcUtils;

import java.util.List;

/**
 * @Author Lijl
 * @ClassName TvMUrlServiceImpl
 * @Description TODO
 * @Date 2020/10/28 10:03
 * @Version 1.0
 */
@ServiceCode(value = "m_url")
public class TvMUrlServiceImpl extends AbstractTvTemplate {

    /**
     * @Author Lijl
     * @MethodName executes
     * @Description URL事件（月）
     * @Date 11:35 2020/10/28
     * @Version 1.0
     * @param conStrArr
     * @param hiveTableEnum
     * @param jdbcUrl
     * @return: java.lang.String
    **/
    @Override
    protected String executes(String[] conStrArr, HiveTableEnum hiveTableEnum, String jdbcUrl, String separator) throws Exception {
        JdbcUtils jdbcUtils = new JdbcUtils();
        StringBuilder sb = new StringBuilder("SELECT SERV_NUMBER,IMEI FROM "+hiveTableEnum+" WHERE 1=1");
        for (String con : conStrArr) {
            sb.append(" AND "+con);
        }
        List<TvMSumSkInduUrl> list1 = jdbcUtils.executeQueryList(jdbcUrl, "", "", sb.toString(), TvMSumSkInduUrl.class, null);
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
