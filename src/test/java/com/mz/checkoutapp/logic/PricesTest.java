package com.mz.checkoutapp.logic;

import com.mz.checkoutapp.repository.Item;
import com.mz.checkoutapp.repository.ItemRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@TestPropertySource(locations = "classpath:test.properties")
@RunWith(SpringRunner.class)
@DataJpaTest

public class PricesTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void get() throws Exception {
        Item test1 = new Item("test1",new BigDecimal(1),1,1);
        Item test2 = new Item("test2",new BigDecimal(1),1,1);
        Set<Item> expected = new HashSet<>();
        expected.add(test1);
        expected.add(test2);
        expected.stream().forEach(testEntityManager::persist);
        Set<Item> result = new HashSet<>(itemRepository.findAll());
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getOne() throws Exception {
        Item test1 = new Item("test1",new BigDecimal(1),1,1);
        Set<Item> expected = new HashSet<>();
        expected.add(test1);
        testEntityManager.persist(test1);
        Set<Item> result = new HashSet<>();
        result.add(itemRepository.findByName(test1.getName()));
        Assert.assertEquals(expected, result);
    }
}