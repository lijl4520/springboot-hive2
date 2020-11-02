package com.embraces.hive.config;

import java.util.List;

import com.embraces.hive.util.PojoMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author Lijl
 * @ClassName WebConfig
 * @Description
 * @Date 2020/10/20 15:09
 * @Version 1.0
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

		super.configureMessageConverters(converters);
		
		// 注入自定义消息转换器
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = PojoMapper.getObjectMapper();
		jsonConverter.setObjectMapper(objectMapper);
		converters.add(jsonConverter);
	}
}
