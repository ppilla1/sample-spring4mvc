package com.pzv.platform.persistence;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({ "com.pzv.platform.persistence" })
@PropertySource({"classpath:application.properties"})
public class AppConfig {
	
}
