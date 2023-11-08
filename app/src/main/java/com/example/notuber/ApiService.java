package com.example.notuber;

import com.example.notuber.Model.LogIn;
import com.example.notuber.Model.LogInDriver;
import com.example.notuber.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("user/register")
    Call<Void> registerUser(@Body User user);

    @POST("/user/conductor/login")
    Call<Void> logInDriver(@Body LogInDriver logInDriver);

    @POST("user/employee/login")
    Call<Void> logInEmployee(@Body LogIn logIn);
}
