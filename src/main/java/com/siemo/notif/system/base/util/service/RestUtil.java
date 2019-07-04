package com.siemo.notif.system.base.util.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service(value = "restUtil")
@PropertySource(value="classpath:/config/rest.properties")
public class RestUtil {
	
	@Autowired
	private Environment env;
	
	private UriComponentsBuilder getURIbUILBuilder(){
		String serviceHost = env.getProperty("batch.host");
		int servicePort = Integer.parseInt(env.getProperty("batch.port"));
		
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
        .scheme(env.getProperty("default.scheme"))
        .host(serviceHost).port(servicePort);
		return uriComponentsBuilder;
	}
	
	private UriComponentsBuilder getURIbUILBuilderNoPort(){
		String serviceHost = env.getProperty("batch.host");
		
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
        .scheme(env.getProperty("default.scheme"))
        .host(serviceHost);
		return uriComponentsBuilder;
	}
	
	public String generateURI(String pathUri) {
		UriComponentsBuilder uriBuilder = getURIbUILBuilderNoPort();
		String pathBase = env.getProperty("batch.base.path");
		UriComponentsBuilder path = uriBuilder.path(pathBase+pathUri);
		UriComponents build = path
				.build()
				.encode();
		
		
		String uri = build.toUriString();
		return uri;
	}

}
