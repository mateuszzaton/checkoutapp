package com.mz.checkoutapp.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mz.checkoutapp.repository.Item;
import com.mz.checkoutapp.repository.ItemRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@TestPropertySource(locations = "classpath:test.properties")
@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ItemRepository itemRepository;


    @Test
    public void getOrder() throws Exception {
        Item test = new Item("test",new BigDecimal(1),1,10);
        int orderAmount = 1;
        String orderName = "test";
        Map<String,String> expected = new HashMap<>();
        expected.put("test", new BigDecimal(1).setScale(2, RoundingMode.HALF_UP)+" for "+ orderAmount + " units.");
        expected.put("Total price", new BigDecimal(1).setScale(2,RoundingMode.HALF_UP).toString());
        testEntityManager.persist(test);
        String testOrder = "{\""+orderName+"\":\""+orderAmount+"\"}";
        Order order = new Order(testOrder, itemRepository);
        Assert.assertEquals(expected,order.getOrder());
    }
}