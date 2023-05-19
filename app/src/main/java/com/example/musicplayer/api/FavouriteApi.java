package com.example.musicplayer.api;

import com.example.musicplayer.domain.FavouriteMessage;
import com.example.musicplayer.domain.User;
import com.example.musicplayer.domain.UserMessage;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FavouriteApi {

    @FormUrlEncoded
    @POST("favourite/find")
    Call<FavouriteMessage> findFavorite(@Field("songId") Long songId, @Field("userId") Long userId);

    @FormUrlEncoded
    @POST("favourite/listByUser")
    Call<FavouriteMessage> listByUser(@Field("userId") Long userId);


    @FormUrlEncoded
    @POST("favourite/add")
    Call<FavouriteMessage> addFavourite(@Field("songId") Long songId, @Field("userId") Long userId);

    @FormUrlEncoded
    @POST("favourite/delete")
    Call<FavouriteMessage> deleteFavorite(@Field("songId") Long songId, @Field("userId") Long userId);

}
