package com.embraces.hive.service.impl.hive;

import com.embraces.hive.annotation.ServiceCode;
import com.embraces.hive.model.hive.HiveTableEnum;
import com.embraces.hive.model.hive.TvDSumSkInduApp;
import com.embraces.hive.model.hive.TvMSumSkInduAll;
import com.embraces.hive.template.hive.AbstractTvTemplate;
import com.embraces.hive.util.JdbcUtils;

import java.util.List;

/**
 * @Author Lijl
 * @ClassName TvMAllServiceImpl
 * @Description 全行业（月累计）
 * @Date 2020/11/23 14:43
 * @Version 1.0
 */
@ServiceCode(value = "m_all")
public class TvMAllServiceImpl extends AbstractTvTemplate {

    @Override
    protected String executes(String conStr, HiveTableEnum hiveTableEnum, String jdbcUrl, String separator) throws Exception {
        JdbcUtils jdbcUtils = new JdbcUtils();
        StringBuffer sb = new StringBuffer("SELECT SERV_NUMBER,IMEI FROM "+hiveTableEnum+" WHERE 1=1");
        sb.append(conStr);
        List<TvMSumSkInduAll> list1 = jdbcUtils.executeQueryList(jdbcUrl,"","",sb.toString(), TvMSumSkInduAll.class);
        StringBuffer stringBuilder = new StringBuffer("SERV_NUMBER"+separator+"IMEI\r\n");
        if (list1!=null&&list1.size()>0){
            list1.forEach(td -> {
                String serv_number = td.getSERV_NUMBER();
                String imei = td.getIMEI();
                String sn = "";
                try {
                    sn = bdiDecnew(serv_number);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String im = "";
                try {
                    im = bdiEncrypt(imei);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                stringBuilder.append(sn+separator+im+"\r\n");
            });
        }
        return stringBuilder.toString();
    }
}
