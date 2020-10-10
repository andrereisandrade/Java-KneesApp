package br.com.kneesapp.base.dao;

import br.com.kneesapp.entity.JRole;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author andre
 */
public interface IRoleDao {
    
    List<JRole> getRoleByUser(String email, Connection conn) throws Exception;
    void setUserRole(String email, Integer roleId,Connection conn) throws Exception;
}
