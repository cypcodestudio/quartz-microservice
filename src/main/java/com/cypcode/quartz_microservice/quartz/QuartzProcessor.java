package com.cypcode.quartz_microservice.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class QuartzProcessor implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("abstract uartz processor");
	}

}
