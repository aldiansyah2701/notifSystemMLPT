package com.siemo.notif.system.message;

public class SaveRequest {
	
	
	
	public String userId;
	public String tokenDevice;
	public String channel;
	public String systemOperasi;
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getSystemOperasi() {
		return systemOperasi;
	}

	public void setSystemOperasi(String systemOperasi) {
		this.systemOperasi = systemOperasi;
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
	
	

}
