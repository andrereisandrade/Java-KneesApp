package br.com.kneesapp.dao;

import br.com.kneesapp.base.dao.IUserDAO;
import static br.com.kneesapp.criteria.AdvertiserCriteria.ADVERTISER;
import static br.com.kneesapp.criteria.BaseCriteria.ID;
import static br.com.kneesapp.criteria.BaseCriteria.NAME;
import static br.com.kneesapp.criteria.UserCriteria.USER_EMAIL;
import static br.com.kneesapp.criteria.BaseCriteria.PHOTO;
import static br.com.kneesapp.criteria.ParticipantCriteria.PARTICIPANT;
import static br.com.kneesapp.criteria.UserCriteria.EMAIL;
import static br.com.kneesapp.criteria.UserCriteria.USER_ENTITY;
import br.com.kneesapp.entity.JLogin;
import br.com.kneesapp.entity.JParticipant;
import br.com.kneesapp.entity.UserEntity;
import br.com.kneesapp.util.JConfigUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author andre
 */
public class JUserDAO implements IUserDAO {



    public final String DATE = "data_nascimento";
    public final String PHONE = "telefone";
    public final String PASSWORD = "senha";

    @Override
    public UserEntity findUserByEmail(String email, Connection conn) throws Exception {

        String sql = getUserByEmailQuery();
        UserEntity user = new UserEntity();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            user.setEmail(rs.getString(EMAIL));
            user.setName(rs.getString(NAME));
        }
        rs.close();
        ps.close();
        return user;
    }

    @Override
    public UserEntity login(JLogin login, Connection conn) throws Exception {
        UserEntity user = null;
        String sql;
        switch (login.getPermition()) {            
            case 1:
                sql = queryLoginAdmin();
                break;
            case 2:
                sql = queryLoginParticipant();
                break;
            case 3:
                sql = queryLoginAdversiter();
                break;
            default:
                return user;
        }

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, login.getEmail());
        ps.setString(2, login.getPassword());

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            user = new JParticipant();
            user.setEmail(rs.getString(EMAIL));
            user.setName(rs.getString(NAME));
            user.setPhoto(JConfigUtil.getUserImage(rs.getString(PHOTO)));
            user.setId(Long.parseLong(rs.getString(ID)));
        }
        rs.close();
        ps.close();
        return user;
    }

    @Override
    public void create(Connection conn, UserEntity e) throws Exception {

        String sql = "INSERT INTO  usuario (email, nome, senha) values  (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getEmail());
            ps.setString(2, e.getName());
            ps.setString(3, e.getPassword());
            ps.execute();
        }
    }

    @Override
    public UserEntity readById(Connection conn, Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserEntity> readByCriteria(Connection conn, Map<Long, Object> criteria) throws Exception {
        List<UserEntity> userList = new ArrayList<>();
        String sql = "select * from usuario";

        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery(sql);
        while (rs.next()) {
            UserEntity user = new UserEntity();
            user.setEmail(rs.getString("email"));
            user.setName(rs.getString("nome"));
            user.setPassword(rs.getString("senha"));
            userList.add(user);
        }
        rs.close();
        s.close();

        return userList;
    }

    @Override
    public void update(Connection conn, UserEntity e) throws Exception {
        String sql = "UPDATE usuario SET nome=?, senha=? where email=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, e.getName());
        ps.setString(2, e.getPassword());
        ps.setString(3, e.getEmail());
        ps.execute();
        ps.close();
    }

    @Override
    public void delete(Connection conn, Long id) throws Exception {
        //TODO: Usuario não tem id, exclusao deve ser por email
        String sql = " DELETE FROM usuario WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, id);
        ps.execute();
        ps.close();
    }
    
    public void deleteByEmail(Connection conn, String email) throws Exception {
        String sql = " DELETE FROM usuario WHERE email = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, email);
        ps.execute();
        ps.close();
    }

    @Override
    public void updateToken(Connection conn, String token) throws Exception {
        String sql = "update token set token = ? where id = 1";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, token);
        ps.execute();
        ps.close();
    }

    @Override
    public String getToken(Connection conn) throws Exception {
        String token = null;
        String sql = "select * from token where id = 1";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
           token = rs.getString("token");
        }
        rs.close();
        return token;
    }

    
    private String queryLoginParticipant() {
        return "SELECT *FROM " + PARTICIPANT + " p "
                + "INNER JOIN " + USER_ENTITY + " u ON p." + USER_EMAIL + " = u." + EMAIL + ""
                + " WHERE u." + EMAIL + " = ? AND u." + PASSWORD + " = ?";
    }

    private String queryLoginAdversiter() {
        return "SELECT *FROM " + ADVERTISER + " a "
                + "INNER JOIN " + USER_ENTITY + " u ON a." + USER_EMAIL + " = u." + EMAIL + ""
                + " WHERE u." + EMAIL + " = ? AND u." + PASSWORD + " = ?";
    }

    private String queryLoginAdmin() {
        return "SELECT *FROM " + USER_ENTITY + " WHERE " + EMAIL + "=? AND " + PASSWORD + "= ?";
    }

    private String getUserByEmailQuery() {
        return "SELECT *FROM " + USER_ENTITY + " WHERE " + EMAIL + "= ?";
    }

}
