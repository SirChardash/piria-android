package com.sirchardash.piria.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tour {

    private final Integer id;
    private final Integer museumId;
    private final String title;
    private final String description;
    private final double ticketPrice;
    private final String startTime;
    private final String endTime;

    public Tour(@JsonProperty("id") Integer id,
                @JsonProperty("museumId") Integer museumId,
                @JsonProperty("title") String title,
                @JsonProperty("description") String description,
                @JsonProperty("ticketPrice") double ticketPrice,
                @JsonProperty("startTime") String startTime,
                @JsonProperty("endTime") String endTime) {
        this.id = id;
        this.museumId = museumId;
        this.title = title;
        this.description = description;
        this.ticketPrice = ticketPrice;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMuseumId() {
        return museumId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", museumId=" + museumId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }

}
