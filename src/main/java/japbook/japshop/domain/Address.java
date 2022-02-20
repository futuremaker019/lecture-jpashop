package japbook.japshop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable  // 내장 타입이라는 것을 명시
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
