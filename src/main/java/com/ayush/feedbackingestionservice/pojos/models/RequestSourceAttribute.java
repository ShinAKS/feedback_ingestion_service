package com.ayush.feedbackingestionservice.pojos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestSourceAttribute {
    String deviceId;
    String client;
    String appVersion;
    String country;
    Double latitude;
    Double longitude;
}
