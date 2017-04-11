package com.pzv.platform.persistence.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.configuration.DatabaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppPropertyUtil {
	private static final Logger LOG = LoggerFactory.getLogger(AppPropertyUtil.class);

	public static Properties fetchProperties(String propFileName) {
		Properties properties = null;

		if (Objects.nonNull(propFileName) && !propFileName.isEmpty()) {
			try (InputStream in = AppPropertyUtil.class.getClassLoader().getResourceAsStream(propFileName)) {
				properties = new Properties();
				properties.load(in);
			} catch (IOException e) {
				LOG.error("{}", e.getMessage(), e);
			}
		}
		return properties;
	}

	public static Properties fetchProperties(DatabaseConfiguration dbConfiguration) {
		Properties properties = null;

		if (Objects.nonNull(dbConfiguration)) {
			Iterator<String> keyIterator = dbConfiguration.getKeys();
			properties = new Properties();

			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				properties.setProperty(key, dbConfiguration.getString(key));
			}
		}
		return properties;
	}
}
