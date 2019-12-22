package com.samuelbernard.latihan_retrofit.model;

import com.google.gson.annotations.SerializedName;

public class Student {
    @SerializedName("password")
    private String password;
    @SerializedName("email")
    private String email;
    @SerializedName("username")
    private String username;
    @SerializedName("id")
    private String id;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}