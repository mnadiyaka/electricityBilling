package com.billing.webapp.repository;

import com.billing.webapp.model.entity.Address;
import com.billing.webapp.model.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends MongoRepository<Address, String> {
}
