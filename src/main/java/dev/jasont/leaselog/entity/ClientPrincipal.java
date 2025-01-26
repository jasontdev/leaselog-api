package dev.jasont.leaselog.entity;

import java.util.ArrayList;
import java.util.List;

public class ClientPrincipal {
    private String userId;
    private List<String> userRoles = new ArrayList<>();
    private String identityProvider;
    private String userDetails;

    public ClientPrincipal() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<String> userRoles) {
        this.userRoles = userRoles;
    }

    public String getIdentityProvider() {
        return identityProvider;
    }

    public void setIdentityProvider(String identityProvider) {
        this.identityProvider = identityProvider;
    }

    public String getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(String userDetails) {
        this.userDetails = userDetails;
    }
}
