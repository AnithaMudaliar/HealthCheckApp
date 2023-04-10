package com.thapovan.healthcheckapp.helper;

import com.thapovan.healthcheckapp.model.HeathDataResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestClient {

    String BASE_URL = "https://dev.smef.online/api/otp-mgmt/";

    @GET("health-check")
    Call<HeathDataResponse> getHealthDataList();

}
