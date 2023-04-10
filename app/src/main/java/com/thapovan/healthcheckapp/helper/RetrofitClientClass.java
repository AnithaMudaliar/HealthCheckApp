package com.thapovan.healthcheckapp.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientClass {
    private static RetrofitClientClass instance = null;
    private RestClient restClient;

    private RetrofitClientClass() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                           .baseUrl(RestClient.BASE_URL)
                           .addConverterFactory(GsonConverterFactory.create(gson))
                           .build();
        restClient = retrofit.create(RestClient.class);
    }

    public static synchronized RetrofitClientClass getInstance() {
        if (instance == null) {
            instance = new RetrofitClientClass();
        }
        return instance;
    }

    public RestClient getRestClient() {
        return restClient;
    }
}
