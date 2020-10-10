package br.com.kneesapp.entity;

import br.com.kneesapp.base.BaseEvent;
import java.util.Date;

/**
 *
 * @author andre
 */
public class EventResponse extends BaseEvent {

    private String cep;
    private String state;
    private String number;
    private String street;
    private String country;
    private String cityName;
    private String latitude;
    private String longitude;
    private String neighborhood;

    private String category;
    private String advertiser;

    private boolean externalEvent;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

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

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }

    public boolean isExternalEvent() {
        return externalEvent;
    }

    public void setExternalEvent(boolean externalEvent) {
        this.externalEvent = externalEvent;
    }
}
