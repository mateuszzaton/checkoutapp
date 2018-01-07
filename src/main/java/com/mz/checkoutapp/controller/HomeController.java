package com.mz.checkoutapp.controller;


import com.mz.checkoutapp.logic.Order;
import com.mz.checkoutapp.logic.Prices;
import com.mz.checkoutapp.repository.Item;
import com.mz.checkoutapp.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
public class HomeController {

    @Autowired
    private ItemRepository repository;
    private final String ALL = "all";
    @Qualifier("itemRepository")

    @RequestMapping(value = "/")
    public String home() {
        return "checkout application";
    }

    @RequestMapping(value = "/add")
    void add() {
        Set<Item> set = new HashSet<>();
        set.add(new Item("A", new BigDecimal(40),3,70));
        set.add(new Item("B", new BigDecimal(10),2,15));
        set.add(new Item("C", new BigDecimal(30),4,60));
        set.add(new Item("D", new BigDecimal(25),2,40));
        set.stream().forEach(repository::save);
    }
    @RequestMapping(value = "/prices")
    public Set returnPrices(@RequestParam(value="name", defaultValue=ALL) String name) throws Exception {
        if(name.equalsIgnoreCase(ALL)) {
                return new Prices().get(repository);
            }
            else {
                return new Prices().getOne(repository,name);
            }
    }

    @RequestMapping(value="/order", method = RequestMethod.POST, consumes="application/json")
    public @ResponseBody Map<String,String> makeOrder(@RequestBody String json) throws Exception {
        Order order = new Order(json, repository);
        return order.getOrder();
    }

}
