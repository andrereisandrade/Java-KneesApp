package br.com.kneesapp.dao;

import br.com.kneesapp.base.dao.IRoleDao;
import br.com.kneesapp.entity.JRole;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andre
 */
public class JRoleDAO implements IRoleDao {

    public final String ROLE = "role";
    public final String ROLE_USUARIO = "role_usuario";
    public final String ID = "id";
    public final String ROLE_ID = "role_id";
    public final String USUARIO_EMAIL = "usuario_email";
    public final String NAME = "nome";

    @Override
    public List<JRole> getRoleByUser(String email, Connection conn) throws Exception {

        List<JRole> roleList = new ArrayList<>();
        String sql = getRolesByUser();

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            JRole role = new JRole();
            role.setId(rs.getLong(ID));
            role.setName(rs.getString(NAME));
            roleList.add(role);
        }
        rs.close();
        ps.close();

        return roleList;
    }
    
    @Override
    public void setUserRole(String email, Integer roleId, Connection conn) throws Exception {
        String sql = "insert into " + ROLE_USUARIO + " (usuario_email, role_id) values (?, ?) ";
        
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, email);
        ps.setLong(2, roleId);
        ps.execute();
        ps.close();
    }
    
    private String getRolesByUser() {
        return "SELECT *FROM " + ROLE_USUARIO + " ru "
                + " INNER JOIN " + ROLE + " r ON r." + ID + " = ru." + ROLE_ID + " "
                + " WHERE " + USUARIO_EMAIL + " = ? ";
    }
}
