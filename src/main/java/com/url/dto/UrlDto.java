package com.url.dto;

public class UrlDto {

	private Long id;
	private String url;
	private String friendlyName;

	public UrlDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFriendlyName() {
		return friendlyName;
	}

	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}

	@Override
	public String toString() {
		return "UrlDto{" +
				"id=" + id +
				", url='" + url + '\'' +
				", friendlyName='" + friendlyName + '\'' +
				'}';
	}
}
