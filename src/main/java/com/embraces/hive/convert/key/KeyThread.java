//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.embraces.hive.convert.key;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class KeyThread implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeyThread.class);
    private KeyService keyService = null;
    private IKeyExecutor keyExecutor = null;
    private static final int MONTHLEN = 1;
    private int paramMonth = 0;
    private int managerCycle = 86400000;
    private static boolean refreshFlag = true;

    public KeyThread(KeyService keyService, String adapterUrl, int paramMonth) {
        this.keyService = keyService;
        this.keyExecutor = new HttpKeyExecutor(adapterUrl);
        if (paramMonth != 0) {
            this.paramMonth = paramMonth;
        }

    }

    @Override
    public void run() {
        try {
            (new Timer()).schedule(new MyTimerTask01(), 300000L);
            this.process();
            Thread.sleep((long)this.managerCycle);
        } catch (InterruptedException var2) {
            LOGGER.error(var2.getMessage(), var2);
        }

    }

    public void process() {
        String secretKeyAndExpireTime = "";
        String[] keySplits = new String[2];
        String mapKey = "";
        Map keyMap = this.keyService.getKeyMap();
        String[] months = this.calcMonths();
        String[] arrayOfString1 = months;
        int j = months.length;

        for(int i = 0; i < j; ++i) {
            String curMonth = arrayOfString1[i];
            mapKey = "SM4_" + curMonth;
            if (!keyMap.containsKey(mapKey) || refreshFlag) {
                secretKeyAndExpireTime = this.keyExecutor.getHttpKey(curMonth + "01000000");
                keySplits = secretKeyAndExpireTime.split(",");
                if (keySplits.length > 1) {
                    this.writeMap(keySplits, curMonth, keyMap);
                    refreshFlag = false;
                } else {
                    LOGGER.warn("get the secretKey and ExpireTime is null,Please check whether the security center has the key for this month " + curMonth);
                    this.managerCycle = 3600000;
                }
            }
        }

        this.keyService.setKeyMap(keyMap);
    }

    private HashSet<String> segment(String beginTime, String endTime) {
        HashSet monthSet = new HashSet();
        Calendar beginCldr = new GregorianCalendar();
        GregorianCalendar endCldr = new GregorianCalendar();

        try {
            beginCldr.setTime(DateUtils.parseDateStrictly(beginTime, new String[]{"yyyyMM"}));
            endCldr.setTime(DateUtils.parseDateStrictly(endTime, new String[]{"yyyyMM"}));

            while(beginCldr.compareTo(endCldr) <= 0) {
                monthSet.add(DateFormatUtils.format(beginCldr, "yyyyMM"));
                beginCldr.add(2, 1);
            }
        } catch (ParseException var7) {
            LOGGER.error(var7.getMessage(), var7);
        }

        return monthSet;
    }

    private String[] calcMonths() {
        String[] months = new String[3];
        int m = 0;
        Calendar calendar = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

        try {
            String originMonth = String.valueOf(this.paramMonth);
            String formatMonth = originMonth.substring(0, 6);
            Date date = sdf.parse(formatMonth);

            for(int i = -1; i <= 1; ++i) {
                calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(2, i);
                months[m++] = DateFormatUtils.format(calendar, "yyyyMM");
            }

            return months;
        } catch (Exception var9) {
            var9.printStackTrace();
            return null;
        }
    }

    private void writeMap(String[] keySplits, String curMonth, Map<String, String> keyMap) {
        String secretKey = keySplits[0];
        String expireTime = keySplits[1];
        if (StringUtils.isNotBlank(expireTime) && expireTime.length() >= 6 && StringUtils.isNotBlank(secretKey)) {
            this.managerCycle = 86400000;
            expireTime = expireTime.substring(0, 6);
            if (curMonth.compareTo(expireTime) <= 0) {
                Set set = this.segment(curMonth, expireTime);
                Iterator it = set.iterator();

                while(it.hasNext()) {
                    String str = (String)it.next();
                    keyMap.put("SM4_" + str, secretKey);
                }
            }
        }

    }

    public static void main(String[] args) throws ParseException {
        System.out.println(DateUtils.parseDateStrictly("20151011", new String[]{"yyyyMM"}));
        System.out.println(DateUtils.parseDateStrictly("201512", new String[]{"yyyyMM"}));
    }

    private static class MyTimerTask01 extends TimerTask {
        private MyTimerTask01() {
        }

        @Override
        public void run() {
            KeyThread.refreshFlag = true;
            KeyThread.LOGGER.debug("refresh flag set to true,refresh will start in a minute");
        }
    }
}
