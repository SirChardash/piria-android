package com.sirchardash.piria.model;

import java.util.List;

public class Museums {

    private final List<Museum> museums;

    public Museums(List<Museum> museums) {
        this.museums = museums;
    }

    public List<Museum> getMuseums() {
        return museums;
    }

}
