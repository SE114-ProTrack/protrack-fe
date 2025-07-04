package com.example.protrack.data;

import com.example.protrack.model.response.TaskAttachmentResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface TaskAttachmentService {
    @GET("files/tasks/{taskId}/attachments")
    Call<List<TaskAttachmentResponse>> getAttachmentsByTask(
            @Header("Authorization") String token,
            @Path("taskId") String taskId
    );
}
