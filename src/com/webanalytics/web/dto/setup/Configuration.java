package com.webanalytics.web.dto.setup;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Configuration {

	private Integer id;
	private String configurationName;
	private String website;
	private String websiteTrackId;
	private String scriptUrl;
	private int refreshRate;
	
	private List<SiteAlias> allSiteAlias = new ArrayList<SiteAlias>();
	
	public List<SiteAlias> getAllSiteAlias() {
		return allSiteAlias;
	}
	public void setAllSiteAlias(List<SiteAlias> allSiteAlias) {
		this.allSiteAlias = allSiteAlias;
	}
	public String getScriptUrl() {
		return scriptUrl;
	}
	public void setScriptUrl(String scriptUrl) {
		this.scriptUrl = scriptUrl;
	}
	public String getConfigurationName() {
		return configurationName;
	}
	public void setConfigurationName(String configurationName) {
		this.configurationName = configurationName;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getWebsiteTrackId() {
		return websiteTrackId;
	}
	public void setWebsiteTrackId(String websiteTrackId) {
		this.websiteTrackId = websiteTrackId;
	}
	public int getRefreshRate() {
		return refreshRate;
	}
	public void setRefreshRate(int refreshRate) {
		this.refreshRate = refreshRate;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Configuration other = (Configuration) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
