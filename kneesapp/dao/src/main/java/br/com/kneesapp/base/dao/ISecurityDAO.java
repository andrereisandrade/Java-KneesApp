package br.com.kneesapp.base.dao;

import br.com.kneesapp.entity.JSecurity;
import java.sql.Connection;

/**
 *
 * @author andre
 */
public interface ISecurityDAO extends IBaseDAO<JSecurity> {

    JSecurity findByToken(Connection conn, String token) throws Exception;

    JSecurity findByEmail(Connection conn, String email) throws Exception;
}
