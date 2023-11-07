package com.example.notuber.Model;

import com.google.gson.annotations.SerializedName;

public class Task {
    @SerializedName("id")
    private long id;
    @SerializedName("companyID")
    private String companyID;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("location")
    private String location;

    @SerializedName("role")
    private String role;
}
