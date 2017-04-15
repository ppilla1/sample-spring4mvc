package com.pzv.platform.persistence.repo;

import java.util.Objects;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import com.pzv.platform.persistence.util.AppPropertyUtil;

@Configuration
@Profile("local")
public class LocalPersistenceConfig {

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = null;

		Properties dbProperties = AppPropertyUtil.fetchProperties("db-local.properties");

		if (Objects.nonNull(dbProperties)) {
			dataSource = new BasicDataSource();
			dataSource.setDriverClassName(dbProperties.getProperty("jdbc.driverClassName"));
			dataSource.setUrl(dbProperties.getProperty("jdbc.url"));
			dataSource.setUsername(dbProperties.getProperty("jdbc.user"));
			dataSource.setPassword(dbProperties.getProperty("jdbc.pass"));

		}

		return dataSource;
	}

	@Bean("additionalProperties")
	public Properties defaultAdditionalProperties() {
		return AppPropertyUtil.fetchProperties("db-local.properties");
	}
}
