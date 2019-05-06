package com.vma.restrxjdbc.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfiguration extends BeanConfig {

	private static final Logger logger = LoggerFactory.getLogger(SwaggerConfiguration.class);

	@Autowired 
	private SwaggerProperties properties;
	
	@Autowired
	private JerseyConfiguration resource;
	
	@PostConstruct
	public void init() {
		resource.register(ApiListingResource.class);
		resource.register(SwaggerSerializers.class);
		
		this.setTitle(properties.getTitle());
		this.setVersion(properties.getVersion());
		this.setContact(properties.getContact());
		this.setSchemes(properties.getSchemes().split(","));
		this.setBasePath(properties.getBasePath());
		this.setResourcePackage(properties.getResourcePackage());
		this.setPrettyPrint(properties.getPrettyPrint());
		this.setScan(true);
		
		logger.info("Swagger configuration finished.");
	}

}
