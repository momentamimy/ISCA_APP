package com.example.isca_app.service;

import com.example.isca_app.model.Retrofit.PhotoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by K. A. ANUSHKA MADUSANKA on 7/9/2018.
 */
public interface PhotoDataService {



    @GET("rest/?")
    Call<PhotoResponse> getPhotos(
            @Query("method") String method,
            @Query("api_key") String api_key,
            @Query("format") String format,
            @Query("tags") String tags,
            @Query("nojsoncallback") String nojsoncallback);


    @GET("rest/?")
    Call<PhotoResponse> getPhotosWithPage(
            @Query("method") String method,
            @Query("api_key") String api_key,
            @Query("format") String format,
            @Query("tags") String tags,
            @Query("nojsoncallback") String nojsoncallback,
            @Query("page") long page);

}
