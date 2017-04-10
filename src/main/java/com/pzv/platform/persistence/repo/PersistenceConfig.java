package com.pzv.platform.persistence.repo;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "com.pzv.platform.persistence.repo.dao" })
@ComponentScan({ "com.pzv.platform.persistence.repo" })
@PropertySource({ "classpath:application.properties" })
public class PersistenceConfig {
	@Autowired
	private Environment env;

	@Autowired
	private DataSource dataSource;

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean
	public NamedParameterJdbcTemplate jdbcTemplate() {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		return jdbcTemplate;
	}

	@Profile("default")
	@Bean
	public DataSource defaultDataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();

		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.HSQL)
				.addScript(env.getProperty("default.db.create.script"))
				.addScript(env.getProperty("default.db.datainit.script")).build();

		return db;
	}

	@Profile("local")
	@Bean
	public DataSource localDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("local.jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("local.jdbc.url"));
		dataSource.setUsername(env.getProperty("local.jdbc.user"));
		dataSource.setPassword(env.getProperty("local.jdbc.pass"));
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Autowired @Qualifier("additionalProperties") Properties additionalProperties) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

		em.setDataSource(dataSource);
		em.setPackagesToScan(new String[] { "com.pzv.platform.persistence.model" });
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setShowSql(true);
		vendorAdapter.setGenerateDdl(true);
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties);

		return em;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	@Profile("default")
	@Bean("additionalProperties")
	public Properties defaultAdditionalProperties() {
		Properties properties = new Properties();
//		properties.setProperty("hibernate.globally_quoted_identifiers",env.getProperty("default.hibernate.globally_quoted_identifiers"));
		properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("default.hibernate.hbm2ddl.auto"));
		properties.setProperty("hibernate.dialect", env.getProperty("default.hibernate.dialect"));
		return properties;
	}

	@Profile("local")
	@Bean("additionalProperties")
	public Properties localAdditionalProperties() {
		Properties properties = new Properties();
//		properties.setProperty("hibernate.globally_quoted_identifiers",env.getProperty("local.hibernate.globally_quoted_identifiers"));
		properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("local.hibernate.hbm2ddl.auto"));
		properties.setProperty("hibernate.dialect", env.getProperty("local.hibernate.dialect"));
		return properties;
	}
}
