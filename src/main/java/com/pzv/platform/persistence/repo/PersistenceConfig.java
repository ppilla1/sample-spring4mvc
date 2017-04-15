package com.pzv.platform.persistence.repo;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.apache.commons.configuration.DatabaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.pzv.platform.persistence.AppConfig;
import com.pzv.platform.persistence.util.AppPropertyUtil;

@Configuration
@Import(value={AppConfig.class})
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "com.pzv.platform.persistence.repo.dao" })
@ComponentScan({ "com.pzv.platform.persistence.repo" })
public class PersistenceConfig extends CamelConfiguration{

	@Bean
	public DatabaseConfiguration DatabaseConfiguration(DataSource dataSource) {
		Properties appProperties = AppPropertyUtil.fetchProperties("application.properties");
		DatabaseConfiguration dbConfiguration = new DatabaseConfiguration(
				dataSource, 
				appProperties.getProperty("application.conf.tablename"),
				appProperties.getProperty("application.conf.tablename.appnameColumn"),
				appProperties.getProperty("application.conf.tablename.keynameColumn"), 
				appProperties.getProperty("application.conf.tablename.valueColumn"), 
				appProperties.getProperty("application.name"));
		return dbConfiguration;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean
	public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		return jdbcTemplate;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Autowired @Qualifier("additionalProperties") Properties additionalProperties,DataSource dataSource) {
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


}
