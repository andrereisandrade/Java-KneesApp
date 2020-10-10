package br.com.kneesapp.entity;

/**
 *
 * @author andre
 */
public class JLogin {
    
    private String clientId;
    private String clientSecurity;
    private String password;
    private String email;
    private int permition;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecurity() {
        return clientSecurity;
    }

    public void setClientSecurity(String clientSecurity) {
        this.clientSecurity = clientSecurity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }   

    public int getPermition() {
        return permition;
    }

    public void setPermition(int permition) {
        this.permition = permition;
    }
    
}
