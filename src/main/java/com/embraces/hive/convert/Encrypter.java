//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.embraces.hive.convert;

import com.embraces.hive.convert.inft.CipherServiceImpl;
import com.embraces.hive.convert.inft.ICipherService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Encrypter {
    private static final Logger LOGGER = LoggerFactory.getLogger(Encrypter.class);
    private static ICipherService cipher;

    public Encrypter() {
    }

    public static String encrypt(String paramDate, String number, String authSrvUrl) {
        if (cipher == null) {
            cipher = new CipherServiceImpl(authSrvUrl, Integer.parseInt(paramDate));
        }

        String v = cipher.encrypt(paramDate, number);
        if (StringUtils.isBlank(v)) {
            LOGGER.warn("get key value failed,please check paramter");
        }

        return v;
    }
}
