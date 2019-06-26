package com.siemo.notif.system.aspect;

import java.io.IOException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;



@Aspect
@Qualifier("auditTrail")
public class AuditTrail {
	
	org.slf4j.Logger LOG_CONTROLLER = LoggerFactory.getLogger("auditController");
	org.slf4j.Logger LOG_SERVICE = LoggerFactory.getLogger("auditService");
	

	    @Before("execution(* com.siemo.notif.system.controller.*.*(..))")
	    public void before(JoinPoint joinPoint) {
	    	LOG_CONTROLLER.info("before method" + joinPoint.getSignature().getName() + "Class" 
	    						+ joinPoint.getTarget().getClass().getSimpleName());
	    	
	    }
	    
	    @AfterReturning(pointcut = "(execution(* com.siemo.notif.system.controller.*.*(..))) && args(request)", returning = "result")
	    public void afterReturning(JoinPoint joinPoint, Object request, Object result) throws IOException {
	    	
	    ObjectWriter objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
	    
	    String req = objectWriter.writeValueAsString(request);
	    String res = objectWriter.writeValueAsString(result);
	    
	    System.out.println(req);
	    System.out.println(res);
	    }
	    
}
