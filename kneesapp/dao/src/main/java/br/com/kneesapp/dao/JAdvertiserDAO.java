package br.com.kneesapp.dao;

import br.com.kneesapp.base.dao.IAdvertiserDAO;
import br.com.kneesapp.entity.JAdvertiser;
import br.com.kneesapp.util.JConfigUtil;
import java.sql.Connection;
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
public class JAdvertiserDAO implements IAdvertiserDAO {

    public final String PARTICIPANT = "participante";
    public final String USUARIO = "usuario";
    public final String ID = "id";
    public final String SEX = "sexo";
    public final String NAME = "nome";
    public final String DATE = "data_nascimento";
    public final String EMAIL = "email";
    public final String USUARIO_EMAIL = "usuario_email";
    public final String PHONE = "telefone";
    public final String PHOTO = "foto";
    public final String PASSWORD = "senha";

    private String numberCount;
    private String document;
    private String type;

    @Override
    public void create(Connection conn, JAdvertiser e) throws Exception {
        int i = 0;

        String sql = "INSERT INTO anunciante(usuario_email, tipo, latitude, longitude, foto, endereco, facebook, site, telefone, descricao) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning id;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(++i, e.getEmail());
        ps.setString(++i, e.getType());
        ps.setString(++i, e.getLatitude());
        ps.setString(++i, e.getLongitude());
        ps.setString(++i, e.getPhoto());
        ps.setString(++i, e.getAddress());
        ps.setString(++i, e.getFacebook());
        ps.setString(++i, e.getSite());
        ps.setString(++i, e.getPhone());
        ps.setString(++i, e.getDescription());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            e.setId(rs.getLong("id"));
        }
        ps.close();
        rs.close();
    }

    @Override
    public JAdvertiser readById(Connection conn, Long id) throws Exception {
        String sql = "select * from anunciante a join usuario u on u.email = a.usuario_email where id = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        JAdvertiser advertiser = new JAdvertiser();
        if (rs.next()) {
            advertiser = buildAdvertiser(rs);
        }
        return advertiser;
    }

    @Override
    public JAdvertiser getAdvertiserByEmail(Connection conn, String email) throws Exception {
        String sql = queryGetAdvertiserByEmail();
                PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        JAdvertiser advertiser = new JAdvertiser();
        if (rs.next()) {
            advertiser = buildAdvertiser(rs);
        }
        return advertiser;
    }

    public void update(Connection conn, JAdvertiser e) throws Exception {
        int i = 0;

        String sql = "UPDATE anunciante set tipo=?, latitude=?, longitude=?, telefone=?, endereco=?, foto=?, facebook=?, "
                + "site=?, descricao=?"
                + " where id = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(++i, e.getType());
        ps.setString(++i, e.getLatitude());
        ps.setString(++i, e.getLongitude());
        ps.setString(++i, e.getPhone());
        ps.setString(++i, e.getAddress());
        ps.setString(++i, e.getPhoto());
        ps.setString(++i, e.getFacebook());
        ps.setString(++i, e.getSite());
        ps.setString(++i, e.getDescription());
        ps.setLong(++i, e.getId());
        ps.execute();
    }

    @Override
    public List<JAdvertiser> readByCriteria(Connection conn, Map<Long, Object> mapCriteria) throws Exception {
        List<JAdvertiser> advertiserList = new ArrayList<>();
        String sql = "select * from anunciante a join usuario u on u.email = a.usuario_email";

        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery(sql);
        while (rs.next()) {
            advertiserList.add(buildAdvertiser(rs));
        }
        rs.close();
        s.close();

        return advertiserList;
    }

    @Override
    public void delete(Connection conn, Long id) throws Exception {
        String sql = "delete from anunciante where id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, id);
        ps.execute();
        ps.close();
    }

    private JAdvertiser buildAdvertiser(ResultSet rs) throws SQLException {
         JAdvertiser advertiser = new JAdvertiser();
            advertiser.setId(rs.getLong(ID));
            advertiser.setName(rs.getString("nome"));
            advertiser.setEmail(rs.getString("email"));
            advertiser.setPhoto(JConfigUtil.getEventImage(rs.getString("foto")));
            advertiser.setLatitude(rs.getString("latitude"));
            advertiser.setLongitude(rs.getString("longitude"));
            advertiser.setAddress(rs.getString("endereco"));
            advertiser.setFacebook(rs.getString("facebook"));
            advertiser.setSite(rs.getString("site"));
            advertiser.setDescription(rs.getString("descricao"));
            advertiser.setPhone(rs.getString("telefone"));
        return advertiser;
    }
    
    private String queryGetAdvertiserByEmail(){
        return  "select * from anunciante a "
                + "inner join usuario u on u.email = a.usuario_email "
                + "where usuario_email = ?";
    }

}
