package com.embraces.hive.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.embraces.hive.annotation.ServiceCode;
import com.embraces.hive.model.HiveTableEnum;
import com.embraces.hive.model.TvMEvntNlInduAll;
import com.embraces.hive.model.TwDtEvntNlInduAll;
import com.embraces.hive.template.AbstractEvntTemplate;
import com.embraces.hive.util.BaseResult;
import com.embraces.hive.util.HBaseServiceUtil;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Date;
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
    protected BaseResult<?> execute(List<Map<String,String>> condition, HiveTableEnum hiveTableEnum, String methodType) throws Exception {
        List<Map<String, Object>> mapList = hBaseServiceUtil.selectTableDataByFilter("DWA_HW:" + hiveTableEnum, "SERVERSS", condition, true);
        if (mapList!=null && mapList.size()>0){
            List<TvMEvntNlInduAll> list = new ArrayList<>();
            for (Map<String, Object> map : mapList) {
                TvMEvntNlInduAll tvMEvntNlInduAll = (TvMEvntNlInduAll) super.invoteSetter(TvMEvntNlInduAll.class, map);
                list.add(tvMEvntNlInduAll);
            }
            if (list.size()>0){
                return new BaseResult<List<TvMEvntNlInduAll>>(200,"成功",list);
            }
        }
        return new BaseResult<List<TvMEvntNlInduAll>>(500,"失败",null);
    }
}
