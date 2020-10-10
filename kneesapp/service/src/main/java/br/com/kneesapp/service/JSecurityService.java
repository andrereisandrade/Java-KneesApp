package br.com.kneesapp.service;

import br.com.kneesapp.dao.JRoleDAO;
import br.com.kneesapp.dao.JSecurityDAO;
import br.com.kneesapp.entity.JSecurity;
import br.com.kneesapp.entity.UserEntity;
import br.com.kneesapp.service.base.ISecurityService;
import br.com.kneesapp.sql.JConnectionDB;
import java.sql.Connection;

/**
 *
 * @author andre
 */
public class JSecurityService implements ISecurityService {

    @Override
    public JSecurity findByToken(String token) throws Exception {
        JSecurity securityAPI = new JSecurity();
        Connection conn = JConnectionDB.getInstance().getConnection();
        try {
            JSecurityDAO dao = new JSecurityDAO();
            securityAPI = dao.findByToken(conn, token);
            conn.commit();
            conn.close();
        } catch (Exception ex) {
            conn.commit();
            conn.close();
            throw ex;
        }

        return securityAPI;
    }

    @Override
    public JSecurity findByUsuario(UserEntity user) throws Exception {
        JSecurity securityAPI = new JSecurity();
        Connection conn = JConnectionDB.getInstance().getConnection();
        try {
            JSecurityDAO dao = new JSecurityDAO();
            securityAPI = dao.findByEmail(conn, user.getEmail());
            conn.commit();
            conn.close();
        } catch (Exception ex) {
            conn.commit();
            conn.close();
            throw ex;
        }
        return securityAPI;
    }

    public void setUserRole(String email, Integer roleId) throws Exception {
        Connection conn = JConnectionDB.getInstance().getConnection();
        try {
            JRoleDAO roleDAO = new JRoleDAO();
            roleDAO.setUserRole(email, roleId, conn);
            conn.commit();
            conn.close();
        } catch (Exception ex) {
            conn.rollback();
            conn.close();
            throw ex;
        }
    }

    @Override
    public void create(JSecurity security) throws Exception {
        Connection conn = JConnectionDB.getInstance().getConnection();
        try {
            JSecurityDAO securityDAO = new JSecurityDAO();
            securityDAO.create(conn, security);
            conn.commit();
            conn.close();
        } catch (Exception ex) {
            conn.rollback();
            conn.close();
            throw ex;
        }
    }

}
