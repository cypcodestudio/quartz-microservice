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
		log.info("INTERNALL PROCESSOR: batch Processor");
		log.info("EXECUTE INTERNAL PROCESSOR: business logic here");
		
	}

}
