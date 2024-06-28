package com.pensionbackend.enums;

public enum DefinedBenifitCompanies {
    GLOBAL_TECH_CORP("GlobalTech Corporation"),
    PRIME_BUILDERS("PrimeBuilders Ltd."),
    SECURE_BANK("SecureBank Inc."),
    HEALTHCARE_INNOVATIONS("HealthCare Innovations Ltd."),
    STELLAR_ENERGY("StellarEnergy Solutions"),
    SAFE_DRIVE_MOTORS("SafeDrive Motors");

    private final String companyName;

    DefinedBenifitCompanies(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }
}
