package com.vma.restrxjdbc.config;

import javax.annotation.PostConstruct;

import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JerseyProperties.class)
public class JerseyConfiguration extends ResourceConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(JerseyConfiguration.class);
	
	@Autowired
	private JerseyProperties properties;
	
	@PostConstruct
	public void init() throws ClassNotFoundException {
		final String resources = properties.getResources() == null ? "" : properties.getResources();
		final String[] classes = resources.split(",");
		for(int i=0; i<classes.length; i++) {
			final Class<?> clazz = Class.forName(classes[i].trim());
			logger.debug("Registering resource {}", clazz.getCanonicalName());
			register(clazz);
		}
		logger.info("Jersey configuration finished.");
	}
	
}
