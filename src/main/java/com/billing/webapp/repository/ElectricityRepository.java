package com.billing.webapp.repository;

import com.billing.webapp.model.entity.Address;
import com.billing.webapp.model.entity.Electricity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectricityRepository extends MongoRepository<Electricity, String> {
}
