package br.com.kneesapp.base.dao;

import br.com.kneesapp.entity.JLogin;
import br.com.kneesapp.entity.UserEntity;
import java.sql.Connection;

/**
 *
 * @author andre
 */
public interface IUserDAO extends IBaseDAO<UserEntity> {

    UserEntity login(JLogin login, Connection conn) throws Exception;

    UserEntity findUserByEmail(String email, Connection conn) throws Exception;

    void updateToken(Connection conn, String token) throws Exception;

    String getToken(Connection conn) throws Exception;
}
