
package com.embraces.hive.convert.arithmetic;

import org.apache.commons.codec.DecoderException;

public interface IArithmeticService {
    String encrypt(String var1, String var2);

    String decrypt(String var1, String var2) throws DecoderException;
}
