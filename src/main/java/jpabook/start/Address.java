package jpabook.start;

import lombok.AllArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
public class Address {
    private String city;
    private String street;
    private String zipCode;

    public Address() {
    }
    //city+street+zipCode
}
