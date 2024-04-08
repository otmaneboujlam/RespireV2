package com.diginamic.apijava.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLoggerAspect {
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@AfterThrowing(pointcut = "within(com.diginamic.apijava.service..*)", throwing = "exception")
	public void logAfterThrowingMethod(JoinPoint joinPoint, Exception exception) {
		LOG.warn("Exception "+exception.toString());
	}

}
