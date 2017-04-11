package com.pzv.platform.persistence.repo;

import java.util.Objects;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.configuration.DatabaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.pzv.platform.persistence.util.AppPropertyUtil;

@Configuration
@Profile("default")
@PropertySource({ "classpath:db-default.properties" })
public class DefaultPersistenceConfig {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultPersistenceConfig.class);

	@Bean
	public DataSource dataSource() {
		EmbeddedDatabase db = null;
		Properties dbProperties = AppPropertyUtil.fetchProperties("db-default.properties");

		LOG.info("--> {}",dbProperties);
		
		if (Objects.nonNull(dbProperties)) {
			EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();

			db = builder.setType(EmbeddedDatabaseType.HSQL).addScript(dbProperties.getProperty("db.create.script"))
					.addScript(dbProperties.getProperty("db.createConfig.script"))
					.addScript(dbProperties.getProperty("db.datainit.script")).build();
		}

		return db;
	}

	@Bean
	public DatabaseConfiguration DatabaseConfiguration() {
		DatabaseConfiguration dbConfiguration = new DatabaseConfiguration(dataSource(), "pz_platformservice_integration", "appname",
				"configkey", "configvalue", "pzv-integration");
		;
		return dbConfiguration;
	}

	@Bean("additionalProperties")
	public Properties defaultAdditionalProperties() {
		return AppPropertyUtil.fetchProperties("db-default.properties");
	}

}
