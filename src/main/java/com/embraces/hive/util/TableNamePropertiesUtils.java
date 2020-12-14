package com.embraces.hive.util;

import com.embraces.hive.config.HbaseEvntServiceFactory;
import com.embraces.hive.config.TvServiceBaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Lijl
 * @ClassName TableNamePropertiesUtils
 * @Description TODO
 * @Date 2020/12/14 11:42
 * @Version 1.0
 */
public class TableNamePropertiesUtils {

    private static Logger log = LoggerFactory.getLogger(TableNamePropertiesUtils.class);

    public static Map<String,String> tableNameMap = new ConcurrentHashMap<>();

    static {
        log.info("开始加载模型名称properties配置文件");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("tablename");
        Enumeration<String> keys = resourceBundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String val = resourceBundle.getString(key);
            tableNameMap.put(key,val);
        }
        log.info("加载模型名称properties配置文件加载完毕");
    }

    public static String getTableName(String methodType){
        String tableName = tableNameMap.get(methodType);
        if (tableName!=null){
            return tableName;
        }
        return null;
    }
}
