package com.mashedtomatoes.shell.deserializer;

public class MediaCrewMember {
  private String job;
  private String department;
  private Integer talentId;

  public MediaCrewMember() {
  }

  public String getJob() {
    return job;
  }

  public String getDepartment() {
    return department;
  }

  public Integer getTalentId() {
    return talentId;
  }

  public void setJob(String job) {
    this.job = job;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public void setTalentId(Integer talenId) {
    this.talentId = talenId;
  }
}
