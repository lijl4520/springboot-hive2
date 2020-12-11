package com.embraces.hive.model.hbase;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.Serializable;

/**
 * @Author Lijl
 * @ClassName TwDtEvntNlInduAllSum
 * @Description TODO
 * @Date 2020/12/4 16:10
 * @Version 1.0
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,getterVisibility = JsonAutoDetect.Visibility.NONE)
public class TwDtEvntNlInduAllSum implements Serializable {
    private String IMEI;
    private String POI_CLS_CODE;
    private String POI_CLS_NAME;

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
}
