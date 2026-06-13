package com.nhs.automation.framework.models;

public class ResultRule {

    private String keyword;
    private String employer;
    private String jobReference;
    private String payRange;

    private Double maxDistance;
    private Integer maxDays = 30;

    public String getKeyword() {
        return keyword;
    }

    public ResultRule setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public String getEmployer() {
        return employer;
    }

    public ResultRule setEmployer(String employer) {
        this.employer = employer;
        return this;
    }

    public String getJobReference() {
        return jobReference;
    }

    public ResultRule setJobReference(String jobReference) {
        this.jobReference = jobReference;
        return this;
    }

    public String getPayRange() {
        return payRange;
    }

    public ResultRule setPayRange(String payRange) {
        this.payRange = payRange;
        return this;
    }

    public Double getMaxDistance() {
        return maxDistance;
    }

    public ResultRule setMaxDistance(Double maxDistance) {
        this.maxDistance = maxDistance;
        return this;
    }

    public Integer getMaxDays() {
        return maxDays;
    }

    public ResultRule setMaxDays(Integer maxDays) {
        this.maxDays = maxDays;
        return this;
    }

    @Override
    public String toString() {
        return "ResultRule{" +
                "keyword='" + keyword + '\'' +
                ", employer='" + employer + '\'' +
                ", jobReference='" + jobReference + '\'' +
                ", payRange='" + payRange + '\'' +
                ", maxDistance=" + maxDistance +
                ", maxDays=" + maxDays +
                '}';
    }
}