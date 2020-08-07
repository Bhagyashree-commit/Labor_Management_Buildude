package com.example.labourmangement.model;

public class AppliedJobsModel {
    private String job_id;
    private String job_title ;
    private String job_details;
    private String job_wages;
    private String job_area;
    private String applied_by;
    private String applied_date;

    public AppliedJobsModel(String job_id,String job_title, String job_details, String job_wages, String job_area, String applied_by, String applied_date) {
        this.job_id = this.job_id;
        this.job_title = this.job_title;
        this.job_details = this.job_details;
        this.job_wages = this.job_wages;
        this.job_area = this.job_area;
        this.applied_by = this.applied_by;
        this.applied_date =this.applied_date;
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

    public String getApplied_by() {
        return applied_by;
    }

    public void setApplied_by(String applied_by) {
        this.applied_by = applied_by;
    }

    public String getApplied_date() {
        return applied_date;
    }

    public void setApplied_date(String applied_by) {
        this.applied_date = applied_date;
    }

}
