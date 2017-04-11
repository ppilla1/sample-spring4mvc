package com.pzv.platform.persistence.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.sql.DataSource;

import org.apache.commons.configuration.DatabaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CompositeFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.pzv.platform.persistence.AppConfig;
import com.pzv.platform.persistence.repo.PersistenceConfig;
import com.pzv.platform.persistence.util.AppPropertyUtil;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@ComponentScan(basePackages = { "com.pzv.platform.persistence.web" })
@EnableWebMvc
@EnableSwagger2
@Import({ AppConfig.class,PersistenceConfig.class})
@PropertySource("classpath:application.properties")
public class WebConfig extends WebMvcConfigurerAdapter implements WebApplicationInitializer {
	private static final Logger LOG = LoggerFactory.getLogger(WebConfig.class);
	
	/**
	 * This bean was declared to fix issue of
	 * @Value annotation not working while
	 * swagger was enabled
	 * */
	@Bean
	public PropertySourcesPlaceholderConfigurer propertyConfigurer(DatabaseConfiguration dbConfiguration,Environment env) {
		PropertySourcesPlaceholderConfigurer propertyConfigurer = new PropertySourcesPlaceholderConfigurer();
		
		Properties dbProperties = AppPropertyUtil.fetchProperties(dbConfiguration);
		
		if (Objects.nonNull(dbProperties)){
			propertyConfigurer.setProperties(dbProperties);
		}

		propertyConfigurer.setEnvironment(env);
		
		return propertyConfigurer;
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
		appContext.register(this.getClass());
		appContext.setServletContext(servletContext);

		servletContext.addListener(new ContextLoaderListener(appContext));
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet",
				new DispatcherServlet(appContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/*");

		FilterRegistration.Dynamic compositFilter = servletContext.addFilter("DefaultFilters", getCompositFilter());
		compositFilter.addMappingForUrlPatterns(null, false, "/*");

	}

	@Bean
	public Filter getCompositFilter() {
		CompositeFilter compositeFilter = new CompositeFilter();
		List<Filter> filters = new ArrayList<>();
		// Add any require filters

		compositeFilter.setFilters(filters);
		return compositeFilter;
	}

	/**
	 * Enabling CORS support for cross-domain AJAX calls
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE")
				.allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin,Access-Control-Request-Method",
						"Access-Control-Request-Headers", "Authorization")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials",
						"X-Pagination,Authorization")
				.allowCredentials(false).maxAge(10);
	}

	/**
	 * Swagger configuration
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

}
