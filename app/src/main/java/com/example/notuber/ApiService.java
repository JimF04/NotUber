package com.example.notuber;

import com.example.notuber.Model.LogIn;
import com.example.notuber.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("user/register")
    Call<Void> registerUser(@Body User user);

    @POST("/user/driver")
    Call<Void> logInDriver(@Body LogIn logIn);

    @POST("user/employee")
    Call<Void> logInEmployee(@Body LogIn logIn);
}
