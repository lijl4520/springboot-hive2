package com.embraces.hive.service.impl.hbase;

import com.embraces.hive.annotation.ServiceCode;
import com.embraces.hive.model.hbase.TwDtEvntNlInduAll;
import com.embraces.hive.model.hbase.TwDtEvntNlInduAllSum;
import com.embraces.hive.template.hbse.AbstractEvntTemplate;
import com.embraces.hive.util.BaseResult;
import com.embraces.hive.util.HBaseServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Lijl
 * @ClassName TwDtEvntAllServiceImpl
 * @Description TODO
 * @Date 2020/11/24 20:02
 * @Version 1.0
 */
@ServiceCode(value = "tw_dt")
public class TwDtEvntAllServiceImpl extends AbstractEvntTemplate {

    private static Logger log = LoggerFactory.getLogger(TwDtEvntAllServiceImpl.class);

    @Override
    protected BaseResult<?> execute(String rowKey, String tabName, String methodType,org.apache.hadoop.conf.Configuration configuration) throws Exception {
        HBaseServiceUtil hBaseServiceUtil = new HBaseServiceUtil(configuration);
        List<Map<String, Object>> mapList = hBaseServiceUtil.getNumRegexRow("DWA_HW:"+tabName, rowKey);
        List<Object> list = new ArrayList<>();
        if (mapList!=null && mapList.size()>0){
            for (Map<String, Object> map : mapList) {
                Object obj = null;
                if ("s_0001".equals(methodType)){
                    obj = super.invoteSetter(TwDtEvntNlInduAllSum.class, map);
                }else if ("s_0002".equals(methodType)){
                    obj = super.invoteSetter(TwDtEvntNlInduAll.class, map);
                }
                if (obj!=null){
                    list.add(obj);
                }
            }
        }
        return new BaseResult<>(200,"成功",list);
    }
}
