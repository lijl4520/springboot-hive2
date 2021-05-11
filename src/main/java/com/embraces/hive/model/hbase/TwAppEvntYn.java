package com.embraces.hive.model.hbase;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @Author Lijl
 * @ClassName TwAppEvntYn
 * @Description APPtop10维度模型
 * @Date 2021/5/7 14:10
 * @Version 1.0
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,getterVisibility = JsonAutoDetect.Visibility.NONE)
public class TwAppEvntYn {
    private String REG_ID;//区域编码
    private String REG_NAME;//区域名称
    private String INDEX_ID;//维度编码
    private String INDEX_CODE;//维值编码
    private String INDEX_NAME;//维值名称
    private Integer CUST_CNT;//客流数
    private String CNT_RATIO;//占比
    private String STAT_DATE;//统计日期

    public String getREG_ID() {
        return REG_ID;
    }

    public void setREG_ID(String REG_ID) {
        this.REG_ID = REG_ID;
    }

    public String getREG_NAME() {
        return REG_NAME;
    }

    public void setREG_NAME(String REG_NAME) {
        this.REG_NAME = REG_NAME;
    }

    public String getINDEX_ID() {
        return INDEX_ID;
    }

    public void setINDEX_ID(String INDEX_ID) {
        this.INDEX_ID = INDEX_ID;
    }

    public String getINDEX_CODE() {
        return INDEX_CODE;
    }

    public void setINDEX_CODE(String INDEX_CODE) {
        this.INDEX_CODE = INDEX_CODE;
    }

    public String getINDEX_NAME() {
        return INDEX_NAME;
    }

    public void setINDEX_NAME(String INDEX_NAME) {
        this.INDEX_NAME = INDEX_NAME;
    }

    public Integer getCUST_CNT() {
        return CUST_CNT;
    }

    public void setCUST_CNT(Integer CUST_CNT) {
        this.CUST_CNT = CUST_CNT;
    }

    public String getCNT_RATIO() {
        return CNT_RATIO;
    }

    public void setCNT_RATIO(String CNT_RATIO) {
        this.CNT_RATIO = CNT_RATIO;
    }

    public String getSTAT_DATE() {
        return STAT_DATE;
    }

    public void setSTAT_DATE(String STAT_DATE) {
        this.STAT_DATE = STAT_DATE;
    }
}
