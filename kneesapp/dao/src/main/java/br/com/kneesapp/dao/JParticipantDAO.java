package br.com.kneesapp.dao;

import br.com.kneesapp.base.dao.IBaseDAO;
import static br.com.kneesapp.criteria.BaseCriteria.ID;
import static br.com.kneesapp.criteria.BaseCriteria.NAME;
import static br.com.kneesapp.criteria.BaseCriteria.PHOTO;
import static br.com.kneesapp.criteria.ParticipantCriteria.BIRTH_DATE;
import static br.com.kneesapp.criteria.ParticipantCriteria.PARTICIPANT;
import static br.com.kneesapp.criteria.UserCriteria.EMAIL;
import static br.com.kneesapp.criteria.UserCriteria.SEX;
import static br.com.kneesapp.criteria.UserCriteria.USER_EMAIL;
import static br.com.kneesapp.criteria.UserCriteria.USER_ENTITY;
import br.com.kneesapp.entity.JParticipant;
import br.com.kneesapp.util.JConfigUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author andre
 */
public class JParticipantDAO implements IBaseDAO<JParticipant> {


    @Override
    public void create(Connection conn, JParticipant e) throws Exception {
        int i = 1;
        String sql = queryCreate();

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs;

        ps.setString(i++, e.getSex());
        if (e.getBirthDate() != null) {
            ps.setDate(i++, new Date(e.getBirthDate().getTime()));
        } else {
            ps.setDate(i++, null);
        }

        ps.setString(i++, e.getEmail());
        ps.setString(i++, e.getPhoto());
        rs = ps.executeQuery();
        if (rs.next()) {
            e.setId(rs.getLong("id"));
        }
    }

    @Override
    public JParticipant readById(Connection conn, Long id) throws Exception {
        JParticipant participant = new JParticipant();
        String sql = queryReadById();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            participant  = buildParticipant(rs);
        }
        rs.close();
        ps.close();
        return participant;
    }

    @Override
    public List<JParticipant> readByCriteria(Connection conn, Map<Long, Object> criteria) throws SQLException {
        List<JParticipant> participants = new ArrayList<>();
        String sql = queryReadByCriteria();

        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery(sql);
        while (rs.next()) {
            participants.add(buildParticipant(rs));
        }
        rs.close();
        s.close();

        return participants;
    }

    @Override
    public void update(Connection conn, JParticipant e) throws Exception {
        //TODO: Atualizar na tabela para que os campos foto e telefone fique na tabela participante
        String sql = queryUpdate();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, e.getPhoto());
        ps.setString(3, e.getEmail());
        ps.execute();
        ps.close();
    }

    @Override
    public void delete(Connection conn, Long id) throws Exception {
        String sql = queryDelete();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, id);
        ps.execute();
        ps.close();
    }

    private JParticipant buildParticipant(ResultSet rs) throws SQLException {
        JParticipant participant = new JParticipant();
        participant.setId(rs.getLong(ID));
        participant.setSex(rs.getString(SEX));
        participant.setEmail(rs.getString(EMAIL));
        participant.setName(rs.getString(NAME));
        participant.setPhoto(JConfigUtil.getUserImage(rs.getString(PHOTO)));
        participant.setBirthDate(rs.getDate(BIRTH_DATE));
        return participant;
    }

    private String queryCreate() {
        return "INSERT INTO " + PARTICIPANT + " (" + SEX + ", " + BIRTH_DATE + ", " + USER_EMAIL + ", " + PHOTO + ") "
                + "VALUES (?, ?, ?, ?) returning id";
    }

    private String queryReadByCriteria() {
        return "SELECT * FROM " + USER_ENTITY + " u "
                + "INNER JOIN " + PARTICIPANT + " p ON u." + EMAIL + " = p." + USER_EMAIL;
    }

    private String queryReadById() {
        return "SELECT u." + EMAIL + ", p." + ID + ", u." + NAME + ", p." + BIRTH_DATE + ",  p." + PHOTO + ", p." + SEX + " "
                + " FROM " + USER_ENTITY + " u INNER JOIN " + PARTICIPANT + " p "
                + " ON u." + EMAIL + " = p." + USER_EMAIL + " WHERE p." + ID + " = ?";
    }

    private String queryUpdate() {
        return "UPDATE " + PARTICIPANT + " SET " + PHOTO + "=? WHERE " + USER_EMAIL + "=?";
    }

    private String queryDelete() {
        return " DELETE FROM " + PARTICIPANT + " WHERE " + ID + " = ?";
    }
}
