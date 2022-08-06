package com.sirchardash.piria.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Museum {

    private final int id;
    private final int masterId;
    private final String language;
    private final String name;
    private final String address;
    private final String city;
    private final String country;
    private final String countryCode;
    private final String phoneNumber;
    private final String museumType;
    private final String googleLocation;

    public Museum(@JsonProperty("id") int id,
                  @JsonProperty("masterId") int masterId,
                  @JsonProperty("language") String language,
                  @JsonProperty("name") String name,
                  @JsonProperty("address") String address,
                  @JsonProperty("city") String city,
                  @JsonProperty("country") String country,
                  @JsonProperty("countryCode") String countryCode,
                  @JsonProperty("phoneNumber") String phoneNumber,
                  @JsonProperty("museumType") String museumType,
                  @JsonProperty("googleLocation") String googleLocation) {
        this.id = id;
        this.masterId = masterId;
        this.language = language;
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.countryCode = countryCode;
        this.phoneNumber = phoneNumber;
        this.museumType = museumType;
        this.googleLocation = googleLocation;
    }

    public int getId() {
        return id;
    }

    public int getMasterId() {
        return masterId;
    }

    public String getLanguage() {
        return language;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMuseumType() {
        return museumType;
    }

    public String getGoogleLocation() {
        return googleLocation;
    }

    @Override
    public String toString() {
        return "Museum{" +
                "id=" + id +
                ", masterId=" + masterId +
                ", language='" + language + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", museumType='" + museumType + '\'' +
                ", googleLocation='" + googleLocation + '\'' +
                '}';
    }
}
