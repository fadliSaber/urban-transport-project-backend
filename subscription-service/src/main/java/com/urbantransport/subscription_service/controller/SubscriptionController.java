package com.urbantransport.subscription_service.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.urbantransport.subscription_service.entity.Subscription;
import com.urbantransport.subscription_service.service.SubscriptionService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/subscription")
@AllArgsConstructor

public class SubscriptionController {
    private final SubscriptionService service;



    @PostMapping("/subscribe")
    Subscription addSubscription(@RequestBody Subscription sub){
        service.subscribe(sub);
        return sub;

    }
    @GetMapping("/all")
    List<Subscription> getAllSubscriptions(){
        return service.getAllSubscriptions();
    }
    @DeleteMapping("/delete/{id}")
    public void deleteSubscription(@PathVariable UUID id) {
        service.deleteSubscription(id);
    }

    @PutMapping("/update/{id}")
    public Subscription updateSubscription(
            @PathVariable UUID id,
            @RequestParam Optional<Integer> idUser,
            @RequestParam Optional<String> plan,
            @RequestParam Optional<Date> startDate,
            @RequestParam Optional<Date> endDate,
            @RequestParam Optional<String> state,
            @RequestParam Optional<Float> price) {

        return service.updateSubscription(id, idUser, plan, startDate, endDate, state, price);
    }
}

