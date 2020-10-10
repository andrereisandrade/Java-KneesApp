package br.com.kneesapp.service.base;

import br.com.kneesapp.entity.JLogin;
import br.com.kneesapp.entity.UserEntity;

/**
 *
 * @author andre
 */
public interface IUserService extends IBaseCRUDService<UserEntity> {

    UserEntity login(JLogin login) throws Exception;

    UserEntity findUserByEmail(String email) throws Exception;
    
    void updateToken(String token) throws Exception;   

}
