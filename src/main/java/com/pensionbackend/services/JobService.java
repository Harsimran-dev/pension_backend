package com.pensionbackend.services;

import com.pensionbackend.dtos.JobDto;
import com.pensionbackend.entities.Job;

import java.util.List;
import java.util.Optional;

public interface JobService {
    Job createJob(JobDto jobDto, String token);
  
    List<Job> getAllJobs();
    Job updateJob(Long jobId, JobDto jobDto);
    void deleteJob(Long jobId);
    Optional<Job> getJobsByUserId(Long userId);
    String getCompanyTypeByUserId(Long userId);
}
