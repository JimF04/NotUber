package com.example.notuber;

import com.example.notuber.Model.Employee;
import com.example.notuber.Model.LogIn;
import com.example.notuber.Model.Driver;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("driver/register")
    Call<Void> registerDriver(@Body Driver driver);

    @POST("employee/register")
    Call<Void> registerEmployee(@Body Employee employee);

    @POST("driver/login")
    Call<Void> logInDriver(@Body LogIn logIn);

    @POST("employee/login")
    Call<Void> logInEmployee(@Body LogIn logIn);
}
