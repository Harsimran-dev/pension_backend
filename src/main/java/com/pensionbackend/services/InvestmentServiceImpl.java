package com.pensionbackend.services;

import com.pensionbackend.dtos.InvestmentDTO;
import com.pensionbackend.entities.Compliance;
import com.pensionbackend.entities.DefinedContribution;
import com.pensionbackend.entities.FundTransferHistory;
import com.pensionbackend.entities.Investment;
import com.pensionbackend.entities.PensionPot;
import com.pensionbackend.entities.PensionUser;
import com.pensionbackend.enums.ComplianceTrigger;
import com.pensionbackend.enums.InvestmentResult;
import com.pensionbackend.repositories.ComplianceRepository;
import com.pensionbackend.repositories.DefinedContributionRepository;
import com.pensionbackend.repositories.FundTransferHistoryRepository;
import com.pensionbackend.repositories.InvestmentRepository;
import com.pensionbackend.repositories.PensionPotRepository;
import com.pensionbackend.repositories.PensionUserRepository;

import jakarta.persistence.EntityNotFoundException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvestmentServiceImpl implements InvestmentService {
    private final InvestmentRepository investmentRepository;
    private final PensionPotRepository pensionPotRepository;
    private final PensionUserRepository pensionUserRepository;
    private final EmailSendDocImpl emailSendDoc;
    private final DefinedContributionRepository definedContributionRepository;
    private final FundTransferHistoryRepository fundTransferRepository;
    private final ComplianceRepository complianceRepository;

    @Autowired
    public InvestmentServiceImpl(InvestmentRepository investmentRepository,
            PensionPotRepository pensionPotRepository,
            PensionUserRepository pensionUserRepository,
            EmailSendDocImpl emailSendDoc,
            DefinedContributionRepository definedContributionRepository,
            FundTransferHistoryRepository fundTransferRepository,
            ComplianceRepository complianceRepository) {
        this.investmentRepository = investmentRepository;
        this.pensionPotRepository = pensionPotRepository;
        this.pensionUserRepository = pensionUserRepository;
        this.emailSendDoc = emailSendDoc;
        this.definedContributionRepository = definedContributionRepository;
        this.fundTransferRepository = fundTransferRepository;
        this.complianceRepository = complianceRepository;
    }

    @Override
    public Investment updateInvestment(Long investmentId, double currentPrice) {
        Investment investment = investmentRepository.findById(investmentId)
                .orElseThrow(() -> new EntityNotFoundException("Investment not found"));

        double purchasePrice = investment.getPurchasePrice();
        double profitOrLoss = (currentPrice - purchasePrice) * investment.getQuantity();

        investment.setCurrentPrice(currentPrice);
        investment.setMarketValue(currentPrice * investment.getQuantity());

        if (profitOrLoss > 0) {
            investment.setInvestmentStatus(InvestmentResult.PROFIT.toString());
        } else if (profitOrLoss < 0) {
            investment.setInvestmentStatus(InvestmentResult.LOSS.toString());
        } else {
            investment.setInvestmentStatus(InvestmentResult.NO_PROFIT_NO_LOSS.toString());
        }

        investment.setInvestmentReturn(Math.abs(profitOrLoss));

        return investmentRepository.save(investment);
    }

    @Override
    public void transferProfitToPot(Long investmentId, Long pensionPotId) {
        Investment investment = investmentRepository.findById(investmentId)
                .orElseThrow(() -> new EntityNotFoundException("Investment not found"));

        DefinedContribution definedContribution = definedContributionRepository.findByInvestmentOptionsId(investmentId);

        if (definedContribution != null) {
            definedContributionRepository.delete(definedContribution);
        }

        PensionPot pensionPot = pensionPotRepository.findById(pensionPotId)
                .orElseThrow(() -> new EntityNotFoundException("PensionPot not found"));

        double profit = investment.getCurrentPrice() * investment.getQuantity();

        pensionPot.setTotalAmount(pensionPot.getTotalAmount() + profit);
        pensionPotRepository.save(pensionPot);

        FundTransferHistory fundTransfer = new FundTransferHistory();
        fundTransfer.setFromAccount(investment.getName());
        fundTransfer.setToAccount(pensionPot.getName());
        fundTransfer.setAmount(profit);
        fundTransfer.setCreationDate(new java.sql.Date(new Date().getTime()));
        fundTransfer.setUserId(investment.getUser().getId());
        fundTransferRepository.save(fundTransfer);

        investmentRepository.deleteById(investmentId);
    }

    @Override
    public void getAllInvestmentsForUser(Long userId) {
        PensionUser user = pensionUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<Investment> investments = investmentRepository.findByUserId(userId);
        if (investments != null) {
            System.out.println("Number of investments: " + investments.size());

            for (Investment investment : investments) {
                if (calculateTotalLoss(investment) > 1000 && calculateTotalLoss(investment) < 5000) {
                    LocalDate today = LocalDate.now();
                    LocalDate purchaseDate = Instant.ofEpochMilli(investment.getPurchaseDate().getTime())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    long daysDifference = ChronoUnit.DAYS.between(purchaseDate, today);
                    if (daysDifference <= 1) {
                        Compliance existingCompliance = complianceRepository.findByInvestmentId(investment.getId());
                        if (existingCompliance == null) {
                            Compliance compliance = new Compliance();
                            compliance.setUser(user);
                            compliance.setInvestment(investment);
                            compliance.setAction("Loss exceeding 1000 pounds within 1 day of purchase.");
                            compliance.setReason(ComplianceTrigger.LOSS_1000_1_DAY);
                            compliance.setNotificationDate(LocalDateTime.now());
                            compliance.setCreatedAt(LocalDateTime.now());
                            compliance.setUpdatedAt(LocalDateTime.now());
                            compliance.setEmailSent(true);
                            complianceRepository.save(compliance);
                            sendEmail(investment, "Loss exceeding 1000 pounds within 1 day of purchase.",
                                    "LOSS_1000_1_DAY(Severity.MODERATE)");
                        } else {
                            existingCompliance.setAction("Loss exceeding 1000 pounds within 1 day of purchase.");
                            existingCompliance.setReason(ComplianceTrigger.LOSS_1000_1_DAY);
                            existingCompliance.setCreatedAt(LocalDateTime.now());
                            existingCompliance.setUpdatedAt(LocalDateTime.now());
                            complianceRepository.save(existingCompliance);
                            Compliance existinggCompliance = complianceRepository
                                    .findByInvestmentIdAndReason(investment.getId(), ComplianceTrigger.LOSS_1000_1_DAY);
                            if (existinggCompliance == null) {
                                sendEmail(investment, "Loss exceeding 1000 pounds within 1 day of purchase.",
                                        "LOSS_1000_1_DAY(Severity.MODERATE)");

                            }

                        }
                    }
                }
                if (calculateTotalLoss(investment) > 5000) {
                    LocalDate today = LocalDate.now();
                    LocalDate purchaseDate = Instant.ofEpochMilli(investment.getPurchaseDate().getTime())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    long daysDifference = ChronoUnit.DAYS.between(purchaseDate, today);
                    if (daysDifference >= 5) {
                        Compliance existingCompliance = complianceRepository.findByInvestmentId(investment.getId());
                        if (existingCompliance == null) {
                            Compliance compliance = new Compliance();
                            compliance.setUser(user);
                            compliance.setInvestment(investment);
                            compliance.setAction("Loss trigger: Loss exceeding 5000 pounds in 5 days of purchase.");
                            compliance.setReason(ComplianceTrigger.SPEND_5000_5_DAY);
                            compliance.setNotificationDate(LocalDateTime.now());
                            compliance.setCreatedAt(LocalDateTime.now());
                            compliance.setUpdatedAt(LocalDateTime.now());
                            compliance.setEmailSent(true);
                            complianceRepository.save(compliance);

                        } else {
                            existingCompliance
                                    .setAction("Loss trigger: Loss exceeding 5000 pounds in 5 days of purchase.");
                            existingCompliance.setReason(ComplianceTrigger.SPEND_5000_5_DAY);
                            existingCompliance.setUpdatedAt(LocalDateTime.now());
                            complianceRepository.save(existingCompliance);
                            Compliance existinggCompliance = complianceRepository.findByInvestmentIdAndReason(
                                    investment.getId(), ComplianceTrigger.SPEND_5000_5_DAY);
                            if (existinggCompliance == null) {
                                sendEmail(investment, "Loss trigger: Loss exceeding 5000 pounds in 5 days of purchase.",
                                        "LOSS_5000_5_DAY(Severity.HIGH)");

                            }

                        }

                    }
                }

                Double total = investment.getPurchasePrice() * investment.getQuantity();

                if (total > 10000) {
                    LocalDate today = LocalDate.now();
                    LocalDate purchaseDate = Instant.ofEpochMilli(investment.getPurchaseDate().getTime())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    long daysDifference = ChronoUnit.DAYS.between(purchaseDate, today);
                    if (daysDifference <= 1) {
                        Compliance existingCompliances = complianceRepository.findByInvestmentId(investment.getId());
                        if (existingCompliances == null) {
                            Compliance compliance = new Compliance();
                            compliance.setUser(user);
                            compliance.setInvestment(investment);
                            compliance.setAction("Spend more than 10000 in 1 day");
                            compliance.setReason(ComplianceTrigger.SPEND_10000_1_DAY);
                            compliance.setNotificationDate(LocalDateTime.now());
                            compliance.setCreatedAt(LocalDateTime.now());
                            compliance.setUpdatedAt(LocalDateTime.now());
                            compliance.setCreatedAt(LocalDateTime.now());
                            compliance.setUpdatedAt(LocalDateTime.now());
                            compliance.setEmailSent(true);

                            complianceRepository.save(compliance);
                            sendEmail(investment, "Spend more than 10000 in 1 day", "SPEND_10000_1_DAY(Severity.HIGH)");
                        }

                        else {
                            existingCompliances.setAction("Spend more than 10000 in 1 day");
                            existingCompliances.setReason(ComplianceTrigger.SPEND_10000_1_DAY);
                            existingCompliances.setUpdatedAt(LocalDateTime.now());
                            complianceRepository.save(existingCompliances);
                            Compliance existinggCompliance = complianceRepository.findByInvestmentIdAndReason(
                                    investment.getId(), ComplianceTrigger.SPEND_10000_1_DAY);
                            if (existinggCompliance == null) {
                                sendEmail(investment, "Spend more than 10000 in 1 day",
                                        "SPEND_10000_1_DAY(Severity.HIGH)");

                            }

                        }

                    }

                }
                ;
            }
        } else {
            System.out.println("Investments list is null or empty.");
        }

    }

    private double calculateTotalLoss(Investment investment) {
        System.out.println("Debugging calculateTotalLoss method...");

        String investmentStatus = investment.getInvestmentStatus();

        System.out.println("Investment Status: " + investmentStatus);

        if (investmentStatus != null && investmentStatus.equals(InvestmentResult.LOSS.toString())) {
            double investmentReturn = investment.getInvestmentReturn();
            System.out.println("Investment Return: " + investmentReturn);

            return investmentReturn;
        } else {
            System.out.println("Investment is not a loss.");

            return 0;
        }
    }

    private void sendEmail(Investment investment, String message, String trigger) {
        PensionUser user = investment.getUser();
        if (user == null || user.getEmail() == null) {
            return;
        }

        String toEmail = user.getEmail();
        String subject = "Trigger Hit";

        emailSendDoc.sendEmailWithTrigger(toEmail, subject, investment, message, trigger);
    }

    @Override
    public List<Investment> getInvestments(Long userId) {
        return investmentRepository.findByUserId(userId);
    }

}
