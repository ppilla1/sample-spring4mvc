package com.pzv.platform.persistence.repo;

import java.util.Objects;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.pzv.platform.persistence.util.AppPropertyUtil;

@Configuration
@Profile("default")
public class DefaultPersistenceConfig {

	@Bean
	public DataSource dataSource() {
		EmbeddedDatabase db = null;
		Properties dbProperties = AppPropertyUtil.fetchProperties("db-default.properties");

		if (Objects.nonNull(dbProperties)) {
			EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();

			db = builder.setType(EmbeddedDatabaseType.HSQL).addScript(dbProperties.getProperty("db.create.script"))
					.addScript(dbProperties.getProperty("db.createConfig.script"))
					.addScript(dbProperties.getProperty("db.datainit.script")).build();
		}

		return db;
	}

	@Bean("additionalProperties")
	public Properties defaultAdditionalProperties() {
		return AppPropertyUtil.fetchProperties("db-default.properties");
	}

}
