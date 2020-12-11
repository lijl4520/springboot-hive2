package com.embraces.hive.model.hbase;

/**
 * @Author Lijl
 * @EnumName HiveTableEnum
 * @Description TODO
 * @Date 2020/10/27 15:31
 * @Version 1.0
 */
public enum HbaseTableEnum {
    TW_DT_EVNT_NL_INDU_ALL_SUM_A("s_0001"),
    TV_M_EVNT_NL_INDU_ALL_SUM_A("s_0004");

    private String typeName;

    HbaseTableEnum(String typeName){
        this.typeName = typeName;
    }

    /**
     * @Author Lijl
     * @MethodName fromTypeName
     * @Description 根据类型的名称，返回类型的枚举实例
     * @Date 15:45 2020/10/27
     * @Version 1.0
     * @param typeName
     * @return: com.embraces.hive.model.HiveTableEnum
    **/
    public static HbaseTableEnum fromTypeName(String typeName){
        if ("s_0002".equals(typeName)){
            typeName = "s_0001";
        }else if ("s_0003".equals(typeName)){
            typeName = "s_0004";
        }
        for (HbaseTableEnum type : HbaseTableEnum.values()) {
            if (type.getTypeName().equals(typeName)){
                return type;
            }
        }
        return null;
    }

    public String getTypeName(){
        return this.typeName;
    }
}
