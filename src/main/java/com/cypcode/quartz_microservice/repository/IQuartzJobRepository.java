package com.cypcode.quartz_microservice.repository;

import org.springframework.stereotype.Repository;

import com.cypcode.quartz_microservice.domain.QuartzJob;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface IQuartzJobRepository extends JpaRepository<QuartzJob, Long>{

}
