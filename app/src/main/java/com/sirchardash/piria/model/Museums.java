package com.sirchardash.piria.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Museums {

    private final List<Museum> museums;

    public Museums(@JsonProperty("museums") List<Museum> museums) {
        this.museums = museums;
    }

    public List<Museum> getMuseums() {
        return museums;
    }

    @Override
    public String toString() {
        return "Museums{" +
                "museums=" + museums +
                '}';
    }
}
