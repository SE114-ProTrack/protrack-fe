package com.example.protrack.model;

import java.io.Serializable;

public class Comment implements Serializable {
    private String name;
    private String time;
    private String content;

    public Comment(String name, String time, String content) {
        this.name = name;
        this.time = time;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }
}
