package br.com.kneesapp.service;

import br.com.kneesapp.constants.ERole;
import br.com.kneesapp.dao.JAdvertiserDAO;
import br.com.kneesapp.dao.JRoleDAO;
import br.com.kneesapp.dao.JUserDAO;
import br.com.kneesapp.entity.JAdvertiser;
import br.com.kneesapp.entity.UserEntity;

import br.com.kneesapp.service.base.IAdvertiserService;
import br.com.kneesapp.service.util.JUtil;
import br.com.kneesapp.sql.JConnectionDB;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author andre
 */
public class JAdvertiserService implements IAdvertiserService {

    @Override
    public void create(JAdvertiser advertiser) throws Exception {
        Connection conn = JConnectionDB.getInstance().getConnection();
        try {
            JUserDAO userDAO = new JUserDAO();
            JAdvertiserDAO advertiserDAO = new JAdvertiserDAO();
            JRoleDAO roleDAO = new JRoleDAO();

            // If doesn't exsist the user then create it
            UserEntity user = userDAO.findUserByEmail(advertiser.getEmail(), conn);
            if (user.getEmail() == null) {
                userDAO.create(conn, advertiser);
            }
            advertiser.setPhoto(JUtil.setImageProfileIfIsNull(advertiser.getPhoto()));
            advertiserDAO.create(conn, advertiser);
            roleDAO.setUserRole(advertiser.getEmail(), ERole.ADVERTISER_ID, conn);

            conn.commit();
            conn.close();
        } catch (Exception ex) {
            conn.rollback();
            conn.close();
            throw ex;
        }

    }

    @Override
    public JAdvertiser readById(Long id) throws Exception {
        Connection conn = JConnectionDB.getInstance().getConnection();
        JAdvertiser advertiser = new JAdvertiser();
        try {

            JAdvertiserDAO advertiserDAO = new JAdvertiserDAO();

            advertiser = advertiserDAO.readById(conn, id);
            conn.commit();
            conn.close();
        } catch (Exception ex) {
            conn.rollback();
            conn.close();
            throw ex;
        }
        return advertiser;
    }

    @Override
    public List<JAdvertiser> readByCriteria(Integer page, Integer pagesize, String filter) throws Exception {
        List<JAdvertiser> advertiserList = new ArrayList<>();
        Map<Long, Object> mapCriteria = getCriteria(filter);

        try {
            Connection conn = JConnectionDB.getInstance().getConnection();
            JAdvertiserDAO dao = new JAdvertiserDAO();
            advertiserList = dao.readByCriteria(conn, mapCriteria);
            conn.commit();
            conn.close();
        } catch (Exception ex) {
            throw ex;
        }
        return advertiserList;
    }

    @Override
    public void update(JAdvertiser e) throws Exception {
        Connection conn = JConnectionDB.getInstance().getConnection();
        try {

            JAdvertiserDAO advertiserDAO = new JAdvertiserDAO();

            advertiserDAO.update(conn, e);
            conn.commit();
            conn.close();
        } catch (Exception ex) {
            conn.rollback();
            conn.close();
            throw ex;
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        Connection conn = JConnectionDB.getInstance().getConnection();
        try {

            JAdvertiserDAO advertiserDAO = new JAdvertiserDAO();

            advertiserDAO.delete(conn, id);
            conn.commit();
            conn.close();
        } catch (Exception ex) {
            conn.rollback();
            conn.close();
            throw ex;
        }
    }

    private Map<Long, Object> getCriteria(String e) {
        Map<Long, Object> criteria = new HashMap<>();
// TODO: UTILIZAR SPLIT PARA QUEBRAR STRING
//        if(e.getName()!=null){
//            criteria.put(NAME_IL, e.getName());
//        }
//        if(e.getAdvertiser()!=null){
//            criteria.put(ADVERTISER_IL, e.getAdvertiser().getName());
//        }
//        if(e.getAdvertiser()!=null){
//            criteria.put(CATEGORY_IL, e.getCategory().getName());
//        }

        return criteria;
    }

    @Override
    public JAdvertiser getAdvertiserByEmail(String email) throws Exception {
        JAdvertiser advertiser;
        try (Connection conn = JConnectionDB.getInstance().getConnection()) {
            JAdvertiserDAO advertiserDAO = new JAdvertiserDAO();
            advertiser = advertiserDAO.getAdvertiserByEmail(conn, email);
        }

        return advertiser;
    }
}
