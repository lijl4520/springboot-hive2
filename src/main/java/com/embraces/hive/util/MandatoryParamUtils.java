package com.embraces.hive.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @Author Lijl
 * @ClassName MandatoryParamUtils
 * @Description TODO
 * @Date 2020/12/14 11:31
 * @Version 1.0
 */
public class MandatoryParamUtils {

    private static Logger log = LoggerFactory.getLogger(MandatoryParamUtils.class);

    public static List<String> tvDimParam = new ArrayList<>();
    public static List<String> tvEventParam = new ArrayList<>();
    public static List<String> tvEvntParam = new ArrayList<>();
    public static List<String> tvEvntParams = new ArrayList<>();
    public static List<String> toMEventParam = new ArrayList<>();
    private static List<String> twAppYn = new ArrayList<>();
    private static List<String> twSearchYn = new ArrayList<>();

    static {
        log.info("开始加载必选参数properties配置文件");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("mandatoryparam");
        Enumeration<String> keys = resourceBundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String val = resourceBundle.getString(key);
            if (val!=null){
                String[] valArr = val.split(",");
                if ("tv_dim".equals(key)){
                    tvDimParam = Arrays.asList(valArr);
                } else if ("tv_event".equals(key)){
                    tvEventParam = Arrays.asList(valArr);
                } else if ("tv_evnt".equals(key)){
                    tvEvntParam = Arrays.asList(valArr);
                } else if ("tv_evet_param".equals(key)){
                    tvEvntParams = Arrays.asList(valArr);
                } else if("to_m_evnt".equals(key)){
                    toMEventParam = Arrays.asList(valArr);
                } else if("tw_app_yn".equals(key)){
                    twAppYn = Arrays.asList(valArr);
                } else if ("tw_search_yn".equals(key)){
                    twSearchYn = Arrays.asList(valArr);
                }
            }
        }
        log.info("加载必选参数properties配置文件加载完毕");
    }

    public static List<String> getTvDimParam(){
        return tvDimParam;
    }

    public static List<String> getTvEventParam(){
        return tvEventParam;
    }

    public static List<String> getTvEvntParam(){
        return tvEvntParam;
    }

    public static List<String> getToMEventParam(){
        return toMEventParam;
    }

    public static List<String> getTwAppYnParam(){
        return twAppYn;
    }

    public static List<String> getTwSearchYnParam(){
        return twSearchYn;
    }
}
