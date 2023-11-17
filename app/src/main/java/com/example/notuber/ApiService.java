package com.example.notuber;

import com.example.notuber.Model.*;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("driver/register")
    Call<Void> registerDriver(@Body Driver driver);

    @POST("employee/register")
    Call<Void> registerEmployee(@Body Employee employee);

    @POST("driver/login")
    Call<Void> logInDriver(@Body LogIn logIn);

    @POST("employee/login")
    Call<Void> logInEmployee(@Body LogIn logIn);

    @POST("addfriend")
    Call<Void> addFriend(@Body FriendRequest friendRequest);

    @POST("friends")
    Call<String> getFriends(@Body String email);

    @GET("graph")
    Call<NodesResponse> getNodes();

    @GET("employee/location/{email}")
    Call<ApiResponse> getEmployeeLocation(@Path("email") String email);


}
