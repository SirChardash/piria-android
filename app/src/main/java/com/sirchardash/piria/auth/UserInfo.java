package com.sirchardash.piria.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {

    private final String userId;
    private final String fullName;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String email;

    public UserInfo(@JsonProperty("sub") String userId,
                    @JsonProperty("name") String fullName,
                    @JsonProperty("preferred_username") String username,
                    @JsonProperty("given_name") String firstName,
                    @JsonProperty("family_name") String lastName,
                    @JsonProperty("email") String email) {
        this.userId = userId;
        this.fullName = fullName;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

}
