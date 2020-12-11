package com.embraces.hive.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * @Author Lijl
 * @ClassName JsonUtil
 * @Description 生成json文件工具类
 * @Date 2020/8/31 10:04
 * @Version 1.0
 */
public class WriterFileUtil {

    private static Logger log = LoggerFactory.getLogger(WriterFileUtil.class);

    /**
     * @Author Lijl
     * @MethodName createJSONFile
     * @Description TODO
     * @Date 11:07 2020/8/31
     * @Version 1.0
     * @param jsonString
     * @param filepath
     * @return: boolean
     */
    public static boolean createCsvFile(String jsonString,String filepath){
        boolean flag = true;
        try {
            log.info("--------------------开始写文件------------------------------");
            File file = new File(filepath);
            if(!file.exists()) {
                file.createNewFile();
            }
            file.createNewFile();//创建新文件
            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(jsonString);
            write.flush();
            write.close();
        } catch (Exception e) {
            log.error("写文件异常：{}",e.getMessage());
            flag = false;
        }
        return flag;
    }
}

