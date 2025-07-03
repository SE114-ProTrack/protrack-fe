package com.example.protrack.data;

import com.example.protrack.model.response.PersonalProductivityResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface Productivity {
    @GET("personal-productivity/my")
    Call<List<PersonalProductivityResponse>> getMyProductivity(@Header("Authorization") String token);

}
