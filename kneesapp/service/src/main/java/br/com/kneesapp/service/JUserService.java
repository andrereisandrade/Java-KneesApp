package br.com.kneesapp.service;

import br.com.kneesapp.constants.ERole;
import br.com.kneesapp.dao.JRoleDAO;
import br.com.kneesapp.dao.JUserDAO;
import br.com.kneesapp.entity.JLogin;
import br.com.kneesapp.entity.JRole;
import br.com.kneesapp.entity.UserEntity;
import br.com.kneesapp.service.base.IUserService;
import br.com.kneesapp.sql.JConnectionDB;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andre
 */
public class JUserService implements IUserService {

    @Override
    public UserEntity findUserByEmail(String email) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserEntity login(JLogin login) throws Exception {
        Connection conn = JConnectionDB.getInstance().getConnection();
        UserEntity user;
        try {
            JUserDAO dao = new JUserDAO();
            user = dao.login(login, conn);
            conn.commit();
            conn.close();
        } catch (Exception ex) {
            conn.rollback();
            conn.close();
            throw ex;
        }
        return user;
    }

    public Integer getPermition(String email) throws Exception {
        Connection conn = JConnectionDB.getInstance().getConnection();
        JRoleDAO roleDAO = new JRoleDAO();
        List<JRole> roleList = roleDAO.getRoleByUser(email, conn);
        Integer roleId = null;
        for (JRole role : roleList) {
            if (role.getId() == ERole.PARTICIPANT_ID) {
                roleId = ERole.PARTICIPANT_ID;
                break;
            }
            if (role.getId() == ERole.ADVERTISER_ID) {
                roleId = ERole.ADVERTISER_ID;
                break;
            }
            if (role.getId() == ERole.ADMIN_ID) {
                roleId = ERole.ADMIN_ID;
                break;
            }
        }
        conn.close();
        return roleId;
    }

    @Override
    public void create(UserEntity e) throws Exception {
        Connection conn = JConnectionDB.getInstance().getConnection();
        try {
            JUserDAO userDAO = new JUserDAO();
            userDAO.create(conn, e);
            conn.commit();
            conn.close();
        } catch (Exception ex) {
            conn.rollback();
            conn.close();
            throw ex;
        }
    }

    @Override
    public UserEntity readById(Long id) throws Exception {
        UserEntity user;
        Connection conn = JConnectionDB.getInstance().getConnection();
        JUserDAO dao = new JUserDAO();
        user = dao.readById(conn, id);
        conn.commit();
        conn.close();
        return user;
    }

    @Override
    public List<UserEntity> readByCriteria(Integer page, Integer pagesize, String filter) throws Exception {
        List<UserEntity> eventList = new ArrayList<>();
        Map<Long, Object> mapCriteria = getCriteria(page, pagesize, filter);
        try {
            Connection conn = JConnectionDB.getInstance().getConnection();
            JUserDAO dao = new JUserDAO();
            eventList = dao.readByCriteria(conn, mapCriteria);
            conn.commit();
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(JParticipantService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return eventList;
    }

    @Override
    public void update(UserEntity e) throws Exception {
        Connection conn = JConnectionDB.getInstance().getConnection();
        try {
            JUserDAO dao = new JUserDAO();
            dao.update(conn, e);
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
            JUserDAO dao = new JUserDAO();
            dao.delete(conn, id);
            conn.commit();
            conn.close();
        } catch (Exception ex) {
            conn.rollback();
            conn.close();
            throw ex;
        }
    }

    private Map<Long, Object> getCriteria(Integer page, Integer pagesize, String e) {
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
    public void updateToken(String token) throws Exception {
        Connection conn = JConnectionDB.getInstance().getConnection();
        try {
            JUserDAO dao = new JUserDAO();
            dao.updateToken(conn, token);
            conn.commit();
            conn.close();
        } catch (Exception ex) {
            conn.rollback();
            conn.close();
            throw ex;
        }
    }

}
