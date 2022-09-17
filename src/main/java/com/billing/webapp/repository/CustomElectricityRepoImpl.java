package com.billing.webapp.repository;

import com.billing.webapp.model.dto.ElectricityDto;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;

import static com.mongodb.client.model.Filters.gt;

public class CustomElectricityRepoImpl implements CustomElectricityRepo {
    @Override
    public List<ElectricityDto> findNotPaidElectricity() {
        final String uri = "http://localhost:8081/db/mydb";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("mydb");
            MongoCollection<Document> collection = database.getCollection("electricity");

            Document doc = collection.find(gt("toPay", "0"))
                    .first();
            if (doc == null) {
                System.out.println("No results found.");
            } else {
                System.out.println(doc.toJson());
            }
        }
        return null;
    }
}
