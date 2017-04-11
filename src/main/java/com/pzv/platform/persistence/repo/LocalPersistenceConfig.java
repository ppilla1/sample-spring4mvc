package com.pzv.platform.persistence.repo;

import java.util.Objects;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.configuration.DatabaseConfiguration;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import com.pzv.platform.persistence.util.AppPropertyUtil;

@Configuration
@Profile("local")
@PropertySource({ "classpath:db-local.properties" })
public class LocalPersistenceConfig {
	private static final Logger LOG = LoggerFactory.getLogger(LocalPersistenceConfig.class);

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

	@Bean
	public DatabaseConfiguration DatabaseConfiguration() {
		DatabaseConfiguration dbConfiguration = new DatabaseConfiguration(dataSource(), "pz_platformservice_integration", "appname",
				"configkey", "configvalue", "pzv-integration");
		return dbConfiguration;
	}

	@Bean("additionalProperties")
	public Properties defaultAdditionalProperties() {
		return AppPropertyUtil.fetchProperties("db-local.properties");
	}
}
