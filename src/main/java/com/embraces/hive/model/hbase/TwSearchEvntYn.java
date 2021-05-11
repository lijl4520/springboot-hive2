package com.embraces.hive.model.hbase;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @Author Lijl
 * @ClassName TwSearchEvntYn
 * @Description 关键词维度模型
 * @Date 2021/5/7 15:35
 * @Version 1.0
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,getterVisibility = JsonAutoDetect.Visibility.NONE)
public class TwSearchEvntYn {
    private String REG_ID;//区域编码
    private String REG_NAME;//区域名称
    private String SRH_WORD;//搜索关键字
    private String SRH_HOT;//搜索排名
    private Integer SRH_CNT;//搜索次数
    private Integer SRH_CUST;//搜索人数
    private String STAT_DAY;//统计日期

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

    public String getSRH_WORD() {
        return SRH_WORD;
    }

    public void setSRH_WORD(String SRH_WORD) {
        this.SRH_WORD = SRH_WORD;
    }

    public String getSRH_HOT() {
        return SRH_HOT;
    }

    public void setSRH_HOT(String SRH_HOT) {
        this.SRH_HOT = SRH_HOT;
    }

    public Integer getSRH_CNT() {
        return SRH_CNT;
    }

    public void setSRH_CNT(Integer SRH_CNT) {
        this.SRH_CNT = SRH_CNT;
    }

    public Integer getSRH_CUST() {
        return SRH_CUST;
    }

    public void setSRH_CUST(Integer SRH_CUST) {
        this.SRH_CUST = SRH_CUST;
    }

    public String getSTAT_DAY() {
        return STAT_DAY;
    }

    public void setSTAT_DAY(String STAT_DAY) {
        this.STAT_DAY = STAT_DAY;
    }
}
