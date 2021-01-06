//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.embraces.hive.convert.key;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyService implements IKeyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeyService.class);
    private Map<String, String> keyMap = new HashMap();

    public KeyService(String adapterUrl, int paramMonth) {
        KeyThread keyThread = new KeyThread(this, adapterUrl, paramMonth);
        keyThread.process();
        Thread damonThread = new Thread(new KeyThread(this, adapterUrl, paramMonth));
        damonThread.setDaemon(true);
        damonThread.start();
    }

    @Override
    public String getKey(String requestTime) {
        if (requestTime.length() < 6) {
            LOGGER.error("The get key's requst time length is too short!");
            return null;
        } else {
            String requestMon = requestTime.substring(0, 6);
            String requestKey = "SM4_" + requestMon;
            return this.keyMap != null && this.keyMap.containsKey(requestKey) ? (String)this.keyMap.get(requestKey) : null;
        }
    }

    public Map<String, String> getKeyMap() {
        return this.keyMap;
    }

    public void setKeyMap(Map<String, String> keyMap) {
        this.keyMap = keyMap;
    }
}
