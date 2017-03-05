package com.partymaker.mvc.admin.confirmation.opt.model;

/**
 * Created by anton on 15/12/16.
 */
public class Secret {

    private String secret;

    public Secret() {
    }

    public Secret(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Secret secret1 = (Secret) o;

        return secret != null ? secret.equals(secret1.secret) : secret1.secret == null;
    }

    @Override
    public int hashCode() {
        return secret != null ? secret.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Secret{");
        sb.append("secret='").append(secret).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
