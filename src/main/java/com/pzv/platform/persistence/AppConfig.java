package com.pzv.platform.persistence;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pzv.platform.persistence.repo.PersistenceConfig;

@Configuration
@Import({PersistenceConfig.class})
@ComponentScan({ "com.pzv.platform.persistence" })
public class AppConfig {

}
