package com.pensionbackend.services;

import com.pensionbackend.dtos.JobDto;
import com.pensionbackend.entities.Companies;
import com.pensionbackend.entities.Job;
import com.pensionbackend.entities.PensionUser;
import com.pensionbackend.entities.RequestApproval;

import com.pensionbackend.repositories.CompaniesRepository;
import com.pensionbackend.repositories.JobRepository;
import com.pensionbackend.repositories.PensionUserRepository;
import com.pensionbackend.repositories.RequestApprovalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final PensionUserRepository userRepository;
    private final RequestApprovalRepository requestApprovalRepository;
    private final EmailSendDoc emailSenddoc;
    private final CompaniesRepository companiesRepository;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository,
                          PensionUserRepository userRepository,
                          RequestApprovalRepository requestApprovalRepository,
                          EmailSendDoc emailSenddoc,
                          CompaniesRepository companiesRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.requestApprovalRepository = requestApprovalRepository;
        this.emailSenddoc = emailSenddoc;
        this.companiesRepository = companiesRepository;
    }

    @Override
    public Job createJob(JobDto jobDto,String token) {
        Optional<Job> existingJob = jobRepository.findByUserId(jobDto.getUserId());
        if (existingJob.isPresent()) {
            throw new IllegalStateException("A job already exists for the user ID: " + jobDto.getUserId());
        }
      Job job = new Job();

        job.setSalary(jobDto.getSalary());
        job.setEmployeeId(jobDto.getEmployeeId());
        job.setJobStarted(jobDto.getJobStarted());
        job.setJobTitle(jobDto.getJobTitle());
    
        job.setCompanyId(jobDto.getCompanyId());
        Optional<PensionUser> userOptional = userRepository.findById(jobDto.getUserId());
    if (userOptional.isPresent()) {
        
       
     
      
        job.setUserId(jobDto.getUserId());
    } else {
        throw new IllegalArgumentException("User not found with ID: ");
    }

    Optional<Companies> companyOptional = companiesRepository.findById(jobDto.getCompanyId());
    if (!companyOptional.isPresent()) {
        throw new IllegalArgumentException("Company not found with ID: " + jobDto.getCompanyId());
    }
    Companies company = companyOptional.get();
    PensionUser user = userOptional.get();
    Optional<RequestApproval> existingRequestApproval = requestApprovalRepository.findByUserId(jobDto.getUserId());
    if (existingRequestApproval.isPresent()) {
        RequestApproval requestApproval = existingRequestApproval.get();
        String randomUserNumber = requestApproval.getRandomUserNumber();

        String userEmail = "bjasper775@gmail.com";
String subject = "Request for Job Approval";
String approvalLink = "http://localhost:8080/api/v1/auth/pension/update?randomUserNumber=" + randomUserNumber + "&approved=true" ;
String rejectionLink = "http://localhost:8080/api/v1/auth/pension/update?randomUserNumber=" + randomUserNumber + "&approved=false" ;

String body = "Dear " + company.getName() + ",\n\nOne of your employees, " + user.getFirstName() + " " + user.getLastName() + " (Employee ID: " + jobDto.getEmployeeId() + "), wants to enroll in the pension scheme. Please click the following link to approve the request: " + approvalLink + "\n\nIf you wish to reject the request, please click the following link: " + rejectionLink + "\n\nThank you.";

emailSenddoc.sendEmail(userEmail, subject, body);


    
    }

       RequestApproval requestApproval = new RequestApproval();
    requestApproval.setUserId(jobDto.getUserId());

    requestApproval.setApproved(false);
    String randomUserNumber = generateRandomNumber();
    requestApproval.setRandomUserNumber(randomUserNumber);

    requestApprovalRepository.save(requestApproval);

    String userEmail = "bjasper775@gmail.com";
String subject = "Request for Job Approval";
String approvalLink = "http://localhost:8080/api/v1/auth/pension/update?randomUserNumber=" + randomUserNumber + "&approved=true" ;
String rejectionLink = "http://localhost:8080/api/v1/auth/pension/update?randomUserNumber=" + randomUserNumber + "&approved=false" ;

String body = "Dear " + company.getName() + ",\n\nOne of your employees, " + user.getFirstName() + " " + user.getLastName() + " (Employee ID: " + jobDto.getEmployeeId() + "), wants to enroll in the pension scheme. Please click the following link to approve the request: " + approvalLink + "\n\nIf you wish to reject the request, please click the following link: " + rejectionLink + "\n\nThank you.";

emailSenddoc.sendEmail(userEmail, subject, body);

return jobRepository.save(job);
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public Job updateJob(Long jobId, JobDto jobDto) {
        Job job = jobRepository.findById(jobId).orElse(null);
        if (job != null) {
             job.setEmployeeId(jobDto.getEmployeeId());
            job.setSalary(jobDto.getSalary());
            job.setJobStarted(jobDto.getJobStarted());
            job.setJobTitle(jobDto.getJobTitle());
       
            job.setUserId(jobDto.getUserId());
            return jobRepository.save(job);
        }
        return null; 
    }

    @Override
    public void deleteJob(Long jobId) {
        jobRepository.deleteById(jobId);
    }

    @Override
    public  Optional<Job>  getJobsByUserId(Long userId) {
        return jobRepository.findByUserId(userId);
    }

    private String generateRandomNumber() {
        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000);
        return String.valueOf(randomNumber);
    }



    @Override
    public String getCompanyTypeByUserId(Long userId) {
        Optional<Job> job = jobRepository.findByUserId(userId);
        
        if (job.isPresent()) {
            Long companyId = job.get().getCompanyId();
            
            Companies company = companiesRepository.findById(companyId).orElse(null);
            
            if (company != null) {
                return company.getType();
            } else {
                return "Company not found";
            }
        } else {
            return "Job not found for the given user ID";
        }
    }
}
