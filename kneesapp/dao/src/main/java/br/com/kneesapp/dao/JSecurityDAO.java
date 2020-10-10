package br.com.kneesapp.dao;

import br.com.kneesapp.entity.JSecurity;
import br.com.kneesapp.entity.UserEntity;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import br.com.kneesapp.base.dao.ISecurityDAO;
import br.com.kneesapp.entity.JRole;

/**
 *
 * @author andre
 */
public class JSecurityDAO implements ISecurityDAO {

    public final String SECURITY = "seguranca";
    public final String ROLE_USER = "role_usuario";
    public final String USER = "usuario";
    public final String ROLE = "role";

    public final String TOKEN = "token";
    public final String EXPIRATION_DATE = "data_expiracao";
    public final String USER_EMAIL = "usuario_email";
    public final String ROLE_ID = "role_id";
    public final String NAME = "nome";
    public final String EMAIL = "email";
    public final String ID = "id";

    @Override
    public JSecurity findByToken(Connection conn, String token) throws Exception {
        String sql = findByTokenQuery();
        JSecurity security = new JSecurity();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, token);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            security.setToken(rs.getString(TOKEN));
            security.setTokenExpiration(rs.getDate(EXPIRATION_DATE));
            security.setUser(getUser(conn, rs.getString(USER_EMAIL)));
        }
        rs.close();
        ps.close();
        return security;
    }

    @Override
    public JSecurity findByEmail(Connection conn, String email) throws Exception {
        String sql = findByEmailQuery();
        JSecurity security = new JSecurity();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            security.setToken(rs.getString(TOKEN));
            security.setTokenExpiration(rs.getDate(EXPIRATION_DATE));
            security.setUser(getUser(conn, rs.getString(USER_EMAIL)));
        }
        rs.close();
        ps.close();
        return security;
    }

    @Override
    public void create(Connection conn, JSecurity e) throws Exception {
        String sql = queryCreate();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getToken());
            ps.setDate(2, new Date(e.getTokenExpiration().getTime()));
            ps.setString(3, e.getUser().getEmail());
            ps.execute();
        }
    }

    @Override
    public JSecurity readById(Connection conn, Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<JSecurity> readByCriteria(Connection conn, Map<Long, Object> criteria) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Connection conn, JSecurity e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Connection conn, Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private UserEntity getUser(Connection conn, String email) throws Exception {
        JUserDAO userDAO = new JUserDAO();
        UserEntity user = userDAO.findUserByEmail(email, conn);
        user.setPermitions(getRoleListByUser(conn, email));
        return user;
    }

    private List<JRole> getRoleListByUser(Connection conn, String email) throws Exception {
        JRoleDAO userDAO = new JRoleDAO();
        return userDAO.getRoleByUser(email, conn);
    }

    private String findByEmailQuery() {
        return "SELECT *FROM " + SECURITY + " WHERE " + USER_EMAIL + "=?";
    }

    private String findByTokenQuery() {
        return "SELECT *FROM " + SECURITY + " WHERE " + TOKEN + "=?";
    }

    private String findByTokenCompleteQuery() {
        return "SELECT *FROM " + SECURITY + " s INNER JOIN " + USER + " u on s." + USER_EMAIL + " = u." + EMAIL + " "
                + "INNER JOIN " + ROLE_USER + " ru on ru." + USER_EMAIL + " = u." + EMAIL + " "
                + "INNER JOIN " + ROLE + " r on r." + ID + " = ru." + ROLE_ID + ""
                + "WHERE u." + EMAIL + " = ?";
    }

    private String queryCreate() {
        return "INSERT INTO " + SECURITY + " (" + TOKEN + ", " + EXPIRATION_DATE + ", " + USER_EMAIL + ") "
                + "VALUES (?, ?, ?)";
    }

}
