package com.embraces.hive.convert;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Encnew {
    private static final Logger LOGGER = LoggerFactory.getLogger(Encnew.class);
    private static boolean getkey = false;
    private static String enckey = "";
    private static SM4 sm4 = new SM4();

    public Encnew() {
    }

    public static String encnew(String date, String encstr, String authSrvUrl, String type) {
        if (!getkey) {
            Class var4 = Encnew.class;
            synchronized(Encnew.class) {
                HttpKeyExecutor hke = new HttpKeyExecutor(authSrvUrl);
                enckey = hke.getHttpKey(date);
                sm4.set(type);
                getkey = true;
            }
        }

        String v = "";
        v = sm4.encrypt(encstr, enckey);
        if (StringUtils.isBlank(v)) {
            LOGGER.warn("encrypt ciphertext failed,please check paramter");
            return v;
        } else {
            return v.toUpperCase();
        }
    }
}
