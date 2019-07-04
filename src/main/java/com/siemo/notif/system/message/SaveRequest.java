package com.siemo.notif.system.message;


public class SaveRequest {
	
	
	
	public String userId;
	public String tokenDevice;
	public String channel;
	public String status;
	public String versi;
	private String category;
	private String detail;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTokenDevice() {
		return tokenDevice;
	}

	public void setTokenDevice(String tokenDevice) {
		this.tokenDevice = tokenDevice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersi() {
		return versi;
	}

	public void setVersi(String versi) {
		this.versi = versi;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
