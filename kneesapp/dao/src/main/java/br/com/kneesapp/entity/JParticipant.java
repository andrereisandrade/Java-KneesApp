package br.com.kneesapp.entity;

import java.util.Date;

/**
 *
 * @author andre
 */
public class JParticipant extends UserEntity {

    private String Sex;
    private Date birthDate;

    public String getSex() {
        return Sex;
    }

    public void setSex(String Sex) {
        this.Sex = Sex;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }    
}
