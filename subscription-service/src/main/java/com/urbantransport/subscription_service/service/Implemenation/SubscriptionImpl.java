package com.urbantransport.subscription_service.service.Implemenation;

import com.urbantransport.subscription_service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.urbantransport.subscription_service.entity.Subscription;
import com.urbantransport.subscription_service.repository.SubscriptionRepository;
import com.urbantransport.subscription_service.service.SubscriptionService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class SubscriptionImpl implements SubscriptionService {
    private final SubscriptionRepository repository;
    @Autowired
    public SubscriptionImpl(SubscriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void subscribe(Subscription newsubscription) {
        repository.save(newsubscription);

    }

    @Override
    public List<Subscription> getAllSubscriptions() {

        List<Subscription> subscriptions = repository.findAll();
        System.out.println("Fetched Subscriptions: " + subscriptions);
        return subscriptions;
    }

    @Override
    public void deleteSubscription(UUID id) {
        repository.deleteById(id);

    }

    @Override
    public Subscription updateSubscription(UUID id, Optional<Integer> idUser, Optional<String> plan, Optional<Date> startDate, Optional<Date> endDate, Optional<String> state, Optional<Float> price) {
        Subscription existingSubscription= repository.findById(id).orElseThrow(() -> new NotFoundException("Subscription not found"));;
        idUser.ifPresent(existingSubscription::setId_user);
        plan.ifPresent(existingSubscription::setPlan);
        startDate.ifPresent(existingSubscription::setStart_date);
        endDate.ifPresent(existingSubscription::setEnd_date);
        state.ifPresent(existingSubscription::setState);
        price.ifPresent(existingSubscription::setPrice);
        return existingSubscription;
    }


}
