package com.embraces.hive.service.impl;

import com.embraces.hive.annotation.ServiceCode;
import com.embraces.hive.model.HiveTableEnum;
import com.embraces.hive.model.TDimMd;
import com.embraces.hive.template.AbstractTvTemplate;
import com.embraces.hive.util.JdbcUtils;

import java.util.List;

/**
 * @Author Lijl
 * @ClassName TvDimServiceImpl
 * @Description TODO
 * @Date 2020/10/27 16:47
 * @Version 1.0
 */
@ServiceCode(value = "dim")
public class TvDimServiceImpl extends AbstractTvTemplate {

    /**
     * @Author Lijl
     * @MethodName executes
     * @Description 维表
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
        StringBuffer sb = new StringBuffer("SELECT poi_cls_code,poi_cls_name,poi_cls1_code,poi_cls2_code,poi_cls3_code,poi_cls4_code,poi_cls5_code,poi_cls1,poi_cls2,poi_cls3,poi_cls4,poi_cls5 FROM "+hiveTableEnum+" WHERE 1 = 1");
        sb.append(conStr);
        List<TDimMd> list1 = jdbcUtils.executeQueryList(jdbcUrl, "", "", sb.toString(), TDimMd.class, null);

        StringBuffer stringBuilder = new StringBuffer("poi_cls_code"+separator+"poi_cls_name"+separator+"poi_cls1_code"+separator+"poi_cls2_code"+separator+"poi_cls3_code"+separator+"poi_cls4_code"+separator+"poi_cls5_code"+separator+"poi_cls1"+separator+"poi_cls2"+separator+"poi_cls3"+separator+"poi_cls4"+separator+"poi_cls5\r\n");
        if (list1!=null){
            list1.forEach(td->{
                String cls_code = td.getPoi_cls_code() != null ? td.getPoi_cls_code() : "";
                String cls_name = td.getPoi_cls_name() != null ? td.getPoi_cls_name() : "";
                String cls1_code = td.getPoi_cls1_code() != null ? td.getPoi_cls1_code() : "";
                String cls2_code = td.getPoi_cls2_code() != null ? td.getPoi_cls2_code() : "";
                String cls3_code = td.getPoi_cls3_code() != null ? td.getPoi_cls3_code() : "";
                String cls4_code = td.getPoi_cls4_code() != null ? td.getPoi_cls4_code() : "";
                String cls5_code = td.getPoi_cls5_code() != null ? td.getPoi_cls5_code() : "";
                String poi_cls1 = td.getPoi_cls1() != null ? td.getPoi_cls1() : "";
                String poi_cls2 = td.getPoi_cls2() != null ? td.getPoi_cls2() : "";
                String poi_cls3 = td.getPoi_cls3() != null ? td.getPoi_cls3() : "";
                String poi_cls4 = td.getPoi_cls4() != null ? td.getPoi_cls4() : "";
                String poi_cls5 = td.getPoi_cls5() != null ? td.getPoi_cls5() : "";
                stringBuilder.append(cls_code+separator+cls_name+separator+
                        cls1_code+separator+cls2_code+separator+cls2_code+separator+cls3_code+separator+
                        cls4_code+separator+cls5_code+separator+poi_cls1+separator+poi_cls2+separator+
                        poi_cls3+separator+poi_cls4+separator+poi_cls5+"\r\n");
            });
        }
        return stringBuilder.toString();
    }
}
