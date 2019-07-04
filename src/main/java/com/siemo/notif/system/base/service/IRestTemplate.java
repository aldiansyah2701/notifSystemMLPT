package com.siemo.notif.system.base.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

public interface IRestTemplate {

	public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... urlVariables)
			throws RestClientException;

	public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType,
			Object... uriVariables) throws RestClientException;

	public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
			Class<T> responseType, Object... uriVariables) throws RestClientException;

}
