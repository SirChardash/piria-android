package com.sirchardash.piria.model;

import lombok.Data;
import lombok.Getter;

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

    public Museum(int id,
                  int masterId,
                  String language,
                  String name,
                  String address,
                  String city,
                  String country,
                  String countryCode,
                  String phoneNumber,
                  String museumType,
                  String googleLocation) {
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
