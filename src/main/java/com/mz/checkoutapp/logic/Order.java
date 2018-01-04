package com.mz.checkoutapp.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mz.checkoutapp.prices.Prices;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Order {
    private ObjectMapper mapper;
    private HashMap<String,Object> orderList;

    public Order() {}

    public Order(String json) throws IOException {
        super();
        mapper  = new ObjectMapper();
        orderList = mapper.readValue(json,HashMap.class);
    }
    public Map<String,String> getOrder() {
        Map<String,String> order = new LinkedHashMap<String,String>();
        try {
            order = makeOrder(orderList);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return order;
    }
    private Map<String, String> makeOrder(HashMap<String,Object> orderList) throws Exception {
        Map<String, String> order = new LinkedHashMap<String, String>();
        double totalPrice = 0;
        for (HashMap.Entry<String,Object> entry : orderList.entrySet()) {
            String name = entry.getKey();
            int count = Integer.parseInt(entry.getValue().toString());
            if(Prices.getOne(name)!=null) {
                order.put(name, Double.toString(determinePrice(count, name)));
                totalPrice = totalPrice + determinePrice(count, name);
            } else {
                order.put(name, "Product not found in catalog");
            }
        }
        order.put("Total price", Double.toString(totalPrice));
        return order;
    }
    private int getPrice(String name) throws Exception {
        return Integer.parseInt(Prices.getOne(name).get("Price").toString());
    }
    private int getDiscount(String name) throws Exception {
        return Integer.parseInt(Prices.getOne(name).get("Discount").toString());
    }
    private int getUnit(String name) throws Exception {
        return Integer.parseInt(Prices.getOne(name).get("Unit").toString());
    }
    private double determinePrice(int orderedAmount, String name) throws Exception {
        if(orderedAmount<getDiscount(name)) {
            return orderedAmount * (double)(getPrice(name)/getUnit(name));
        }
        else {
            return orderedAmount * (double)(getPrice(name)/getUnit(name)) * 0.9;
        }
    }
}
