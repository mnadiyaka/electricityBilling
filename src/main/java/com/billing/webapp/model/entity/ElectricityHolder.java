package com.billing.webapp.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Data
@Accessors(chain = true)
public class ElectricityHolder {

    private static Map<String, Temp> data = new HashMap<>();

    public static Temp getData(String key) {
        return data.get(key);
    }

    public static void setData(String key, Temp newData) {
        data.put(key, newData);
    }

    @Data
    public static class Temp {
        private CompletableFuture<Electricity> future;

        private State state;
    }

    public enum State {
        IN_PROGRESS, COMPLETED, NOT_IDENTIFIED
    }
}