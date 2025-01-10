package com.cypcode.quartz_microservice.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j

public class InternalProcessor extends QuartzProcessor{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("Welcome to Cypcode Internal batch Processor");
		log.info("Execute your Internal business logic here");
		
	}

}
