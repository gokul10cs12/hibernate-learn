package com.example.hibernatelearn.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@AttributeOverrides({
        @AttributeOverride(
                name = "shippingAddress.address",
                column = @Column(name = "shipping_address")
        ),
        @AttributeOverride(
                name = "shippingAddress.city",
                column = @Column(name = "shipping_city")
        ),
        @AttributeOverride(
                name = "shippingAddress.state",
                column = @Column(name = "shipping_state")
        ),
        @AttributeOverride(
                name = "shippingAddress.zipCode",
                column = @Column(name = "shipping_zip_code")
        ),
        @AttributeOverride(
                name = "billToAddress.address",
                column = @Column(name = "bill_to_address")
        ),
        @AttributeOverride(
                name = "billToAddress.city",
                column = @Column(name = "bill_to_city")
        ),
        @AttributeOverride(
                name = "billToAddress.state",
                column = @Column(name = "bill_to_state")
        ),
        @AttributeOverride(
                name = "billToAddress.zipCode",
                column = @Column(name = "bill_to_zip_code")
        )
})

public class OrderHeader extends BaseEntity {

    private String customer;
    @Embedded
    private Address shippingAddress;
    @Embedded
    private Address billToAddress;
    @OneToMany(mappedBy = "orderHeader", cascade = CascadeType.PERSIST)  // One order header can have many orderLine. The MappedBy will define the object of OrderHeader.
    private Set<OrderLine> orderLines;
    @Enumerated(EnumType.STRING) // EnumType.ORDINAL is the default
    private OrderStatus orderStatus;

    public void addOrderLine(OrderLine orderLine){
        if (orderLines == null){
            orderLines = new HashSet<>();
        }
        orderLines.add(orderLine);
        orderLine.setOrderHeader(this);
    }

    public Set<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Set<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public Address getBillToAddress() {
        return billToAddress;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setBillToAddress(Address billToAddress) {
        this.billToAddress = billToAddress;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }


}
