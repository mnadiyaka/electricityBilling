package com.billing.webapp.repository;

import com.billing.webapp.model.entity.Discount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DiscountRepository extends MongoRepository<Discount, String> {
}
