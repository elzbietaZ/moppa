package com.example.eugene.layouttest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MoppaService {
    @GET("retrieveTask")
    Call<Task> tasks(@Query("deviceId") long deviceId);

    @POST("saveResultTasks")
    Call<Task> saveResultTasks(@Body Task body);
}
