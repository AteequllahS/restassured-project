package com.cydeo.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
        {
            "job_id": "AD_ASST",
            "job_title": "Administration Assistant",
            "min_salary": 3000,
            "max_salary": 6000,

         {
 */

//as "link" is part of jason field, we need to instruct jackson data bind or else it will throw exception

//to ignore what is not included in pojo class, but exist in json body
// -> and has to be placed on top of the class
@JsonIgnoreProperties(ignoreUnknown = true)

public class Job {

    @JsonProperty("job_id")
    private String jobId;

    @JsonProperty("job_title")
    private String jobTitle;

    @JsonProperty("min_salary")
    private int minSalary;

    @JsonProperty("max_salary")
    private int maxSalary;

    // the class itself make a default constructor. as long as we dont have a constructor with parameter, so we
    // dont need to declare/make it obvious.

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(int minSalary) {
        this.minSalary = minSalary;
    }

    public int getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(int maxSalary) {
        this.maxSalary = maxSalary;
    }

    @Override
    public String toString() {
        return "Jobs_HR{" +
                "jobId='" + jobId + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", minSalary=" + minSalary +
                ", maxSalary=" + maxSalary +
                '}';
    }
}
