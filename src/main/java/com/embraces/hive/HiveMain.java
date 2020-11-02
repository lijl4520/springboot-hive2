package com.embraces.hive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author Lijl
 * @ClassName HiveMain
 * @Description TODO
 * @Date 2020/10/20 16:34
 * @Version 1.0
 */
@ComponentScan(basePackages = {"com.embraces.hive"})
@EnableAutoConfiguration
public class HiveMain {

	public static void main(String[] args) {
		SpringApplication.run(HiveMain.class, args);
	}
}
