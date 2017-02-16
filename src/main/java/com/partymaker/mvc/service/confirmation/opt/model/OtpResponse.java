package com.partymaker.mvc.service.confirmation.opt.model;

/**
 * Created by anton on 25.01.17.
 */
public class OtpResponse {

    private String response;

    public OtpResponse() {
    }

    public OtpResponse(String response) {
        this.response = response;
    }


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OtpResponse{");
        sb.append("response='").append(response).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
