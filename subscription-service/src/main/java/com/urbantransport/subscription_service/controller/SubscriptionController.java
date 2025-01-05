package com.urbantransport.subscription_service.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@Slf4j
public class SubscriptionController {

    private final SubscriptionService service;

    /**
     * Adds a new subscription.
     *
     * @param sub the subscription to be added
     * @return the added subscription
     */
    @PostMapping("/subscribe")
    public ResponseEntity<Subscription> addSubscription(@RequestBody Subscription sub) {
        try {
            Subscription createdSubscription = service.subscribe(sub);
            log.info("Subscription added successfully: {}", createdSubscription);
            return new ResponseEntity<>(createdSubscription, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error adding subscription: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves all subscriptions.
     *
     * @return a list of subscriptions
     */
    @GetMapping("/all")
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        List<Subscription> subscriptions = service.getAllSubscriptions();
        if (subscriptions.isEmpty()) {
            log.warn("No subscriptions found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info("Retrieved all subscriptions.");
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    /**
     * Deletes a subscription by ID.
     *
     * @param id the subscription ID
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable UUID id) {
        try {
            service.deleteSubscription(id);
            log.info("Subscription with ID {} deleted successfully.", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting subscription with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates an existing subscription.
     *
     * @param id        the subscription ID to update
     * @param idUser    the user ID (optional)
     * @param plan      the subscription plan (optional)
     * @param startDate the start date (optional)
     * @param endDate   the end date (optional)
     * @param state     the subscription state (optional)
     * @param price     the price (optional)
     * @return the updated subscription
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Subscription> updateSubscription(
            @PathVariable UUID id,
            @RequestParam Optional<Integer> idUser,
            @RequestParam Optional<String> plan,
            @RequestParam Optional<Date> startDate,
            @RequestParam Optional<Date> endDate,
            @RequestParam Optional<String> state,
            @RequestParam Optional<Float> price) {

        try {
            Subscription updatedSubscription = service.updateSubscription(id, idUser, plan, startDate, endDate, state, price);
            if (updatedSubscription == null) {
                log.warn("Subscription with ID {} not found for update.", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            log.info("Subscription with ID {} updated successfully.", id);
            return new ResponseEntity<>(updatedSubscription, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating subscription with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
