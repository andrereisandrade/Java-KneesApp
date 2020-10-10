package br.com.kneesapp.entity;

import br.com.kneesapp.base.ABaseEntity;
import java.util.List;

/**
 *
 * @author andre
 */
public class UserEntity extends ABaseEntity{
    
    private String email;
    private String phone;
    private String password;
    private String name;
    private String photo;
    private List<JRole> permitions;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }    

    public List<JRole> getPermitions() {
        return permitions;
    }

    public void setPermitions(List<JRole> permitions) {
        this.permitions = permitions;
    }
}
