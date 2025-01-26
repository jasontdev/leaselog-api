package dev.jasont.leaselog.entity;

import java.util.UUID;

public class User {
    private UUID id;
    private String oidcId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOidcId() {
        return oidcId;
    }

    public void setOidcId(String oidcId) {
        this.oidcId = oidcId;
    }
}
