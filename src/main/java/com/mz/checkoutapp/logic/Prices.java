package com.mz.checkoutapp.logic;

import com.mz.checkoutapp.repository.Item;
import com.mz.checkoutapp.repository.ItemRepository;

import java.util.HashSet;
import java.util.Set;

public class Prices {
    public Prices(){}
    public Set<Item> get(ItemRepository repository) {
        Set<Item> items = new HashSet<>();
        repository.findAll().forEach(items::add);
        return items;
    }
    public Set<Item> getOne(ItemRepository repository, String name) {
        Set<Item> item = new HashSet<>();
        item.add(repository.findByName(name));
        return item;
    }
}
