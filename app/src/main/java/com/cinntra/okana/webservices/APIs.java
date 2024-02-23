package com.cinntra.okana.webservices;

import com.cinntra.okana.model.LogIn;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface APIs {
    @GET("marvel")
    Call<String> Testing();

    @POST("/b1s/v1/loging/")
    Call<String> testLogin(@Body LogIn body);

}
