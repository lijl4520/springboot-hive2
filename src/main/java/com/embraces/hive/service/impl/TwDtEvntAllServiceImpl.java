package com.embraces.hive.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.embraces.hive.annotation.ServiceCode;
import com.embraces.hive.model.HiveTableEnum;
import com.embraces.hive.model.TvMEvntNlInduAll;
import com.embraces.hive.model.TwDtEvntNlInduAll;
import com.embraces.hive.template.AbstractEvntTemplate;
import com.embraces.hive.util.BaseResult;
import com.embraces.hive.util.HBaseServiceUtil;
import com.embraces.hive.util.JdbcUtils;

import javax.annotation.Resource;
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

    @Resource
    private HBaseServiceUtil hBaseServiceUtil;

    @Override
    protected BaseResult<?> execute(List<Map<String,String>> condition, HiveTableEnum hiveTableEnum, String methodType) throws Exception {
        List<Map<String, Object>> mapList = hBaseServiceUtil.selectTableDataByFilter("DWA_HW:" + hiveTableEnum, "SERVERSS", condition, true);
        if (mapList!=null && mapList.size()>0){
            List<TwDtEvntNlInduAll> list = new ArrayList<>();
            for (Map<String, Object> map : mapList) {
                TwDtEvntNlInduAll twDtEvntNlInduAll = (TwDtEvntNlInduAll) super.invoteSetter(TwDtEvntNlInduAll.class, map);
                list.add(twDtEvntNlInduAll);
            }
            if (list.size()>0){
                return new BaseResult<List<TwDtEvntNlInduAll>>(200,"成功",list);
            }
        }
        return new BaseResult<List<TwDtEvntNlInduAll>>(500,"失败",null);
    }
}
