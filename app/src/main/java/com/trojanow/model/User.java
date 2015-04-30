package com.trojanow.model;

/**
 * Created by pabloivan57 on 3/27/15.
 */
public class User {
    private String fullname;
    private String password;
    private String email;
    private Integer id;
    private Boolean active;
    private String  authentication_token;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer userId) {
        this.id = userId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAuthenticationToken() {
        return this.authentication_token;
    }
}
