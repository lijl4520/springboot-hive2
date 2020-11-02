package com.embraces.hive.controller;

import com.embraces.hive.util.BaseResult;
import com.embraces.hive.util.DesEncryptUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Lijl
 * @ClassName DesDecrptController
 * @Description TODO
 * @Date 2020/10/30 10:13
 * @Version 1.0
 */
@RestController
public class DesDecrptController {

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
}
