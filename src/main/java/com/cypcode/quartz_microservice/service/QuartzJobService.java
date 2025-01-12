package com.cypcode.quartz_microservice.service;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Service;

import com.cypcode.quartz_microservice.domain.QuartzJob;
import com.cypcode.quartz_microservice.quartz.QuartzProcessor;
import com.cypcode.quartz_microservice.repository.IQuartzJobRepository;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import java.nio.file.*;

@Slf4j
@Service
public class QuartzJobService {

	@Autowired
	private IQuartzJobRepository quartzJobRepository;
	
	 @Autowired
	 private SchedulerFactoryBean schedulerFactoryBean;

	
	public QuartzJob save(QuartzJob job){
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			JobDetail jobDetail = jobDetail(job);
	        scheduler.addJob(jobDetail, true);
			scheduler.scheduleJob(jobTrigger(jobDetail, job));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (SchedulerException e) {
			e.printStackTrace();
		} 
		
		return quartzJobRepository.save(job);
	}
	
	public List<QuartzJob> retrieveAllJobs(){
		return quartzJobRepository.findAll();
	}
	
	public Trigger jobTrigger(JobDetail detail, QuartzJob job) {
		return TriggerBuilder.newTrigger()
				.forJob(detail)
				.startNow()
				.withIdentity(job.getName())
				.withDescription(job.getDescription())
				.withSchedule(CronScheduleBuilder.cronSchedule(job.getExpression()))
				.build();
	}
	
	public JobDetail jobDetail(QuartzJob job) throws ClassNotFoundException {

		try {
			Class<QuartzProcessor> clazz = (Class<QuartzProcessor>) Class.forName(job.getClazzjndi());
	    	
			 log.info("Job created with processor: {}", job.getName());
			return JobBuilder.newJob()
					.ofType(clazz)
					.storeDurably(true)
					.withIdentity(job.getName())
					.withDescription(job.getDescription())
					.build();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void startTrigger(String name) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		try {
			QuartzJob job = quartzJobRepository.findByName(name);
			
			JobDetail jobDetail = jobDetail(job);
        scheduler.addJob(jobDetail, true);
		scheduler.scheduleJob(jobTrigger(jobDetail, job));
		}catch(Exception e) {
			log.error(e.getMessage());
		}
		
	}
	
	public void pauseTrigger(String name) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		scheduler.pauseTrigger(new TriggerKey(name));
	}
	
	public void resumeTrigger(String name) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		scheduler.resumeTrigger(new TriggerKey(name));
	}
	
	public void stopTrigger(String name) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		scheduler.unscheduleJob(new TriggerKey(name));
	}
	
}
