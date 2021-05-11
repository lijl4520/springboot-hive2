package com.embraces.hive.service.impl.hbase;

import com.embraces.hive.annotation.ServiceCode;
import com.embraces.hive.model.hbase.ToMEvntNlUserFlux;
import com.embraces.hive.template.hbse.AbstractEvntTemplate;
import com.embraces.hive.util.BaseResult;
import com.embraces.hive.util.HBaseServiceUtil;
import org.apache.hadoop.conf.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Lijl
 * @ClassName ToMEvntNlUserServiceImpl
 * @Description 青海同步接口实现
 * @Date 2021/3/11 17:37
 * @Version 1.0
 */
@ServiceCode(value = "to_m")
public class ToMEvntNlUserServiceImpl extends AbstractEvntTemplate {

    @Override
    protected BaseResult execute(String rowKey, String tabName, String methodNameType, Configuration conf) throws Exception {
        HBaseServiceUtil hBaseServiceUtil = new HBaseServiceUtil(conf);
        String tableName = "DWA_HW:" + tabName;
        String[] rowkeyArr = rowKey.split(",");
        String startRowKey = rowkeyArr[0];
        String endRowKey = rowkeyArr[1] + "a";
        int dataCount = hBaseServiceUtil.getTableDataCount(tableName, "", startRowKey, endRowKey);
        if (dataCount>5000){
            return BaseResult.error("由于需响应的数据量太大，请按照分钟粒度段查询");
        }
        List<Map<String,Object>> mapList = hBaseServiceUtil.getNumRegexRow(tableName, startRowKey,endRowKey);
        List<Object> list = new ArrayList<>();
        if (mapList!=null && mapList.size()>0){
            for (Map<String, Object> map : mapList) {
                Object obj = super.invoteSetter(ToMEvntNlUserFlux.class, map);
                if (obj!=null){
                    list.add(obj);
                }
            }
        }
        return BaseResult.ok("成功",list);
    }
}
