package com.example.protrack.data;

import com.example.protrack.model.response.PageProjectResponse;
import com.example.protrack.model.response.ProjectResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProjectService {

    @GET("projects/user")
    Call<PageProjectResponse> getMyProjects(
            @Query("page") int page,
            @Query("size") int size,
            @Header("Authorization") String token
    );

    @GET("projects/user/get3")
    Call<List<ProjectResponse>> get3Projects(
            @Header("Authorization") String token
    );

    @GET("projects/{id}")
    Call<ProjectResponse> getProjectDetail(
            @Header("Authorization") String token,
            @Path("id") String projectId
    );

    @Multipart
    @POST("projects")
    Call<ProjectResponse> createProject(
            @Header("Authorization") String token,
            @Part("project") RequestBody projectJson,
            @Part MultipartBody.Part file // file optional, có thể truyền null
    );
}
