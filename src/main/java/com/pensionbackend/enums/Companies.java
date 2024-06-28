package com.pensionbackend.enums;

import java.util.Arrays;
import java.util.List;

public enum Companies {
    GLOBAL_TECH_CORP("GlobalTech Corporation", Arrays.asList(InvestmentType.STOCKS, InvestmentType.COMMODITIES)),
    PRIME_BUILDERS("PrimeBuilders Ltd.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.CRYPTO)),
    SECURE_BANK("SecureBank Inc.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.FOREX)),
    HEALTHCARE_INNOVATIONS("HealthCare Innovations Ltd.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.CRYPTO)),
    STELLAR_ENERGY("StellarEnergy Solutions", Arrays.asList(InvestmentType.STOCKS, InvestmentType.COMMODITIES)),
    SAFE_DRIVE_MOTORS("SafeDrive Motors", Arrays.asList(InvestmentType.STOCKS, InvestmentType.FOREX)),
    VISIONARY_VENTURES("Visionary Ventures Ltd.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.CRYPTO)),
    CREATIVE_CONCEPTS("Creative Concepts Inc.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.COMMODITIES)),
    TECHNO_GROUP("Techno Group Ltd.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.FOREX)),
    BRIGHT_FUTURES("Bright Futures Inc.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.COMMODITIES)),
    GLOBAL_VISION("Global Vision Corporation", Arrays.asList(InvestmentType.STOCKS, InvestmentType.CRYPTO)),
    TECH_INNOVATORS("Tech Innovators Inc.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.FOREX)),
    FRONTIER_ENTERPRISES("Frontier Enterprises Ltd.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.COMMODITIES)),
    INFINITE_INNOVATION("Infinite Innovation Inc.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.CRYPTO)),
    STRATEGIC_SOLUTIONS("Strategic Solutions Ltd.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.COMMODITIES)),
    ADVANCE_TECHNOLOGIES("Advance Technologies Inc.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.FOREX)),
    VISION_TECH("Vision Tech Solutions", Arrays.asList(InvestmentType.STOCKS, InvestmentType.CRYPTO)),
    BOLD_BRAINS("Bold Brains Ltd.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.COMMODITIES)),
    DIGITAL_EVOLUTION("Digital Evolution Inc.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.FOREX)),
    NOVUS_CORP("Novus Corporation", Arrays.asList(InvestmentType.STOCKS, InvestmentType.COMMODITIES)),
    TECHNICAL_TRENDS("Technical Trends Ltd.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.CRYPTO)),
    QUANTUM_SOLUTIONS("Quantum Solutions Inc.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.COMMODITIES)),
    INNOVATION_HUB("Innovation Hub Ltd.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.CRYPTO)),
    FUTURE_TECH("Future Tech Inc.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.FOREX)),
    INVENTIVE_SOLUTIONS("Inventive Solutions Ltd.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.COMMODITIES)),
    DATA_DRIVEN("Data-Driven Solutions Inc.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.CRYPTO)),
    GREEN_TECH("GreenTech Innovations Ltd.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.COMMODITIES)),
    TECH_GURUS("Tech Gurus Inc.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.FOREX)),
    DATA_DYNAMICS("Data Dynamics Ltd.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.CRYPTO)),
    TECH_HORIZON("Tech Horizon Inc.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.COMMODITIES)),
    NEW_WORLD("New World Innovations Ltd.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.CRYPTO)),
    DIGITAL_HUB("Digital Hub Ltd.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.COMMODITIES)),
    TECH_STRATEGY("Tech Strategy Inc.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.FOREX)),
    FORWARD_VISION("Forward Vision Ltd.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.COMMODITIES)),
    DIGITAL_EDGE("Digital Edge Inc.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.CRYPTO)),
    TECH_PULSE("Tech Pulse Solutions", Arrays.asList(InvestmentType.STOCKS, InvestmentType.COMMODITIES)),
    FUTURE_FOCUS("Future Focus Inc.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.FOREX)),
    INNOVATIVE_MINDS("Innovative Minds Ltd.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.COMMODITIES)),
    DATA_SCIENCE("Data Science Solutions", Arrays.asList(InvestmentType.STOCKS, InvestmentType.CRYPTO)),
    TECH_PROGRESS("Tech Progress Inc.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.COMMODITIES)),
    VIRTUAL_VISION("Virtual Vision Ltd.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.FOREX)),
    DATA_TRENDS("Data Trends Inc.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.COMMODITIES)),
    TECH_UNIVERSE("Tech Universe Ltd.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.CRYPTO)),
    DIGITAL_INSIGHT("Digital Insight Solutions", Arrays.asList(InvestmentType.STOCKS, InvestmentType.COMMODITIES)),
    NEXT_TECH("Next Tech Innovations", Arrays.asList(InvestmentType.STOCKS, InvestmentType.FOREX)),
    TECH_ADVANCE("Tech Advance Inc.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.COMMODITIES)),
    VIRTUAL_REALITY("Virtual Reality Solutions", Arrays.asList(InvestmentType.STOCKS, InvestmentType.CRYPTO)),
    DATA_STRATEGY("Data Strategy Inc.", Arrays.asList(InvestmentType.STOCKS, InvestmentType.COMMODITIES));

    private final String companyName;
    private final List<InvestmentType> supportedInvestmentTypes;

    Companies(String companyName, List<InvestmentType> supportedInvestmentTypes) {
        this.companyName = companyName;
        this.supportedInvestmentTypes = supportedInvestmentTypes;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<InvestmentType> getSupportedInvestmentTypes() {
        return supportedInvestmentTypes;
    }
}
