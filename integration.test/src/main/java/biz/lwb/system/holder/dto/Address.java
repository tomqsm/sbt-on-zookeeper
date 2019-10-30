package biz.lwb.system.holder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String city;
    private String postalCode;
    private String street;
    private String homeNumber;
    private String apartmentNumber;
    private String prefixStreet;
    private String postOffice;
    private String countryCode;
    private String voivodship;
    private Date lastModificationDate;

}
