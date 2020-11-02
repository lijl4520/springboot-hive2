package com.embraces.hive.model;

/**
 * @Author Lijl
 * @EnumName HiveTableEnum
 * @Description TODO
 * @Date 2020/10/27 15:31
 * @Version 1.0
 */
public enum HiveTableEnum {
    TV_D_SUM_SK_INDU_CAR_URL_DT("api0001"),
    TV_D_SUM_SK_INDU_CAR_APP_DT("api0002"),
    TV_D_SUM_SK_INDU_CAR_KEYWORD_DT("api0003"),
    TV_D_SUM_SK_INDU_TRAIN_URL_DT("api0004"),
    TV_D_SUM_SK_INDU_TRAIN_APP_DT("api0005"),
    TV_D_SUM_SK_INDU_TRAIN_KEYWORD_DT("api0006"),
    T_DIM_MD_INDUSTRY_CONT_CODE("api0007"),
    TV_D_SUM_SK_INDU_ENTER_URL_DT("api0008"),
    TV_M_SUM_SK_INDU_ENTER_URL("api0009"),
    TV_D_SUM_SK_INDU_ENTER_APP_DT("api0010"),
    TV_M_SUM_SK_INDU_ENTER_APP("api0011"),
    TV_M_SUM_SK_INDU_TRAIN_URL("api0012"),
    TV_M_SUM_SK_INDU_TRAIN_APP("api0013"),
    TV_M_SUM_SK_INDU_CAR_URL("api0014"),
    TV_M_SUM_SK_INDU_CAR_APP("api0015"),
    TV_D_SUM_SK_INDU_WED_URL_DT("api0016"),
    TV_M_SUM_SK_INDU_WED_URL("api0017"),
    TV_D_SUM_SK_INDU_WED_APP_DT("api0018"),
    TV_M_SUM_SK_INDU_WED_APP("api0019"),
    TV_D_SUM_SK_INDU_FOOD_URL_DT("api0020"),
    TV_M_SUM_SK_INDU_FOOD_URL("api0021"),
    TV_D_SUM_SK_INDU_FOOD_APP_DT("api0022"),
    TV_M_SUM_SK_INDU_FOOD_APP("api0023"),
    TV_D_SUM_SK_INDU_VIDEO_URL_DT("api0024"),
    TV_M_SUM_SK_INDU_VIDEO_URL("api0025"),
    TV_D_SUM_SK_INDU_VIDEO_APP_DT("api0026"),
    TV_M_SUM_SK_INDU_VIDEO_APP("api0027"),
    TV_D_SUM_SK_INDU_GAME_URL_DT("api0028"),
    TV_M_SUM_SK_INDU_GAME_URL("api0029"),
    TV_D_SUM_SK_INDU_GAME_APP_DT("api0030"),
    TV_M_SUM_SK_INDU_GAME_APP("api0031");

    private String typeName;

    HiveTableEnum(String typeName){
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
    public static HiveTableEnum fromTypeName(String typeName){
        for (HiveTableEnum type : HiveTableEnum.values()) {
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
