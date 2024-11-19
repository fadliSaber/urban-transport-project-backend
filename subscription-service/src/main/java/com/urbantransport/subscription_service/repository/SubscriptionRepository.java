package com.urbantransport.subscription_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import com.urbantransport.subscription_service.entity.Subscription;

import java.util.UUID;
@Repository

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
}
