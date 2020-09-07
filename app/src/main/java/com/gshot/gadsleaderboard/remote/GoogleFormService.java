package com.gshot.gadsleaderboard.remote;

import retrofit2.Retrofit;

public class GoogleFormService {

    private static final String BASE_URL = "https://docs.google.com/forms/d/e/";

    private static Retrofit retrofit;

    public static Retrofit buildService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit;
    }
}
