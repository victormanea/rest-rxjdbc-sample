package com.vma.restrxjdbc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="sample.jersey")
public class JerseyProperties {
	
	private String resources;

	public String getResources() {
		return resources;
	}

	public void setResources(String register) {
		this.resources = register;
	}
	
}
