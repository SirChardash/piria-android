package com.sirchardash.piria.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.jetbrains.annotations.NotNull;

public class TourContentEntry {

    private final String url;
    private final String type;

    public TourContentEntry(@JsonProperty("url") String url,
                            @JsonProperty("type") String type) {
        this.url = url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    @Override
    @NotNull
    public String toString() {
        return "TourContentEntry{" +
                "url='" + url + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
