package com.pensionbackend.enums;

public enum ComplianceTrigger {
    SPEND_10000_1_MONTH(Severity.MODERATE),
    LOSS_1000_1_DAY(Severity.MODERATE),
    LOSS_10000_30_DAYS(Severity.MODERATE),
    LOSS_100000_365_DAYS(Severity.MODERATE),
    SPEND_5000_5_DAY(Severity.HIGH),
    LOSS_5000_1_DAY(Severity.HIGH),
    SPEND_10000_1_DAY(Severity.HIGH);

    private final Severity severity;

    ComplianceTrigger(Severity severity) {
        this.severity = severity;
    }

    public Severity getSeverity() {
        return severity;
    }

    public enum Severity {
        HIGH,
        MODERATE
    }
}
