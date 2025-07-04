package com.example.protrack.realtime;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.LifecycleEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ua.naiksoftware.stomp.dto.StompHeader;

public class StompManager {
    private static StompClient mStompClient;
    private static Disposable mLifecycleDisposable;

    public static void connect(String wsUrl, String token, StompConnectListener listener) {
        if (mStompClient != null && mStompClient.isConnected()) return;

        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, wsUrl);
        List<StompHeader> headers = new ArrayList<>();
        headers.add(new StompHeader("Authorization", "Bearer " + token));
        mStompClient.connect(headers);

        mLifecycleDisposable = mStompClient.lifecycle()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lifecycleEvent -> {
                    switch (lifecycleEvent.getType()) {
                        case OPENED:
                            listener.onConnect();
                            break;
                        case ERROR:
                            listener.onError(lifecycleEvent.getException());
                            break;
                        case CLOSED:
                            listener.onClose();
                            break;
                    }
                });
    }

    public static void subscribe(String topic, StompMessageListener listener) {
        if (mStompClient == null) return;
        mStompClient.topic(topic)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(msg -> listener.onMessage(msg.getPayload()));
    }

    public static void disconnect() {
        if (mLifecycleDisposable != null && !mLifecycleDisposable.isDisposed()) {
            mLifecycleDisposable.dispose();
        }
        if (mStompClient != null) {
            mStompClient.disconnect();
        }
    }

    public interface StompConnectListener {
        void onConnect();
        void onError(Throwable e);
        void onClose();
    }
    public interface StompMessageListener {
        void onMessage(String payload);
    }
}
