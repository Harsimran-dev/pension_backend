package com.pensionbackend.services;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pensionbackend.dtos.DefinedBenefitPensionSchemeDTO;
import com.pensionbackend.entities.Companies;
import com.pensionbackend.entities.DefinedBenifitPensionScheme;
import com.pensionbackend.entities.Job;
import com.pensionbackend.entities.PersonalDetails;
import com.pensionbackend.repositories.CompaniesRepository;
import com.pensionbackend.repositories.DefinedBenifitPensionSchemeRepository;
import com.pensionbackend.repositories.JobRepository;
import com.pensionbackend.repositories.PensionPotRepository;
import com.pensionbackend.repositories.UserDetailsRepository;

@Service
public class DefinedBenifitServiceimpl implements DefinedBenifitService {

    @Autowired
    private JobRepository jobRepository;

    private final DefinedBenifitPensionSchemeRepository pensionSchemeRepository;
    private final PensionPotRepository pensionPotRepository;
    private final CompaniesRepository companiesRepository;
    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public DefinedBenifitServiceimpl(DefinedBenifitPensionSchemeRepository pensionSchemeRepository,
            PensionPotRepository pensionPotRepository, CompaniesRepository companiesRepository,UserDetailsRepository userDetailsRepository) {
        this.pensionSchemeRepository = pensionSchemeRepository;
        this.pensionPotRepository = pensionPotRepository;
        this.companiesRepository = companiesRepository;
        this.userDetailsRepository=userDetailsRepository;
    }

    @Override
    public DefinedBenifitPensionScheme createDefinedPensionBenefitScheme(DefinedBenefitPensionSchemeDTO dto,
            double inflationRate, double taxRate, Long userId) {

        Job userJob = jobRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found for User ID: " + userId));

                        PersonalDetails userdetails = userDetailsRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found for User ID: " + userId));

                Date dateofbirth=userdetails.getDateOfBirth();

        Long companyId = userJob.getCompanyId();
        Double salary = userJob.getSalary();

        Companies company = companiesRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found for ID: " + companyId));

        int retirementGoalAge = dto.getRetirementGoalAge();

        int yearsOfService = calculateYearsOfService(userJob.getJobStarted(), retirementGoalAge,dateofbirth);

        String companyType = company.getType();

        if ("defined_benefit".equals(companyType)) {
            double netPension = calculateRetirementSalary(salary, yearsOfService, dto.getAccrualRate(),
                    inflationRate, taxRate);

            DefinedBenifitPensionScheme pensionScheme = new DefinedBenifitPensionScheme();
            pensionScheme.setUserId(userId);
            pensionScheme.setCompany(companyId);
            pensionScheme.setEmployeeId(dto.getEmployeeId());
            pensionScheme.setFinalSalary(salary);
            pensionScheme.setYearsOfService(yearsOfService);
            pensionScheme.setAccrualRate(dto.getAccrualRate());
            pensionScheme.setRetirementGoalAge(retirementGoalAge);
            pensionScheme.setNetPensionSalary(netPension);

            return pensionSchemeRepository.save(pensionScheme);
        } else if ("defined_contribution".equals(companyType)) {
            throw new IllegalArgumentException(
                    "Company is defined contribution and not compatible with defined benefit scheme");
        } else {
            throw new IllegalArgumentException("Invalid company type: " + companyType);
        }

    }

    private double calculateRetirementSalary(double finalSalary, int yearsOfService, double accrualRate,
            double inflationRate, double taxRate) {
        double pensionAmount = finalSalary * (accrualRate) * yearsOfService;

        double adjustedPensionAmount = adjustForInflation(pensionAmount, inflationRate);

        double netPensionAmount = adjustForTaxation(adjustedPensionAmount, taxRate);

        return netPensionAmount;
    }

    private double adjustForInflation(double pensionAmount, double inflationRate) {
        double adjustedPensionAmount = pensionAmount * (1 + inflationRate / 100);
        return adjustedPensionAmount;
    }

    private double adjustForTaxation(double pensionAmount, double taxRate) {
        double netPensionAmount = pensionAmount * (1 - taxRate);
        return netPensionAmount;
    }

    public DefinedBenifitPensionScheme findByUserId(Long userId) {
        return pensionSchemeRepository.findByUserId(userId);
    }

    @Override
    public void deleteDefinedPensionBenefitSchemeById(Long id) {
        pensionSchemeRepository.deleteById(id);
    }

    private int calculateYearsOfService(String jobStarted, int retirementGoalAge, Date dob) {
        LocalDate startDate = LocalDate.parse(jobStarted);
        LocalDate dobLocalDate = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dobPlusRetirementAge = dobLocalDate.plusYears(retirementGoalAge);
        LocalDate currentDate = LocalDate.now();
      
        int yearsToRetirement = Period.between(currentDate, dobPlusRetirementAge).getYears();
    return yearsToRetirement;
    }


    @Override
public void updateDefinedPensionBenefitScheme(DefinedBenefitPensionSchemeDTO dto, double inflationRate, double taxRate,Long userId) {
    DefinedBenifitPensionScheme pensionScheme = pensionSchemeRepository.findById(dto.getId())
            .orElseThrow(() -> new IllegalArgumentException("Pension Scheme not found for ID: " + dto.getId()));

    pensionScheme.setEmployeeId(dto.getEmployeeId());
    pensionScheme.setFinalSalary(dto.getFinalSalary());
    pensionScheme.setAccrualRate(dto.getAccrualRate());
    pensionScheme.setRetirementGoalAge(dto.getRetirementGoalAge());

    if (dto.getFinalSalary() != 0 || dto.getAccrualRate() != 0 || dto.getRetirementGoalAge() != 0) {
        Job userJob = jobRepository.findByUserId(pensionScheme.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Job not found for User ID: " + pensionScheme.getUserId()));
    
                PersonalDetails userdetails = userDetailsRepository.findByUserId(pensionScheme.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Job not found for User ID: " ));

           
        int yearsOfService = calculateYearsOfService(userJob.getJobStarted(), dto.getRetirementGoalAge(), userdetails.getDateOfBirth());

        double netPension = calculateRetirementSalary(dto.getFinalSalary(), yearsOfService, dto.getAccrualRate(), inflationRate, taxRate);
        pensionScheme.setNetPensionSalary(netPension);
    }

    pensionSchemeRepository.save(pensionScheme);
}

}
