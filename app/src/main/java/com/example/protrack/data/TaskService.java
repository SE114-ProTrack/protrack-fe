package com.example.protrack.data;

import com.example.protrack.model.response.TaskPageResponse;
import com.example.protrack.model.response.TaskResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TaskService {
    // Lấy task theo projectId (có paging)
    @GET("tasks/project/{projectId}")
    Call<TaskPageResponse> getTasksByProject(
            @Header("Authorization") String token,
            @Path("projectId") String projectId,
            @Query("page") int page,
            @Query("size") int size
    );

    // Lấy task của user hiện tại
    @GET("tasks/user")
    Call<List<TaskResponse>> getTasksByUser(@Header("Authorization") String token);

    @GET("tasks/{id}")
    Call<TaskResponse> getTaskDetail(
            @Header("Authorization") String token,
            @Path("id") String taskId
    );
}
