package com.pzv.platform.persistence.integration;

import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pzv.platform.persistence.AppConfig;

@Configuration
@Import(value={AppConfig.class})
@ComponentScan({"com.pzv.platform.persistence.service","com.pzv.platform.persistence.integration"})
public class CamelConfig extends CamelConfiguration{

}
