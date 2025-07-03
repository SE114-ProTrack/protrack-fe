package com.example.protrack.data;

import com.example.protrack.model.response.PageNotificationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NotificationService {
    @GET("notifications")
    Call<PageNotificationResponse> getMyNotifications(
            @Query("page") int page,
            @Query("size") int size,
            @Header("Authorization") String token
    );
}

