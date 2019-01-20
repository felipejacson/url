package com.url.dto;

import java.util.List;

public class CustomResponseDto {

	private int status;
	private String message;
	private List list;
	private UrlDto data;

	public CustomResponseDto() {
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public UrlDto getData() {
		return data;
	}

	public void setData(UrlDto data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "CustomResponseDto{" +
				"status=" + status +
				", message='" + message + '\'' +
				", list=" + list +
				", data=" + data +
				'}';
	}
}
