package org.cegielka.periodicals.dto;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Data
public class PublicationRegistrationRequest {
    private String title;
    private Long price;
    private String topic;

    public PublicationRegistrationRequest(){}

    public PublicationRegistrationRequest(String title, Long price, String topic) {
        this.title = title;
        this.price = price;
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}