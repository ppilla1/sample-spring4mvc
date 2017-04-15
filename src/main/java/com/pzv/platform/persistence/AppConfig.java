package com.pzv.platform.persistence;

import java.util.Objects;
import java.util.Properties;

import org.apache.camel.CamelContext;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.commons.configuration.DatabaseConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import com.pzv.platform.persistence.util.AppPropertyUtil;

@Configuration
@ComponentScan({ "com.pzv.platform.persistence.repo","com.pzv.platform.persistence.integration" })
@PropertySource({"classpath:application.properties"})
public class AppConfig {
	
	/**
	 * This bean was declared to fix issue of
	 * @Value annotation not working while
	 * swagger was enabled
	 * */
	@Bean
	public PropertySourcesPlaceholderConfigurer propertyConfigurer(DatabaseConfiguration dbConfiguration,Environment env,CamelContext camelContext) {
		PropertySourcesPlaceholderConfigurer propertyConfigurer = new PropertySourcesPlaceholderConfigurer();
		propertyConfigurer.setEnvironment(env);

		Properties dbProperties = AppPropertyUtil.fetchProperties(dbConfiguration);
		
		if (Objects.nonNull(dbProperties)){
			propertyConfigurer.setProperties(dbProperties);
			PropertiesComponent pc = new PropertiesComponent();
			pc.setInitialProperties(dbProperties);
			camelContext.addComponent("properties", pc);
		}

		
		return propertyConfigurer;
	}	
}
