package com.mz.checkoutapp.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;

import static org.junit.Assert.*;


@TestPropertySource(locations = "classpath:test.properties")
@RunWith(SpringRunner.class)
@DataJpaTest
public class ItemRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void findByNameTest() {

        Item test = new Item("test",new BigDecimal(1),1,1);
        testEntityManager.persist(test);
        Item item = itemRepository.findByName(test.getName());
        assertEquals(test.getName(),item.getName());
    }

}