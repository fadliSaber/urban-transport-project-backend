package com.urbantransport.subscription_service.service;

import org.springframework.stereotype.Service;
import com.urbantransport.subscription_service.entity.Subscription;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public interface SubscriptionService {
     void subscribe(Subscription newsubscription);
     List<Subscription> getAllSubscriptions();
     void deleteSubscription(UUID id);
     Subscription updateSubscription(
             UUID id,
             Optional<Integer> idUser,
             Optional<String> plan,
             Optional<Date> startDate,
             Optional<Date> endDate,
             Optional<String> state,
             Optional<Float> price
     );

}
