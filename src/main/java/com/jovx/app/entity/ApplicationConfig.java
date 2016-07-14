package com.jovx.app.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.jovx.jpa.IdEntity;

@Entity
public class ApplicationConfig extends IdEntity {
	private String applicationInstance;
	private ModuleConfig defaultModule;
	private List<ModuleConfig> moduleConfigs = new ArrayList<ModuleConfig>();
	@CollectionTable(name = "status")
	private List<String> appStatus = new ArrayList<String>();

	public List<String> getAppStatus() {
		return appStatus;
	}

	public void addStatus(String x) {
		appStatus.add(x);
	}

	public void setAppStatus(List<String> appStatus) {
		this.appStatus = appStatus;
	}

	private Boolean defaultConfig = true;
	private String title;
	private int width = 1200;
	private int height = 640;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void addModuleConfig(ModuleConfig moduleConfig) {
		this.moduleConfigs.add(moduleConfig);
		moduleConfig.setApplicationConfig(this);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getDefaultConfig() {
		return defaultConfig;
	}

	public void setDefaultConfig(Boolean defaultConfig) {
		this.defaultConfig = defaultConfig;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "applicationConfig")
	public List<ModuleConfig> getModuleConfigs() {
		return moduleConfigs;
	}

	public void setModuleConfigs(List<ModuleConfig> moduleConfigs) {
		this.moduleConfigs = moduleConfigs;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public ModuleConfig getDefaultModule() {
		return defaultModule;
	}

	public void setDefaultModule(ModuleConfig defaultModule) {
		this.defaultModule = defaultModule;
	}

	public String getApplicationInstance() {
		return applicationInstance;
	}

	public void setApplicationInstance(String applicationInstance) {
		this.applicationInstance = applicationInstance;
	}

}
