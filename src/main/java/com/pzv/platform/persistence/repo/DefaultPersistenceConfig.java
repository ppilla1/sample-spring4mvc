package com.pzv.platform.persistence.repo;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@Profile("default")
@PropertySource({"classpath:db-default.properties"})
public class DefaultPersistenceConfig {
	@Autowired
	private Environment env;
	
	@Bean
	public DataSource dataSource(){
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();

		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.HSQL)
				.addScript(env.getProperty("db.create.script"))
				.addScript("db/sql/create-hsql-configDB.sql")
				.addScript(env.getProperty("db.datainit.script")).build();

		return db;
	}
	
	@Bean("additionalProperties")
	public Properties defaultAdditionalProperties() {
		Properties properties = new Properties();
//		properties.setProperty("hibernate.globally_quoted_identifiers",env.getProperty("default.hibernate.globally_quoted_identifiers"));
		properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		return properties;
	}

}
