package com.example.hibernatelearn.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class OrderLine extends BaseEntity {
    private Integer quantityOrdered;
    @ManyToOne // here many OrderLine can have one orderHeader
    private OrderHeader orderHeader;

    @ManyToOne
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantityOrdered() {
        return quantityOrdered;
    }

    public OrderHeader getOrderHeader(OrderHeader orderHeader) {
        return this.orderHeader;
    }

    public void setQuantityOrdered(Integer quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public void setOrderHeader(OrderHeader orderHeader) {
        this.orderHeader = orderHeader;
    }
}
