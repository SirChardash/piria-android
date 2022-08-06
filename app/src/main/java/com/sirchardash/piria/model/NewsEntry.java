package com.sirchardash.piria.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewsEntry {

    private final String title;
    private final String description;
    private final String imageUrl;
    private final String articleUrl;

    public NewsEntry(@JsonProperty("title") String title,
                     @JsonProperty("description") String description,
                     @JsonProperty("imageUrl") String imageUrl,
                     @JsonProperty("articleUrl") String articleUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.articleUrl = articleUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    @Override
    public String toString() {
        return "NewsEntry{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", articleUrl='" + articleUrl + '\'' +
                '}';
    }

}
