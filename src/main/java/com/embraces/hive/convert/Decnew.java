//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.embraces.hive.convert;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Decnew {
    private static final Logger LOGGER = LoggerFactory.getLogger(Decnew.class);
    private static boolean getkey = false;
    private static String deckey = "";
    private static SM4 sm4 = new SM4();

    public Decnew() {
    }

    public static String decnew(String date, String decstr, String authSrvUrl, String type) {
        if (!getkey) {
            Class var4 = Decnew.class;
            synchronized(Decnew.class) {
                HttpKeyExecutor hke = new HttpKeyExecutor(authSrvUrl);
                deckey = hke.getHttpKey(date);
                sm4.set(type);
                getkey = true;
            }
        }

        String v = "";

        try {
            v = sm4.decrypt(decstr, deckey);
            if (StringUtils.isBlank(v)) {
                LOGGER.warn("decrypt ciphertext failed,please check paramter");
                return v;
            }
        } catch (DecoderException var6) {
            LOGGER.error(var6.toString());
        }

        return v;
    }
}
