package com.siemo.notif.system.message;

import java.util.ArrayList;


public class BatchRecipients {
	private ArrayList<String> tokens;
	
	public BatchRecipients() {
		
	}
	
	public BatchRecipients(ArrayList<String> tokens) {
		this.tokens = tokens;
	}

	public ArrayList<String> getTokens() {
		return tokens;
	}

	public void setTokens(ArrayList<String> tokens) {
		this.tokens = tokens;
	}  
}
