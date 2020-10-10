package br.com.kneesapp.service.base;

import br.com.kneesapp.entity.JSecurity;
import br.com.kneesapp.entity.UserEntity;

/**
 *
 * @author andre
 */
public interface ISecurityService {

    JSecurity findByToken(String token) throws Exception;

    JSecurity findByUsuario(UserEntity usuario) throws Exception;

    void create(JSecurity e) throws Exception;
}
