package com.embraces.hive.service.impl.hive;

import com.embraces.hive.annotation.ServiceCode;
import com.embraces.hive.model.hive.TvDSumSkInduAll;
import com.embraces.hive.template.hive.AbstractTvTemplate;
import com.embraces.hive.util.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author Lijl
 * @ClassName TvDAllServiceImpl
 * @Description 全行业查询实现
 * @Date 2020/11/23 14:42
 * @Version 1.0
 */
@ServiceCode(value = "tv_event_nl_indu")
public class TvEventNlInduServiceImpl extends AbstractTvTemplate {

    private static Logger log = LoggerFactory.getLogger(TvEventNlInduServiceImpl.class);

    /**
     * @Author Lijl
     * @MethodName executes
     * @Description 全行业模型数据查询
     * @Date 2020/11/23 14:50
     * @Version 1.0
     * @param conStr
     * @param tabName
     * @param jdbcUrl
     * @param separator
     * @return: java.lang.String
    **/
    @Override
    protected String executes(String conStr, String tabName, String jdbcUrl, String separator) throws Exception {
        JdbcUtils jdbcUtils = new JdbcUtils();
        StringBuffer sb = new StringBuffer("SELECT SERV_NUMBER,IMEI FROM "+tabName+" WHERE 1=1");
        sb.append(conStr);
        List<TvDSumSkInduAll> list1 = jdbcUtils.executeQueryList(jdbcUrl,"","",sb.toString(), TvDSumSkInduAll.class);
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
                    log.error("加解密异常{}",e.getMessage());
                }
                String im = "";
                try {
                    im = bdiEncrypt(imei);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("加解密异常{}",e.getMessage());
                }
                stringBuilder.append(sn+separator+im+"\r\n");
            });
        }
        return stringBuilder.toString();
    }
}
