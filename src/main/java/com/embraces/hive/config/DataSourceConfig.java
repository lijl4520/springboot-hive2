package com.embraces.hive.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.ResourceBundle;

import com.embraces.hive.util.HBaseServiceUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

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

	@Value("${hbase.zookeeper.quorum}")
	private String zookeeperQuorum;
	@Value("${hbase.zookeeper.property.clientPort}")
	private String zookeeperClientPort;
	@Value("${zookeeper.znode.parent}")
	private String zookeeperZnodeParent;
	@Value("${hbase.hbase-site.path}")
	private String sitePath;
	@Value("${hbase.is_hbase}")
	private boolean is_hbase;


	@Bean
	public HBaseServiceUtil getHbaseServiceUtil() {
		loadProps();
		//用户认证
		authentication();
		if (is_hbase){
			org.apache.hadoop.conf.Configuration conf = org.apache.hadoop.hbase.HBaseConfiguration.create();
			conf.set("hbase.zookeeper.quorum", zookeeperQuorum);
			conf.set("hbase.zookeeper.property.clientPort", zookeeperClientPort);
			conf.set("zookeeper.znode.parent", zookeeperZnodeParent);
			conf.addResource(new Path(sitePath));
			return new HBaseServiceUtil(conf);
		}else{
			return new HBaseServiceUtil();
		}
	}


	private void loadProps() {
		log.info("开始加载properties配置文件");
		ResourceBundle resourceBundle = ResourceBundle.getBundle("servicebean");
		Enumeration<String> keys = resourceBundle.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String val = resourceBundle.getString(key);
			log.info("读取BeanId,API标识:{}对应的业务BeanId:{}",key,val);
			if (key.contains("_")){
				HbaseEvntServiceFactory.serviceCode.put(key,val);
			}else{
				TvServiceBaseFactory.serviceCode.put(key,val);
			}
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
			System.setProperty("java.security.krb5.conf", krb5FilePath);
		}

		// 使用Hadoop安全登录
		loginUserFromKeyTab();
	}



	@Scheduled(cron = "0 0 0 * * ?")
	public void cronJob(){
		// 使用Hadoop安全登录
		loginUserFromKeyTab();
	}

	private void loginUserFromKeyTab() {
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