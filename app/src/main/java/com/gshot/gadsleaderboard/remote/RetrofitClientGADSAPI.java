package com.gshot.gadsleaderboard.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientGADSAPI {

    private static final String BASE_URL = "https://gadsapi.herokuapp.com";

    private static Retrofit retrofit;

    public static Retrofit buildService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(setGson()))
                    .build();
        }
        return retrofit;
    }

    private static Gson setGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }
}
