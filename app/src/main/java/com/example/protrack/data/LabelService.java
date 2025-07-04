package com.example.protrack.data;

import com.example.protrack.model.response.LabelResponse;

import retrofit2.Call;
import retrofit2.http.*;

public interface LabelService {
    @GET("labels/{id}")
    Call<LabelResponse> getLabelById(@Header("Authorization") String token, @Path("id") String labelId);
}