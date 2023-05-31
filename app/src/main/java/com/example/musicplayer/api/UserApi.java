package com.example.musicplayer.api;

import com.example.musicplayer.domain.SongMessage;
import com.example.musicplayer.domain.SongUpdate;
import com.example.musicplayer.domain.User;
import com.example.musicplayer.domain.UserMessage;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {
    @FormUrlEncoded
    @POST("user/login")
    Call<UserMessage> login(@Field("phone") String phone, @Field("password") String password);


    @POST("user/register")
    Call<UserMessage> register(@Body User user);
    @GET(value = "/getuserbyid/{id}")
    Call <UserMessage> getuserbyid(@Path("id") long id);

    @POST(value = "user/all")
    Call<UserMessage> getAllUser();

    @PUT("user/update/{id}")
    Call<UserMessage> update(@Path("id") long id, @Body User user);

    @FormUrlEncoded
    @POST("user/delete")
    Call<UserMessage> deleteUser(@Field("id") Long id);
}
