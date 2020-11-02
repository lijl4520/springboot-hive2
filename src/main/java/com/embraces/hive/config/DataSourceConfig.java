package com.embraces.hive.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Author Lijl
 * @ClassName DataSourceConfig
 * @Description 数据源配置
 * @Date 2020/10/20 15:09
 * @Version 1.0
 */
@Configuration
public class DataSourceConfig {
	
	private Logger log = LoggerFactory.getLogger(DataSourceConfig.class);

	@Value("${hive.datasource.hive.url}")
	public String url;
	@Value("${separator}")
	public String separator;
	@Value("${authSrvUrl}")
	public String authSrvUrl;

	/**
	 * 认证类型
	 */
	@Value("${authentication.type}")
	private String authenticationType;
	
	/**
	 * 认证服务器配置文件路径
	 */
	@Value("${authentication.kerberos.krb5FilePath}")
	private String krb5FilePath;
	
	/**
	 * 认证主体
	 */
	@Value("${authentication.kerberos.principal}")
	private String principal;
	
	/**
	 * 认证使用的keytab文件
	 */
	@Value("${authentication.kerberos.keytab}")
	private String keytab;


	@Bean
	public DataSource dataSource() {
		loadProps();
		//用户认证
		//authentication();
		return new DruidDataSource();
	}

	private void loadProps() {
		log.info("开始加载properties配置文件");
		ResourceBundle resourceBundle = ResourceBundle.getBundle("servicebean");
		Enumeration<String> keys = resourceBundle.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String val = resourceBundle.getString(key);
			log.info("读取BeanId,API标识:{}对应的业务BeanId:{}",key,val);
			TvServiceBaseFactory.serviceCode.put(key,val);
		}
		log.info("加载properties配置文件加载完毕");
	}


	/**
	 * 认证
	 */
	private void authentication() {

		if(authenticationType != null && "kerberos".equalsIgnoreCase(authenticationType.trim())) {
			log.info("开始设置kerberos身份验证.");
		} else {
			log.info("未设置kerberos身份验证.");
			return;
		}
		
		if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
			System.out.println(krb5FilePath);
			// 默认：这里不设置的话，win默认会到 C盘下读取krb5.init
			log.info("krb5文件路径{}",krb5FilePath);
		} else {
			log.info("krb5文件路径{}",krb5FilePath);
			// linux 会默认到 /etc/krb5.conf
			System.setProperty("java.security.krb5.conf", krb5FilePath); 
		}

		// 使用Hadoop安全登录
		org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
		conf.set("hadoop.security.authentication", authenticationType);
		try {
			UserGroupInformation.setConfiguration(conf);
			UserGroupInformation.loginUserFromKeytab(principal, keytab);
			log.info("Kerberos身份认证完成, krb5FilePath：{}, principal:{}, keytab:{}", krb5FilePath, principal, keytab);
		} catch (IOException e1) {
			log.error(e1.getMessage() + ", detail:{}", e1);
		}
	}
}