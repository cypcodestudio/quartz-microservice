package com.cypcode.quartz_microservice.controller;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cypcode.quartz_microservice.domain.QuartzJob;
import com.cypcode.quartz_microservice.service.QuartzJobService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("schedule")
public class SchedulerController {
	
	@Autowired
	private QuartzJobService quartzJobService;

	@PostMapping("register")
	public ResponseEntity<?> createJob(@RequestBody QuartzJob request) {
		log.info("Batch Job {} created successfully", request.getName());
		return ResponseEntity.ok(quartzJobService.save(request));
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<?> putMethodName(@PathVariable("id") long id, @RequestBody QuartzJob request) {
		return ResponseEntity.ok(quartzJobService.save(request));
	}
	
	@GetMapping("job-list")
	public ResponseEntity<?> retriveJob() {
		return ResponseEntity.ok(quartzJobService.retrieveAllJobs());
	}
	
	@PostMapping("start/{name}")
	public ResponseEntity<?> executeTrigger(@PathVariable("name") String name) {
		try {
			quartzJobService.startTrigger(name);
			return ResponseEntity.ok().build();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
	@PostMapping("pause/{name}")
	public ResponseEntity<?> pauseTrigger(@PathVariable("name") String name) {
		try {
			quartzJobService.pauseTrigger(name);
			return ResponseEntity.ok().build();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
	@PostMapping("stop/{name}")
	public ResponseEntity<?> stopTrigger(@PathVariable("name") String name) {
		try {
			quartzJobService.stopTrigger(name);
			return ResponseEntity.ok().build();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
	@PostMapping("resume/{name}")
	public ResponseEntity<?> resumeTrigger(@PathVariable("name") String name) {
		try {
			quartzJobService.resumeTrigger(name);
			return ResponseEntity.ok().build();
		} catch (SchedulerException e) {
			log.error(e.getMessage());
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
}
