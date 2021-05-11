package com.embraces.hive.service.impl.hbase;

import com.embraces.hive.annotation.ServiceCode;
import com.embraces.hive.model.hbase.ToMEvntNlUserFlux;
import com.embraces.hive.model.hbase.TwAppEvntYn;
import com.embraces.hive.template.hbse.AbstractEvntTemplate;
import com.embraces.hive.util.BaseResult;
import com.embraces.hive.util.HBaseServiceUtil;
import org.apache.hadoop.conf.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Lijl
 * @ClassName TwAppEvntYnImpl
 * @Description APPtop10
 * @Date 2021/5/7 14:44
 * @Version 1.0
 */
@ServiceCode(value = "tw_app_yn")
public class TwAppEvntYnImpl extends AbstractEvntTemplate {
    @Override
    protected BaseResult execute(String rowKey, String tabName, String methodNameType, Configuration conf) throws Exception {
        HBaseServiceUtil hBaseServiceUtil = new HBaseServiceUtil(conf);
        String tableName = "DWA_HW:" + tabName;
        List<Map<String,Object>> mapList = hBaseServiceUtil.getNumRegexRow(tableName, rowKey,rowKey+"a");
        List<Object> list = new ArrayList<>();
        if (mapList!=null && mapList.size()>0){
            for (Map<String, Object> map : mapList) {
                Object obj = super.invoteSetter(TwAppEvntYn.class, map);
                if (obj!=null){
                    list.add(obj);
                }
            }
        }
        return BaseResult.ok("成功",list);
    }
}
