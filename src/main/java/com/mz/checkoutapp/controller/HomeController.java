package com.mz.checkoutapp.controller;


import com.mz.checkoutapp.logic.Order;
import com.mz.checkoutapp.prices.Prices;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class HomeController {

    @RequestMapping(value = "/")
    public String home() {
        return "checkout application";
    }

    @RequestMapping(value = "/prices")
    public Map returnPrices(@RequestParam(value="name", defaultValue="all") String name) throws Exception {
        if(name.equalsIgnoreCase("all")) {
            return Prices.getAllPrices();
        }
        else {
            return Prices.getOne(name);
        }
    }

    @RequestMapping(value="/order", method = RequestMethod.POST, consumes="application/json")
    @ResponseBody
    public Map<String,String> makeOrder(@RequestBody String json) throws Exception {
        Order order = new Order(json);
        return order.getOrder();
    }

}
