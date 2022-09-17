package com.billing.webapp.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Data
@Accessors(chain = true)
public class ElectricityHolder {

    private static Map<String, CompletableFuture<Electricity>> data = new HashMap<>();

    public static CompletableFuture<Electricity> getData(String key) {
        return data.get(key);
    }

    public static void setData(String key, CompletableFuture<Electricity> newData) {
        data.put(key, newData);
    }


}