package com.jovx.app.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.jovx.jpa.IdEntity;

@Entity
public class ModuleConfig extends IdEntity {
	private String moduleClazz;
	private String title;
	private String image;
	private String resourceName;
	private String description;

	private ApplicationConfig applicationConfig;

	@ManyToOne(cascade = CascadeType.ALL)
	public ApplicationConfig getApplicationConfig() {
		return applicationConfig;
	}

	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getModuleClazz() {
		return moduleClazz;
	}

	public void setModuleClazz(String moduleClazz) {
		this.moduleClazz = moduleClazz;
	}

}
