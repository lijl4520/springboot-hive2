package com.embraces.hive.model.enumentity;

/**
 * @Author Lijl
 * @EnumName EventEnum
 * @Description TODO
 * @Date 2020/12/3 12:19
 * @Version 1.0
 */
public enum EventEnum {
    CALLING("1"),
    SMS("2"),
    URL("3"),
    APP("4"),
    PUBLIC_VISIT("5"),
    KEYWORD("6"),
    INDU("7");

    private String event;

    EventEnum(String event){
        this.event = event;
    }

    public static EventEnum fromEvent(String event){
        for (EventEnum e : EventEnum.values()) {
            if (e.getEvent().equals(event)){
                return e;
            }
        }
        return null;
    }

    public String getEvent(){
        return this.event;
    }
}
