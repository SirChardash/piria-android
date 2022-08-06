package com.sirchardash.piria.model;

public class TrackingLog {

    private final long timestamp;
    private final String userId;
    private final String category;
    private final String subcategory;
    private final String label;
    private final String value;

    public TrackingLog(long timestamp,
                       String userId,
                       String category,
                       String subcategory,
                       String label,
                       String value) {
        this.timestamp = timestamp;
        this.userId = userId;
        this.category = category;
        this.subcategory = subcategory;
        this.label = label;
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

}
