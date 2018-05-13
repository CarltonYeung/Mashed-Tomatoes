package com.mashedtomatoes.http;

import javax.validation.constraints.NotNull;

public class ReviewReportRequest {

    private String reason;

    public ReviewReportRequest(){

    }

    @NotNull
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
