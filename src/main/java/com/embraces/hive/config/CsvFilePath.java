package com.embraces.hive.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Lijl
 * @ClassName CsvFilePath
 * @Description TODO
 * @Date 2020/10/25 20:18
 * @Version 1.0
 */
@Configuration
public class CsvFilePath {
    @Value("${sftp.locacsvpath}")
    public String locaCsvPath;
    @Value("${sftp.remotepath}")
    public String remotePath;
    @Value("${sftp.host}")
    public String host;
    @Value("${sftp.account}")
    public String account;
    @Value("${sftp.password}")
    public String password;
    @Value("${sftp.port}")
    public int port;
    @Value("${sftp.isSftp}")
    public boolean is_sftp;
}
