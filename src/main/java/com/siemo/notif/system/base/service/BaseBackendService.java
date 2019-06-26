package com.siemo.notif.system.base.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;

public class BaseBackendService implements IRestTemplate{
	
	private String username;
	private String password;
	private RestTemplate restTemplate;
	
	public BaseBackendService(String username, String password, RestTemplate restTemplate) {
		this.username = username;
		this.password = password;
		this.restTemplate = restTemplate;
		addAuthentication();
	}
	
	protected void addAuthentication() {
		if (StringUtils.isEmpty(username)) {
			throw new RuntimeException("Username is mandatory for Basic Auth");
		}
		
		BasicAuthInterceptor intercept = new BasicAuthInterceptor(username,password);
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(intercept);
		restTemplate.setRequestFactory(new InterceptingClientHttpRequestFactory(
				restTemplate.getRequestFactory(), interceptors));
	}

	@Override
	public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... urlVariables)
			throws RestClientException {
		ResponseEntity<T> forEntityResponse = restTemplate.getForEntity(url, responseType, urlVariables);
		ObjectMapper mapper = new ObjectMapper();
		String body = null;
		try {
			body = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(forEntityResponse.getBody());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return forEntityResponse;
	}

	@Override
	public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType,
			Object... uriVariables) throws RestClientException {
		ObjectMapper mapperReq = new ObjectMapper();

		String reqBody = null;
		try {
			reqBody = mapperReq.writerWithDefaultPrettyPrinter().writeValueAsString(request);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String body = null;
		ResponseEntity<T> postForEntity = restTemplate.postForEntity(url, request, responseType, uriVariables);
		try {
			body = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(postForEntity.getBody());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return postForEntity;
	}

	@Override
	public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
			Class<T> responseType, Object... uriVariables) throws RestClientException {
		ObjectMapper mapper = new ObjectMapper();
		String body = null;
		ResponseEntity<T> deleteResponse = restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
		try {
			body = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(deleteResponse.getBody());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return deleteResponse;
	}

}
