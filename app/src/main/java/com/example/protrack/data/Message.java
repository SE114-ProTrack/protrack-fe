package com.example.protrack.data;

import com.example.protrack.model.request.MessageRequest;
import com.example.protrack.model.response.MessagePage;
import com.example.protrack.model.response.MessagePreviewResponse;
import com.example.protrack.model.response.MessageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Message {
    // Danh sách cuộc trò chuyện (preview)
    @GET("messages/previews")
    Call<List<MessagePreviewResponse>> getMessagePreviews(@Header("Authorization") String token);

    // Lấy tin nhắn với một user cụ thể (paging)
    @GET("messages/with/{userId}")
    Call<MessagePage> getConversationWithUser(
            @Header("Authorization") String token,
            @Path("userId") String userId,
            @Query("page") int page,
            @Query("size") int size
    );

    @POST("messages")
    Call<MessageResponse> sendMessage(
            @Header("Authorization") String token,
            @Body MessageRequest request
    );

}

