package com.siemo.notif.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.siemo.notif.system.aspect.AuditTrail;
import com.siemo.notif.system.base.service.BaseBackendService;

@SpringBootApplication
@PropertySource(value = "classpath:/config/rest.properties")
public class NotifSystemApplication {
	
	@Autowired
	Environment env;

	public static void main(String[] args) {
		SpringApplication.run(NotifSystemApplication.class, args);

	}

	@Bean(name = "auditTrail")
	public AuditTrail auditTrail() {
		return new AuditTrail();
	}
	
	
	@Bean(name = "batchrest")
	public BaseBackendService getOmnirest(){
		return new BaseBackendService(
				env.getProperty("batch.username"),
				env.getProperty("batch.password"),
				new  RestTemplate());
	}

}
