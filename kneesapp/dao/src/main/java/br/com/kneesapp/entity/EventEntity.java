package br.com.kneesapp.entity;

import br.com.kneesapp.base.BaseEvent;

/**
 *
 * @author andre.andrade
 */
public class EventEntity extends BaseEvent {

    private JAdvertiser advertiser;
    private JCategory category;
    private boolean externalEvent;
    private String latitude;
    private String longitude;
    private String address;

//    private String street;
//    private String neighborhood;
//    private String number;
//    private String cityName;
//    private String state;
//    private String cep;
//    private String country;

    public JAdvertiser getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(JAdvertiser advertiser) {
        this.advertiser = advertiser;
    }

    public JCategory getCategory() {
        return category;
    }

    public void setCategory(JCategory category) {
        this.category = category;
    }

    public boolean isExternalEvent() {
        return externalEvent;
    }

    public void setExternalEvent(boolean externalEvent) {
        this.externalEvent = externalEvent;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String Address) {
        this.address = Address;
    }
    

//    public String getStreet() {
//        return street;
//    }
//
//    public void setStreet(String street) {
//        this.street = street;
//    }
//
//    public String getNeighborhood() {
//        return neighborhood;
//    }
//
//    public void setNeighborhood(String neighborhood) {
//        this.neighborhood = neighborhood;
//    }
//
//    public String getNumber() {
//        return number;
//    }
//
//    public void setNumber(String number) {
//        this.number = number;
//    }
//
//    public String getCityName() {
//        return cityName;
//    }
//
//    public void setCityName(String cityName) {
//        this.cityName = cityName;
//    }
//
//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

//    public String getCep() {
//        return cep;
//    }
//
//    public void setCep(String cep) {
//        this.cep = cep;
//    }
//
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }

}
