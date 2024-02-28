package com.ayush.feedbackingestionservice.pojos.models.client.zendesk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class ZendeskTicket {

    @JsonProperty("ticket_id")
    String id;

    @JsonProperty("ticket")
    Ticket ticket;

}
