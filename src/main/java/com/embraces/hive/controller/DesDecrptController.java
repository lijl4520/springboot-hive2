package com.embraces.hive.controller;

import com.embraces.hive.config.DataSourceConfig;
import com.embraces.hive.convert.Decnew;
import com.embraces.hive.util.BaseResult;
import com.embraces.hive.util.DesEncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @Author Lijl
 * @ClassName DesDecrptController
 * @Description TODO
 * @Date 2020/10/30 10:13
 * @Version 1.0
 */
@RestController
public class DesDecrptController {

    private DataSourceConfig dataSourceConfig;
    @Autowired
    public void setDataSourceConfig(DataSourceConfig dataSourceConfig){
        this.dataSourceConfig = dataSourceConfig;
    }

    /**
     * @Author lijiale
     * @MethodName desDecrpt
     * @Description TODO
     * @Date 8:45 2020/12/25
     * @Version 1.0
     * @param str
     * @return: com.embraces.hive.util.BaseResult<?>
    **/
    @PostMapping(value = "/desDecrpt/{str}")
    public BaseResult<?> desDecrpt(@PathVariable String str){
        try {
            byte[] decrypt = DesEncryptUtil.decrypt(str);
            String s = new String(decrypt);
            return new BaseResult<>(200,"解码成功",s);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new BaseResult<>(500,"解码失败",null);
    }

    /**
     * @Author lijiale
     * @MethodName sm4Decnew
     * @Description TODO
     * @Date 9:01 2020/12/24
     * @Version 1.0
     * @param str
     * @return: com.embraces.hive.util.BaseResult<?>
    **/
    @PostMapping(value = "/sm4Decnew/{str}")
    public BaseResult<?> sm4Decnew(@PathVariable String str){
        try {
            String dateStr = LocalDate.now().toString().replaceAll("-","");
            String decnew = Decnew.decnew(dateStr, str, dataSourceConfig.authSrvUrl, "utf-8");
            return new BaseResult<>(200,"成功",decnew);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new BaseResult<>(500,"解密失败",null);
    }
}
