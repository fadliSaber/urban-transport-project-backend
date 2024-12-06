package com.urbantransport.route_schedule_service.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.urbantransport.route_schedule_service.entity.Stop;

@Repository
public interface StopRepository extends CassandraRepository<Stop, UUID> {
}
