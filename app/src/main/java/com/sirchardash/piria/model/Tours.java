package com.sirchardash.piria.model;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Tours {

    private final List<Tour> tours;

    public Tours(@JsonProperty("tours") List<Tour> tours) {
        this.tours = tours;
    }

    public List<Tour> getTours() {
        return tours;
    }

    @NonNull
    @Override
    public String toString() {
        return "Tours{" +
                "tours=" + tours +
                '}';
    }

}
