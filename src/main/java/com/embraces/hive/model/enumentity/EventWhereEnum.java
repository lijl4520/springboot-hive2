package com.embraces.hive.model.enumentity;

/**
 * @Author Lijl
 * @EnumName EventWhereEnum
 * @Description TODO
 * @Date 2020/12/3 14:29
 * @Version 1.0
 */
public enum EventWhereEnum {
    CALLED_SMS_NUM("2"),
    URL_VISIT_NUM("3"),
    APP_VISIT_NUM("4"),
    PUBLIC_VISIT_NUM("5"),
    KEYWORD_VISIT_NUM("6"),
    INDU_STAY_NUM("7");

    private String event;

    EventWhereEnum(String event){
        this.event = event;
    }

    public static EventWhereEnum fromventWhereEnum(String event){
        for (EventWhereEnum e : EventWhereEnum.values()) {
            if (e.getEventWhereEnum().equals(event)){
                return e;
            }
        }
        return null;
    }

    public String getEventWhereEnum(){
        return this.event;
    }
}
