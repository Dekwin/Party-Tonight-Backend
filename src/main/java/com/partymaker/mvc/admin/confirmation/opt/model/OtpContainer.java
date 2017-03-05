package com.partymaker.mvc.admin.confirmation.opt.model;

/**
 * Created by anton on 15/12/16.
 */
public class OtpContainer {

    private String otp;

    public OtpContainer(String otp) {
        this.otp = otp;
    }

    public OtpContainer() {
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OtpContainer{");
        sb.append("otp='").append(otp).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
