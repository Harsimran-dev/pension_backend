package com.pensionbackend.controllers;
import org.springframework.http.HttpHeaders;

import com.pensionbackend.dtos.JobDto;
import com.pensionbackend.entities.Job;
import com.pensionbackend.repositories.JobRepository;
import com.pensionbackend.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user/jobs")
public class JobController {

  private final JobService jobService;
    private final JobRepository jobRepository;

    @Autowired
    public JobController(JobService jobService, JobRepository jobRepository) {
        this.jobService = jobService;
        this.jobRepository = jobRepository;
    }

    @PostMapping("/user")
    public ResponseEntity<?> createJobWithUserIdInBody(
        @RequestBody JobDto jobDto,
        @RequestHeader HttpHeaders headers
    ) {
        String token = headers.getFirst("Authorization");
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body("Authorization token not provided");
        }
        
        
        Long userId = jobDto.getUserId();
        if (userId == null) {
            return ResponseEntity.badRequest().body("User ID is required");
        }
    
        Optional<Job> existingJob = jobRepository.findByUserId(userId);
        if (existingJob.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A job already exists for the user ID: " + userId);
        }
    
        Job createdJob = jobService.createJob(jobDto,token);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
    }
    
    

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<Job> updateJob(@PathVariable Long jobId, @RequestBody JobDto jobDto) {
        Job updatedJob = jobService.updateJob(jobId, jobDto);
        if (updatedJob == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedJob);
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long jobId) {
        jobService.deleteJob(jobId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Optional<Job>> getJobsByUserId(@PathVariable Long userId) {
        Optional<Job> jobs = jobService.getJobsByUserId(userId);
        return ResponseEntity.ok(jobs);
    }
}
