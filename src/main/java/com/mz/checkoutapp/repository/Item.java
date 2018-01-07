package com.mz.checkoutapp.repository;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getDiscountAmount() {
        return discount_amount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discount_amount = discountAmount;
    }

    public Item(){}

    public Item(String name, BigDecimal price, int unit, int discountAmount) {
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.discount_amount = discountAmount;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private int unit;
    private int discount_amount;




}
