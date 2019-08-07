package com.example.isca_app.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by K. A. ANUSHKA MADUSANKA on 7/9/2018.
 */
public class RetrofitInstance {

    private static Retrofit retrofit = null;
    private static String BASE_URL="https://api.flickr.com/services/";

    public static PhotoDataService getService(){


        if(retrofit==null){

               retrofit=new Retrofit
                       .Builder()
                       .baseUrl(BASE_URL)
                       .addConverterFactory(GsonConverterFactory.create())
                       .build();

        }

        return retrofit.create(PhotoDataService.class);

    }

}
