package com.partymaker.mvc.model.enums;

/**
 * Created by igorkasyanenko on 07.04.17.
 */
public enum Roles {
    ROLE_ADMIN ("ROLE_ADMIN"),
    ROLE_PARTY_MAKER ("ROLE_PARTY_MAKER"),
    ROLE_STREET_DANCER ("ROLE_STREET_DANCER");


    private final String name;

    private Roles(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
