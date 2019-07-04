package com.siemo.notif.system.message;

public class SendBatchRequest {

	private String group_id;
	private String push_time;
	private BatchMessage batchMessage = new BatchMessage();
	private BatchRecipients batchRecipients = new BatchRecipients();
	private boolean sandbox;
	private String deeplink;
	private String custom_payload;

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getPush_time() {
		return push_time;
	}

	public void setPush_time(String push_time) {
		this.push_time = push_time;
	}

	public boolean isSandbox() {
		return sandbox;
	}

	public void setSandbox(boolean sandbox) {
		this.sandbox = sandbox;
	}

	public String getDeeplink() {
		return deeplink;
	}

	public void setDeeplink(String deeplink) {
		this.deeplink = deeplink;
	}

	public String getCustom_payload() {
		return custom_payload;
	}

	public void setCustom_payload(String custom_payload) {
		this.custom_payload = custom_payload;
	}

	public BatchMessage getMessage() {
		return batchMessage;
	}

	public void setMessage(BatchMessage batchMessage) {
		this.batchMessage = batchMessage;
	}

	public BatchRecipients getRecipients() {
		return batchRecipients;
	}

	public void setRecipients(BatchRecipients batchRecipients) {
		this.batchRecipients = batchRecipients;
	}

}
