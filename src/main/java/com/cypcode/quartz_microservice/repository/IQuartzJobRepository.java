package com.cypcode.quartz_microservice.repository;

import org.springframework.stereotype.Repository;

import com.cypcode.quartz_microservice.domain.QuartzJob;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


@Repository
public interface IQuartzJobRepository extends JpaRepository<QuartzJob, Long>{
	QuartzJob findByName(String name);

}
