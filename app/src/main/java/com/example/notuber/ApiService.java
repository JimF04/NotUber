package com.example.notuber;

import com.example.notuber.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("user/register")
    Call<Void> registerUser(@Body User user);
}
