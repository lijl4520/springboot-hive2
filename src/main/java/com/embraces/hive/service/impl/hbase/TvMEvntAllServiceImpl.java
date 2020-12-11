package com.embraces.hive.service.impl.hbase;

import com.embraces.hive.annotation.ServiceCode;
import com.embraces.hive.model.hbase.HbaseTableEnum;
import com.embraces.hive.model.hbase.TvMEvntNlInduAll;
import com.embraces.hive.model.hbase.TvMEvntNlInduAllSum;
import com.embraces.hive.model.hive.HiveTableEnum;
import com.embraces.hive.template.hbse.AbstractEvntTemplate;
import com.embraces.hive.util.BaseResult;
import com.embraces.hive.util.HBaseServiceUtil;

import javax.annotation.Resource;
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

    @Resource
    private HBaseServiceUtil hBaseServiceUtil;

    @Override
    protected BaseResult<?> execute(String rowKey, HbaseTableEnum hiveTableEnum, String methodType) throws Exception {
        //Map<String, Object> map = hBaseServiceUtil.selectOneRowDataMap("DWA_HW:"+hiveTableEnum, rowKey);
        List<Map<String,Object>> mapList = hBaseServiceUtil.selectTableDataByRowFilter("DWA_HW:"+hiveTableEnum, rowKey);
        List<Object> list = new ArrayList<>();
        if (mapList!=null && mapList.size()>0){
            for (Map<String, Object> map : mapList) {
                Object obj = null;
                if ("s_0003".equals(methodType)){
                    obj = super.invoteSetter(TvMEvntNlInduAllSum.class, map);
                }else{
                    obj = super.invoteSetter(TvMEvntNlInduAll.class, map);
                }
                if (obj!=null){
                    list.add(obj);
                }
            }
        }
        return new BaseResult<>(200,"成功",list);
    }
}
