package com.embraces.hive.service.impl.hbase;

import com.embraces.hive.annotation.ServiceCode;
import com.embraces.hive.model.hbase.TvMEvntNlInduAll;
import com.embraces.hive.model.hbase.TvMEvntNlInduAllSum;
import com.embraces.hive.template.hbse.AbstractEvntTemplate;
import com.embraces.hive.util.BaseResult;
import com.embraces.hive.util.HBaseServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Lijl
 * @ClassName TvMEvntAllServiceImpl
 * @Description TODO
 * @Date 2020/11/24 20:04
 * @Version 1.0
 */
@ServiceCode(value = "tv_m")
public class TvMEvntAllServiceImpl extends AbstractEvntTemplate {

    @Override
    protected BaseResult execute(String rowKey, String tabName, String methodType,org.apache.hadoop.conf.Configuration configuration) throws Exception {
        HBaseServiceUtil hBaseServiceUtil = new HBaseServiceUtil(configuration);
        List<Map<String,Object>> mapList = hBaseServiceUtil.getNumRegexRow("DWA_HW:"+tabName, rowKey);
        List<Object> list = new ArrayList<>();
        if (mapList!=null && mapList.size()>0){
            for (Map<String, Object> map : mapList) {
                Object obj = null;
                if ("s_0003".equals(methodType)){
                    obj = super.invoteSetter(TvMEvntNlInduAllSum.class, map);
                }else if ("s_0004".equals(methodType)){
                    obj = super.invoteSetter(TvMEvntNlInduAll.class, map);
                }
                if (obj!=null){
                    list.add(obj);
                }
            }
        }
        return BaseResult.ok("成功",list);
    }
}
