package br.com.kneesapp.dao;

import br.com.kneesapp.base.dao.IEventDAO;
import static br.com.kneesapp.criteria.AdvertiserCriteria.ADVERTISER;
import static br.com.kneesapp.criteria.AdvertiserCriteria.ADVERTISER_ID;
import static br.com.kneesapp.criteria.CategoryCriteria.CATEGORY;
import static br.com.kneesapp.criteria.CategoryCriteria.CATEGORY_ID;
import static br.com.kneesapp.criteria.EventCriteria.ADVERTISER_IL;
import static br.com.kneesapp.criteria.EventCriteria.CATEGORY_IL;
import static br.com.kneesapp.criteria.EventCriteria.ADVERTISER_ID_EQ;
import static br.com.kneesapp.criteria.EventCriteria.DATE;
import static br.com.kneesapp.criteria.EventCriteria.DESCRIPTION;
import static br.com.kneesapp.criteria.EventCriteria.EVENT;
import static br.com.kneesapp.criteria.EventCriteria.ID;
import static br.com.kneesapp.criteria.EventCriteria.NAME;
import static br.com.kneesapp.criteria.EventCriteria.NAME_IL;
import static br.com.kneesapp.criteria.EventCriteria.PHOTO;
import static br.com.kneesapp.criteria.EventCriteria.CITY;
import static br.com.kneesapp.criteria.EventCriteria.STREET;
import static br.com.kneesapp.criteria.EventCriteria.NUMBER;
import static br.com.kneesapp.criteria.EventCriteria.NEIGBORHOOD;
import static br.com.kneesapp.criteria.EventCriteria.COUNTRY;
import static br.com.kneesapp.criteria.EventCriteria.LIMIT_IL;
import static br.com.kneesapp.criteria.EventCriteria.OFFSET_IL;
import static br.com.kneesapp.criteria.EventCriteria.DATE_IL;
import static br.com.kneesapp.criteria.EventCriteria.ADDRESS;
import br.com.kneesapp.entity.EventEntity;
import br.com.kneesapp.entity.JAdvertiser;
import br.com.kneesapp.entity.JCategory;
import br.com.kneesapp.util.JConfigUtil;
import br.com.kneesapp.util.PreparedStatementBuilder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static br.com.kneesapp.criteria.EventCriteria.ADDRESS_IL;

/**
 *
 * @author andre
 */
public class JEventDAO implements IEventDAO {

    @Override
    public void create(Connection conn, EventEntity event) throws Exception {
        String sql = queryCreate();
        int i = 1;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(i++, event.getName());
        ps.setString(i++, event.getPhoto());
        ps.setDate(i++, new Date(event.getDate().getTime()));
        ps.setString(i++, event.getDescription());
        ps.setLong(i++, 1); //e.getCategory().getId());
        ps.setLong(i++, event.getAdvertiser().getId()); //e.getAddress().getId());
        ps.setString(i++, event.getAddress());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            event.setId(rs.getLong(ID));
        }
        rs.close();
        ps.close();
    }

    @Override
    public EventEntity readById(Connection conn, Long id) throws Exception {
        EventEntity event = null;
        String sql = queryReadById();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            event = buildEvent(rs);
        }

        rs.close();
        ps.close();
        return event;
    }

    @Override
    public List<EventEntity> readByCriteria(Connection conn, Map<Long, Object> mapCriteria) throws Exception {
        List<EventEntity> eventList = new ArrayList<>();
        PreparedStatement statement = getPreparedStatament(conn, mapCriteria);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            eventList.add(buildEvent(rs));
        }

        rs.close();
        statement.close();
        return eventList;
    }

    @Override
    public void update(Connection conn, EventEntity e) throws Exception {
        String sql = queryUpdate();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(3, e.getId());
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

    private PreparedStatement getPreparedStatament(Connection conn, Map<Long, Object> mapCriteria) throws SQLException {
        String sql = queryReadByCriteria();

        List<Object> paramList = new ArrayList<>();

        if (mapCriteria.containsKey(NAME_IL)) {
            String name = "%" + (String) mapCriteria.get(NAME_IL) + "%";
            sql += " AND e." + NAME + " ILIKE  ? ";
            paramList.add(name);
        }
        if (mapCriteria.containsKey(CATEGORY_IL)) {
            String category = "%" + (String) mapCriteria.get(CATEGORY_IL) + "%";
            sql += " AND c." + NAME + " ILIKE  ? ";
            paramList.add(category);
        }
        if (mapCriteria.containsKey(ADVERTISER_IL)) {
            String advertiser = "%" + (String) mapCriteria.get(ADVERTISER_IL) + "%";
            sql += " AND u." + NAME + " ILIKE ?";
            paramList.add(advertiser);
        }
        if (mapCriteria.containsKey(ADVERTISER_ID_EQ)) {
            String advertiserId = (String) mapCriteria.get(ADVERTISER_ID_EQ);
            sql += " AND a.id = CAST (? AS int)";
            paramList.add(advertiserId);
        }
        if (mapCriteria.containsKey(ADDRESS_IL)) {
            String address = "%" + (String) mapCriteria.get(ADDRESS_IL) + "%";
            sql += " AND e." + ADDRESS + " ILIKE ?";
            paramList.add(address);
        }
        if (mapCriteria.containsKey(DATE_IL)) {
            String date = "%" + (Date) mapCriteria.get(DATE_IL) + "%";
            sql += " AND e." + DATE + " ILIKE ?";
            paramList.add(date);
        }

        sql += " ORDER BY id ASC ";

        if (mapCriteria.containsKey(LIMIT_IL)) {
            Long limit = (Long) mapCriteria.get(LIMIT_IL);
            sql += "LIMIT ?";
            paramList.add(limit);
        }
        if (mapCriteria.containsKey(OFFSET_IL)) {
            Long offset = (Long) mapCriteria.get(OFFSET_IL);
            sql += "OFFSET ?";
            paramList.add(offset);
        }
        return PreparedStatementBuilder.build(conn, sql, paramList);
    }

    private EventEntity buildEvent(ResultSet rs) throws SQLException {

        EventEntity event = new EventEntity();
        event.setId(rs.getLong(ID));
        event.setDate(rs.getDate(DATE));
        event.setName(rs.getString(NAME));
        event.setDescription(rs.getString(DESCRIPTION));
        event.setPhoto(JConfigUtil.getEventImage(rs.getString(PHOTO)));
        event.setAddress(rs.getString(ADDRESS));
//        event.setCityName(rs.getString(CITY));
//        event.setNumber(rs.getString(NUMBER));
//        event.setStreet(rs.getString(STREET));
//        event.setCountry(rs.getString(COUNTRY));
//        event.setNeighborhood(rs.getString(NEIGBORHOOD));

        JCategory category = new JCategory();
        category.setName(rs.getString(CATEGORY));

        JAdvertiser advertiser = new JAdvertiser();
        advertiser.setName(rs.getString(ADVERTISER));

        event.setCategory(category);
        event.setAdvertiser(advertiser);

        return event;

    }

    private String queryCreate() {
        return "INSERT INTO " + EVENT
                + "(" + NAME + ","
                + PHOTO + ","
                + DATE + ","
                + DESCRIPTION + ","
                + CATEGORY_ID + ","
                + ADVERTISER_ID + ","
                + ADDRESS + ") "
                + "VALUES (?,?,?,?,?,?,?) RETURNING id";
    }

    private String queryReadById() {
        return "SELECT e.id, e." + NAME + ", c." + NAME + " as categoria, e." + DESCRIPTION + ", e." + DATE + ", e." + PHOTO + " , "
                + "u." + NAME + " as anunciante ,e." + CITY + " , e." + COUNTRY + ",  e." + STREET + ", e." + NUMBER + ", e." + ADDRESS + ", e." + NEIGBORHOOD + "\n "
                + "	FROM evento e \n"
                + "	inner join categoria c on e.categoria_id = c.id\n"
                + "	inner join anunciante a on e.anunciante_id = a.id\n"
                + "	inner join usuario u on a.usuario_email = u.email\n"
                //+ "	inner join endereco en on e.endereco_id = en.id\n"
                + "     WHERE e.id = ?";
    }

    private String queryReadByCriteria() {
        return "SELECT e.id, e." + NAME + ", c." + NAME + " as categoria, e." + DESCRIPTION + ", e." + DATE + ", e." + PHOTO + " , "
                + "u." + NAME + " as anunciante ,e." + CITY + " , e." + COUNTRY + ",  e." + STREET + ", e." + NUMBER + ", e." + ADDRESS + ", e." + NEIGBORHOOD + "\n "
                + "	FROM evento e \n"
                + "	inner join categoria c on e.categoria_id = c.id\n"
                + "	inner join anunciante a on e.anunciante_id = a.id\n"
                + "	inner join usuario u on a.usuario_email = u.email\n"
                + "     WHERE 1 = 1";
    }

    private String queryUpdate() {
        return null;
    }

    private String queryDelete() {
        return " DELETE FROM " + EVENT + " WHERE " + ID + " = ?";
    }

    @Override
    public List<EventEntity> readByAdvertiserId(Connection conn, Long id) throws SQLException {
        String sql = "select * from evento e"
                + " inner join anunciante a on a.id = e.anunciante_id "
                + "where a.id = ?";

        List<EventEntity> eventList = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            eventList.add(buildEvent(rs));
        }

        rs.close();
        ps.close();
        return eventList;

    }
}
