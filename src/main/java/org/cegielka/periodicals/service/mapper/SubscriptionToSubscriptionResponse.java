package org.cegielka.periodicals.service.mapper;

import lombok.AllArgsConstructor;
import org.cegielka.periodicals.dto.SubscriptionResponse;
import org.cegielka.periodicals.entity.Subscription;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class SubscriptionToSubscriptionResponse {

    public List<SubscriptionResponse> map(List<Subscription> subscription) {
        List<SubscriptionResponse> listSubscriptionResponse = new ArrayList<>();
        for (Subscription element : subscription) {
            SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
            subscriptionResponse.setId(element.getId());
            subscriptionResponse.setUser(element.getUser());
            subscriptionResponse.setPublication(element.getPublication());
            subscriptionResponse.setDate(element.getDate().toString().split("T")[0]);
            listSubscriptionResponse.add(subscriptionResponse);
        }
        return listSubscriptionResponse;
    }
}
