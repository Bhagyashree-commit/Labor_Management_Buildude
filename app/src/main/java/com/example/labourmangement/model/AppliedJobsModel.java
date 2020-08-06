package com.example.labourmangement.model;

public class AppliedJobsModel {
    private String job_id;
    private String job_title ;
    private String job_details;
    private String job_wages;
    private String job_area;

    public AppliedJobsModel(String job_id,String job_title, String job_details, String job_wages, String job_area) {
        this.job_id = this.job_id;
        this.job_title = this.job_title;
        this.job_details = this.job_details;
        this.job_wages = this.job_wages;
        this.job_area = this.job_area;
    }

    public AppliedJobsModel() {

    }


    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getJob_details() {
        return job_details;
    }

    public void setJob_details(String job_details) {
        this.job_details = job_details;
    }

    public String getJob_wages() {
        return job_wages;
    }

    public void setJob_wages(String job_wages) {
        this.job_wages = job_wages;
    }

    public String getJob_area() {
        return job_area;
    }

    public void setJob_area(String job_area) {
        this.job_area = job_area;
    }

}
