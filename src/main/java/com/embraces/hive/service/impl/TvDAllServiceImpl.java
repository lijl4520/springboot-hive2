package com.embraces.hive.service.impl;

import com.embraces.hive.annotation.ServiceCode;
import com.embraces.hive.model.HiveTableEnum;
import com.embraces.hive.model.TvDSumSkInduApp;
import com.embraces.hive.template.AbstractTvTemplate;
import com.embraces.hive.util.JdbcUtils;

import java.util.List;

/**
 * @Author Lijl
 * @ClassName TvDAllServiceImpl
 * @Description 全行业（日累计）
 * @Date 2020/11/23 14:42
 * @Version 1.0
 */
@ServiceCode(value = "d_all")
public class TvDAllServiceImpl extends AbstractTvTemplate {
    @Override
    protected String executes(String conStr, HiveTableEnum hiveTableEnum, String jdbcUrl, String separator) throws Exception {
        JdbcUtils jdbcUtils = new JdbcUtils();
        StringBuffer sb = new StringBuffer("SELECT SERV_NUMBER "+hiveTableEnum+" WHERE 1=1");
        sb.append(conStr);
        List<TvDSumSkInduApp> list1 = jdbcUtils.executeQueryList(jdbcUrl, "", "", sb.toString(), TvDSumSkInduApp.class, null);
        StringBuffer stringBuilder = new StringBuffer("SERV_NUMBER\r\n");
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
                stringBuilder.append(sn+"\r\n");
            });
        }
        return stringBuilder.toString();
    }
}
