package com.siemo.notif.system.base.service;

import java.io.IOException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class BasicAuthInterceptor implements ClientHttpRequestInterceptor{
	
	private String username;
	private String password;

	public BasicAuthInterceptor(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution)
			throws IOException {
		HttpHeaders headers = httpRequest.getHeaders();
//		headers.add(HttpHeaders.AUTHORIZATION, encodeCredentialsForBasicAuth(username, password));
		
		headers.add("X-Authorization", "f531c42b9529187f1eb4bbe885ed2e8e");
		headers.add("Content-Type", "application/json");
		headers.add("User-Agent", "BatchPushSender 1.0");
		
//		headers.setContentType(MediaType.APPLICATION_JSON);
		return clientHttpRequestExecution.execute(httpRequest, bytes);
		
	}
	
	public static String encodeCredentialsForBasicAuth(String username, String password) {
		return "Basic " + new Base64().encodeToString((username + ":" + password).getBytes());
	}

}
