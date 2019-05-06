package com.vma.restrxjdbc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="spring.swagger")
public class SwaggerProperties {

	private String basePath;
	
	private String title;
	
	private String version;
	
	private String contact;
	
	private String schemes;
	
	private String resourcePackage;
	
	private Boolean prettyPrint;

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getSchemes() {
		return schemes;
	}

	public void setSchemes(String schemes) {
		this.schemes = schemes;
	}

	public String getResourcePackage() {
		return resourcePackage;
	}

	public void setResourcePackage(String resourcePackage) {
		this.resourcePackage = resourcePackage;
	}

	public Boolean getPrettyPrint() {
		return prettyPrint;
	}

	public void setPrettyPrint(Boolean prettyPrint) {
		this.prettyPrint = prettyPrint;
	}
	
}
