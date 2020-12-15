package com.embraces.hive.model.hbase;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.Serializable;

/**
 * @Author Lijl
 * @ClassName TwDtEventNlInduAll
 * @Description 全行业日统计
 * @Date 2020/11/24 18:59
 * @Version 1.0
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,getterVisibility = JsonAutoDetect.Visibility.NONE)
public class TwDtEvntNlInduAll implements Serializable {

    private String IMEI;
    private String POI_CLS_CODE;
    private String POI_CLS_NAME;
    private String POI_CLS1_CODE;
    private String POI_CLS2_CODE;
    private String POI_CLS3_CODE;
    private String POI_CLS4_CODE;
    private String POI_CLS5_CODE;
    private String POI_CLS1;
    private String POI_CLS2;
    private String POI_CLS3;
    private String POI_CLS4;
    private String POI_CLS5;
    private Double CALLING_DUR;
    private Integer CALLING_NUM;
    private Integer CALLING_CNT;
    private Integer CALLING_DAY;
    private Double CALLED_DUR;
    private Integer CALLED_NUM;
    private Integer CALLED_CNT;
    private Integer CALLED_DAY;
    private Integer CALLED_SMS_NUM;
    private Integer CALLED_SMS_CNT;
    private Integer CALLED_SMS_DAY;
    private Integer URL_VISIT_NUM;
    private Integer URL_VISIT_CNT;
    private Integer URL_ACTIVE_DAYS;
    private Integer URL_ONLINE_DUR;
    private Integer URL_FLUX;
    private Integer APP_VISIT_NUM;
    private Integer APP_VISIT_CNT;
    private Integer APP_ACTIVE_DAYS;
    private Integer APP_ONLINE_DUR;
    private Integer APP_FLUX;
    private Integer PUBLIC_VISIT_NUM;
    private Integer PUBLIC_VISIT_CNT;
    private Integer PUBLIC_ACTIVE_DAYS;
    private Integer PUBLIC_ONLINE_DUR;
    private Integer PUBLIC_FLUX;
    private Integer KEYWORD_VISIT_NUM;
    private Integer KEYWORD_VISIT_CNT;
    private Integer KEYWORD_ACTIVE_DAYS;
    private Integer KEYWORD_ONLINE_DUR;
    private Integer KEYWORD_FLUX;
    private Integer INDU_STAY_NUM;
    private Double INDU_STAY_DURATION;
    private Integer INDU_STAY_DAY;

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getPOI_CLS_CODE() {
        return POI_CLS_CODE;
    }

    public void setPOI_CLS_CODE(String POI_CLS_CODE) {
        this.POI_CLS_CODE = POI_CLS_CODE;
    }

    public String getPOI_CLS_NAME() {
        return POI_CLS_NAME;
    }

    public void setPOI_CLS_NAME(String POI_CLS_NAME) {
        this.POI_CLS_NAME = POI_CLS_NAME;
    }

    public String getPOI_CLS1_CODE() {
        return POI_CLS1_CODE;
    }

    public void setPOI_CLS1_CODE(String POI_CLS1_CODE) {
        this.POI_CLS1_CODE = POI_CLS1_CODE;
    }

    public String getPOI_CLS2_CODE() {
        return POI_CLS2_CODE;
    }

    public void setPOI_CLS2_CODE(String POI_CLS2_CODE) {
        this.POI_CLS2_CODE = POI_CLS2_CODE;
    }

    public String getPOI_CLS3_CODE() {
        return POI_CLS3_CODE;
    }

    public void setPOI_CLS3_CODE(String POI_CLS3_CODE) {
        this.POI_CLS3_CODE = POI_CLS3_CODE;
    }

    public String getPOI_CLS4_CODE() {
        return POI_CLS4_CODE;
    }

    public void setPOI_CLS4_CODE(String POI_CLS4_CODE) {
        this.POI_CLS4_CODE = POI_CLS4_CODE;
    }

    public String getPOI_CLS5_CODE() {
        return POI_CLS5_CODE;
    }

    public void setPOI_CLS5_CODE(String POI_CLS5_CODE) {
        this.POI_CLS5_CODE = POI_CLS5_CODE;
    }

    public String getPOI_CLS1() {
        return POI_CLS1;
    }

    public void setPOI_CLS1(String POI_CLS1) {
        this.POI_CLS1 = POI_CLS1;
    }

    public String getPOI_CLS2() {
        return POI_CLS2;
    }

    public void setPOI_CLS2(String POI_CLS2) {
        this.POI_CLS2 = POI_CLS2;
    }

    public String getPOI_CLS3() {
        return POI_CLS3;
    }

    public void setPOI_CLS3(String POI_CLS3) {
        this.POI_CLS3 = POI_CLS3;
    }

    public String getPOI_CLS4() {
        return POI_CLS4;
    }

    public void setPOI_CLS4(String POI_CLS4) {
        this.POI_CLS4 = POI_CLS4;
    }

    public String getPOI_CLS5() {
        return POI_CLS5;
    }

    public void setPOI_CLS5(String POI_CLS5) {
        this.POI_CLS5 = POI_CLS5;
    }

    public Double getCALLING_DUR() {
        return CALLING_DUR;
    }

    public void setCALLING_DUR(Double CALLING_DUR) {
        this.CALLING_DUR = CALLING_DUR;
    }

    public Integer getCALLING_NUM() {
        return CALLING_NUM;
    }

    public void setCALLING_NUM(Integer CALLING_NUM) {
        this.CALLING_NUM = CALLING_NUM;
    }

    public Integer getCALLING_CNT() {
        return CALLING_CNT;
    }

    public void setCALLING_CNT(Integer CALLING_CNT) {
        this.CALLING_CNT = CALLING_CNT;
    }

    public Integer getCALLING_DAY() {
        return CALLING_DAY;
    }

    public void setCALLING_DAY(Integer CALLING_DAY) {
        this.CALLING_DAY = CALLING_DAY;
    }

    public Double getCALLED_DUR() {
        return CALLED_DUR;
    }

    public void setCALLED_DUR(Double CALLED_DUR) {
        this.CALLED_DUR = CALLED_DUR;
    }

    public Integer getCALLED_NUM() {
        return CALLED_NUM;
    }

    public void setCALLED_NUM(Integer CALLED_NUM) {
        this.CALLED_NUM = CALLED_NUM;
    }

    public Integer getCALLED_CNT() {
        return CALLED_CNT;
    }

    public void setCALLED_CNT(Integer CALLED_CNT) {
        this.CALLED_CNT = CALLED_CNT;
    }

    public Integer getCALLED_DAY() {
        return CALLED_DAY;
    }

    public void setCALLED_DAY(Integer CALLED_DAY) {
        this.CALLED_DAY = CALLED_DAY;
    }

    public Integer getCALLED_SMS_NUM() {
        return CALLED_SMS_NUM;
    }

    public void setCALLED_SMS_NUM(Integer CALLED_SMS_NUM) {
        this.CALLED_SMS_NUM = CALLED_SMS_NUM;
    }

    public Integer getCALLED_SMS_CNT() {
        return CALLED_SMS_CNT;
    }

    public void setCALLED_SMS_CNT(Integer CALLED_SMS_CNT) {
        this.CALLED_SMS_CNT = CALLED_SMS_CNT;
    }

    public Integer getCALLED_SMS_DAY() {
        return CALLED_SMS_DAY;
    }

    public void setCALLED_SMS_DAY(Integer CALLED_SMS_DAY) {
        this.CALLED_SMS_DAY = CALLED_SMS_DAY;
    }

    public Integer getURL_VISIT_NUM() {
        return URL_VISIT_NUM;
    }

    public void setURL_VISIT_NUM(Integer URL_VISIT_NUM) {
        this.URL_VISIT_NUM = URL_VISIT_NUM;
    }

    public Integer getURL_VISIT_CNT() {
        return URL_VISIT_CNT;
    }

    public void setURL_VISIT_CNT(Integer URL_VISIT_CNT) {
        this.URL_VISIT_CNT = URL_VISIT_CNT;
    }

    public Integer getURL_ACTIVE_DAYS() {
        return URL_ACTIVE_DAYS;
    }

    public void setURL_ACTIVE_DAYS(Integer URL_ACTIVE_DAYS) {
        this.URL_ACTIVE_DAYS = URL_ACTIVE_DAYS;
    }

    public Integer getURL_ONLINE_DUR() {
        return URL_ONLINE_DUR;
    }

    public void setURL_ONLINE_DUR(Integer URL_ONLINE_DUR) {
        this.URL_ONLINE_DUR = URL_ONLINE_DUR;
    }

    public Integer getURL_FLUX() {
        return URL_FLUX;
    }

    public void setURL_FLUX(Integer URL_FLUX) {
        this.URL_FLUX = URL_FLUX;
    }

    public Integer getAPP_VISIT_NUM() {
        return APP_VISIT_NUM;
    }

    public void setAPP_VISIT_NUM(Integer APP_VISIT_NUM) {
        this.APP_VISIT_NUM = APP_VISIT_NUM;
    }

    public Integer getAPP_VISIT_CNT() {
        return APP_VISIT_CNT;
    }

    public void setAPP_VISIT_CNT(Integer APP_VISIT_CNT) {
        this.APP_VISIT_CNT = APP_VISIT_CNT;
    }

    public Integer getAPP_ACTIVE_DAYS() {
        return APP_ACTIVE_DAYS;
    }

    public void setAPP_ACTIVE_DAYS(Integer APP_ACTIVE_DAYS) {
        this.APP_ACTIVE_DAYS = APP_ACTIVE_DAYS;
    }

    public Integer getAPP_ONLINE_DUR() {
        return APP_ONLINE_DUR;
    }

    public void setAPP_ONLINE_DUR(Integer APP_ONLINE_DUR) {
        this.APP_ONLINE_DUR = APP_ONLINE_DUR;
    }

    public Integer getAPP_FLUX() {
        return APP_FLUX;
    }

    public void setAPP_FLUX(Integer APP_FLUX) {
        this.APP_FLUX = APP_FLUX;
    }

    public Integer getPUBLIC_VISIT_NUM() {
        return PUBLIC_VISIT_NUM;
    }

    public void setPUBLIC_VISIT_NUM(Integer PUBLIC_VISIT_NUM) {
        this.PUBLIC_VISIT_NUM = PUBLIC_VISIT_NUM;
    }

    public Integer getPUBLIC_VISIT_CNT() {
        return PUBLIC_VISIT_CNT;
    }

    public void setPUBLIC_VISIT_CNT(Integer PUBLIC_VISIT_CNT) {
        this.PUBLIC_VISIT_CNT = PUBLIC_VISIT_CNT;
    }

    public Integer getPUBLIC_ACTIVE_DAYS() {
        return PUBLIC_ACTIVE_DAYS;
    }

    public void setPUBLIC_ACTIVE_DAYS(Integer PUBLIC_ACTIVE_DAYS) {
        this.PUBLIC_ACTIVE_DAYS = PUBLIC_ACTIVE_DAYS;
    }

    public Integer getPUBLIC_ONLINE_DUR() {
        return PUBLIC_ONLINE_DUR;
    }

    public void setPUBLIC_ONLINE_DUR(Integer PUBLIC_ONLINE_DUR) {
        this.PUBLIC_ONLINE_DUR = PUBLIC_ONLINE_DUR;
    }

    public Integer getPUBLIC_FLUX() {
        return PUBLIC_FLUX;
    }

    public void setPUBLIC_FLUX(Integer PUBLIC_FLUX) {
        this.PUBLIC_FLUX = PUBLIC_FLUX;
    }

    public Integer getKEYWORD_VISIT_NUM() {
        return KEYWORD_VISIT_NUM;
    }

    public void setKEYWORD_VISIT_NUM(Integer KEYWORD_VISIT_NUM) {
        this.KEYWORD_VISIT_NUM = KEYWORD_VISIT_NUM;
    }

    public Integer getKEYWORD_VISIT_CNT() {
        return KEYWORD_VISIT_CNT;
    }

    public void setKEYWORD_VISIT_CNT(Integer KEYWORD_VISIT_CNT) {
        this.KEYWORD_VISIT_CNT = KEYWORD_VISIT_CNT;
    }

    public Integer getKEYWORD_ACTIVE_DAYS() {
        return KEYWORD_ACTIVE_DAYS;
    }

    public void setKEYWORD_ACTIVE_DAYS(Integer KEYWORD_ACTIVE_DAYS) {
        this.KEYWORD_ACTIVE_DAYS = KEYWORD_ACTIVE_DAYS;
    }

    public Integer getKEYWORD_ONLINE_DUR() {
        return KEYWORD_ONLINE_DUR;
    }

    public void setKEYWORD_ONLINE_DUR(Integer KEYWORD_ONLINE_DUR) {
        this.KEYWORD_ONLINE_DUR = KEYWORD_ONLINE_DUR;
    }

    public Integer getKEYWORD_FLUX() {
        return KEYWORD_FLUX;
    }

    public void setKEYWORD_FLUX(Integer KEYWORD_FLUX) {
        this.KEYWORD_FLUX = KEYWORD_FLUX;
    }

    public Integer getINDU_STAY_NUM() {
        return INDU_STAY_NUM;
    }

    public void setINDU_STAY_NUM(Integer INDU_STAY_NUM) {
        this.INDU_STAY_NUM = INDU_STAY_NUM;
    }

    public Double getINDU_STAY_DURATION() {
        return INDU_STAY_DURATION;
    }

    public void setINDU_STAY_DURATION(Double INDU_STAY_DURATION) {
        this.INDU_STAY_DURATION = INDU_STAY_DURATION;
    }

    public Integer getINDU_STAY_DAY() {
        return INDU_STAY_DAY;
    }

    public void setINDU_STAY_DAY(Integer INDU_STAY_DAY) {
        this.INDU_STAY_DAY = INDU_STAY_DAY;
    }
}
