package com.embraces.hive.service.impl.hbase;

import com.embraces.hive.annotation.ServiceCode;
import com.embraces.hive.model.hbase.TwAppEvntYn;
import com.embraces.hive.model.hbase.TwSearchEvntYn;
import com.embraces.hive.template.hbse.AbstractEvntTemplate;
import com.embraces.hive.util.BaseResult;
import com.embraces.hive.util.HBaseServiceUtil;
import org.apache.hadoop.conf.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Lijl
 * @ClassName TwSearchEvntYnImpl
 * @Description 关键词搜索
 * @Date 2021/5/7 16:17
 * @Version 1.0
 */
@ServiceCode(value = "tw_search_yn")
public class TwSearchEvntYnImpl extends AbstractEvntTemplate {
    @Override
    protected BaseResult execute(String rowKey, String tabName, String methodNameType, Configuration conf) throws Exception {
        HBaseServiceUtil hBaseServiceUtil = new HBaseServiceUtil(conf);
        String tableName = "DWA_HW:" + tabName;
        int i1 = rowKey.length() - 8;
        int i = Integer.parseInt(rowKey.substring(i1, rowKey.length())) + 1;
        String keyStr = rowKey.substring(0, i1);
        List<Map<String,Object>> mapList = hBaseServiceUtil.getNumRegexRow(tableName, rowKey,keyStr+i);
        List<Object> list = new ArrayList<>();
        if (mapList!=null && mapList.size()>0){
            for (Map<String, Object> map : mapList) {
                Object obj = super.invoteSetter(TwSearchEvntYn.class, map);
                if (obj!=null){
                    list.add(obj);
                }
            }
        }
        return BaseResult.ok("成功",list);
    }
}
