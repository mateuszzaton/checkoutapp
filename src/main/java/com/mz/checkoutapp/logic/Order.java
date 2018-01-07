package com.mz.checkoutapp.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mz.checkoutapp.repository.ItemRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

public class Order {

    private Map<String,String> orderList;
    private ItemRepository repository;
    private final double DISCOUNT = 0.9;
    public Order(String json, ItemRepository repository) {
        orderList = jsonToMap(json);
        this.repository = repository;
    }

    public Map<String,String> getOrder() {
        return processOrder(orderList);
    }

    private Map<String,String> jsonToMap(String json) {
        Map<String, String> order = new LinkedHashMap<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            order = mapper.readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return order;
    }

    private Map<String, String> processOrder(Map<String,String> orderList) {
        Map<String, String> order = new LinkedHashMap<>();
        BigDecimal totalPrice = new BigDecimal(0);
        if(orderList!=null) {
            for (Map.Entry<String, String> entry : orderList.entrySet()) {
                String name = entry.getKey();
                String amount = entry.getValue();
                try {
                    BigDecimal price = determinePrice(amount, name);
                    order.put(name, price + " for " + amount + " units." + printDiscount(amount,name));
                    totalPrice = totalPrice.add(price);
                } catch (Exception e) {
                    e.printStackTrace();
                    order.put(name, "Could not findByName product in the catalog.");
                }
            }
        }
        order.put("Total price", totalPrice.toString());
        return order;
    }
    private String printDiscount(String amount, String name) throws Exception {
        String result = "";
        int discountAmount = getDiscount(name);
        if(Integer.parseInt(amount)>= discountAmount) {
            result = (" "
                    + (float)(1-DISCOUNT)*100
                    + "% discount for buying "
                    + discountAmount + " or more.");
        }
        return result;
    }
    private BigDecimal getPrice(String name) throws Exception {
        return new Prices().getOne(repository,name)
                .iterator().next().getPrice();
    }

    private int getDiscount(String name) throws Exception {
        return new Prices().getOne(repository,name)
                .iterator().next().getDiscountAmount();
    }

    private int getUnit(String name) throws Exception {
        return new Prices().getOne(repository,name)
                .iterator().next().getUnit();
    }

    private BigDecimal determinePrice(String amount, String name) throws Exception {

        BigDecimal price;
        int orderedAmount = Integer.parseInt(amount);
        if(orderedAmount<getDiscount(name)) {
            price = getPrice(name)
                    .divide(new BigDecimal(getUnit(name)), BigDecimal.ROUND_HALF_UP)
                    .multiply(new BigDecimal(orderedAmount));
        }
        else {
            price = getPrice(name)
                    .divide(new BigDecimal(getUnit(name)), BigDecimal.ROUND_HALF_UP)
                    .multiply(new BigDecimal(orderedAmount))
                    .multiply(new BigDecimal(DISCOUNT));
        }
        return price.setScale(2,RoundingMode.HALF_UP);
    }
}
