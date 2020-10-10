package br.com.kneesapp.entity;

import br.com.kneesapp.base.ABaseEntity;
import java.util.Date;

/**
 *
 * @author andre
 */
public class JSecurity extends ABaseEntity {

    private String token = "(init)";
    private Date tokenExpiration;
    private UserEntity user;

    public JSecurity(String token, Date tokenExpiration, UserEntity user) {
        this.token = token;
        this.tokenExpiration = tokenExpiration;
        this.user = user;
    }

    public JSecurity() {
    }

    public void updateToken(String token, Date tokenExpiration) {
        this.token = token;
        this.tokenExpiration = tokenExpiration;
    }

    public void tokenExpiration() {
        this.updateToken("", new Date());
    }

    public boolean expiration() {
        return tokenExpiration != null && tokenExpiration.before(new Date());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTokenExpiration(Date tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Date getTokenExpiration() {
        return tokenExpiration;
    }

    public UserEntity getUser() {
        return user;
    }

    public boolean isSalvo() {
        return getId() != null;
    }

    public boolean isNaoSalvo() {
        return !isSalvo();
    }

}
