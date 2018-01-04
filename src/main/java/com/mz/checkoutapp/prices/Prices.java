package com.mz.checkoutapp.prices;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Prices {
    private static ClassLoader classloader = Thread.currentThread().getContextClassLoader();

    public static Map<String, HashMap<String,Object>> getAllPrices() throws Exception {
        Map<String, HashMap<String,Object>> result =
                new ObjectMapper().readValue(classloader.getResourceAsStream("prices.json"), Map.class);
        return result;
    }
    public static Map<String,Object> getOne(String name) throws Exception{
        TreeMap<String, HashMap<String,Object>> treeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        treeMap.putAll(getAllPrices());
        return treeMap.get(name);
    }
}
