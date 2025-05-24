package edu.wsiiz.repairshop.vehicle.domain;

import edu.wsiiz.repairshop.vehicle.domain.PolicyNumber;

import java.time.LocalDate;

public class InsurancePolicy {
    private final PolicyNumber number;
    private final InsuranceType type;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public InsurancePolicy(PolicyNumber number, InsuranceType type, LocalDate startDate, LocalDate endDate) {
        this.number = number;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public PolicyNumber getNumber() {
        return number;
    }

    public InsuranceType getType() {
        return type;
    }
}
