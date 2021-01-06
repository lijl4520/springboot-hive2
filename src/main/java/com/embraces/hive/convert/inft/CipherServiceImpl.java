//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.embraces.hive.convert.inft;

import com.embraces.hive.convert.arithmetic.IArithmeticService;
import com.embraces.hive.convert.arithmetic.SM4ArithmeticService;
import com.embraces.hive.convert.key.IKeyService;
import com.embraces.hive.convert.key.KeyService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CipherServiceImpl implements ICipherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CipherServiceImpl.class);
    private IKeyService keyService;
    private IArithmeticService arithService;

    public CipherServiceImpl(IKeyService keyService, IArithmeticService arithService) {
        this.keyService = keyService;
        this.arithService = arithService;
    }

    public CipherServiceImpl(String adapterUrl, int paramMonth) {
        this(new KeyService(adapterUrl, paramMonth), new SM4ArithmeticService());
    }

    @Override
    public String encrypt(String requestTime, String plaintext) {
        if (!StringUtils.isBlank(requestTime) && !StringUtils.isBlank(plaintext)) {
            String key = this.keyService.getKey(requestTime);
            if (StringUtils.isBlank(key)) {
                LOGGER.error("get key value failed");
                return null;
            } else {
                String ciphertext = this.arithService.encrypt(key, plaintext);
                if (StringUtils.isBlank(ciphertext)) {
                    LOGGER.info("decrypt ciphertext failed");
                    return null;
                } else {
                    return ciphertext;
                }
            }
        } else {
            return null;
        }
    }

    @Override
    public String decrypt(String requestTime, String ciphertext) {
        if (!StringUtils.isBlank(requestTime) && !StringUtils.isBlank(ciphertext)) {
            String key = this.keyService.getKey(requestTime);
            if (StringUtils.isBlank(key)) {
                LOGGER.error("get key value failed");
                return null;
            } else {
                String plaintext = "";

                try {
                    plaintext = this.arithService.decrypt(key, ciphertext);
                } catch (Exception var6) {
                    LOGGER.error(var6.getMessage(), var6);
                }

                if (StringUtils.isBlank(plaintext)) {
                    LOGGER.info("decrypt ciphertext failed");
                    return null;
                } else {
                    return plaintext.trim();
                }
            }
        } else {
            return null;
        }
    }
}
