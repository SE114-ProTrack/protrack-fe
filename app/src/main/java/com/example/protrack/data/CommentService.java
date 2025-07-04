package com.example.protrack.data;

import com.example.protrack.model.response.CommentPageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CommentService {
    @GET("comments/task/{taskId}")
    Call<CommentPageResponse> getCommentsByTask(
            @Header("Authorization") String token,
            @Path("taskId") String taskId,
            @Query("page") int page,
            @Query("size") int size
    );

}
