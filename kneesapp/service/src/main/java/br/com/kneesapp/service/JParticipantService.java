package br.com.kneesapp.service;

import br.com.kneesapp.constants.ERole;
import br.com.kneesapp.dao.JParticipantDAO;
import br.com.kneesapp.dao.JRoleDAO;
import br.com.kneesapp.dao.JUserDAO;
import br.com.kneesapp.entity.JParticipant;
import br.com.kneesapp.entity.UserEntity;
import br.com.kneesapp.service.base.IParticpantService;
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
public class JParticipantService implements IParticpantService {

    @Override
    public void create(JParticipant participant) throws Exception {
        Connection conn = JConnectionDB.getInstance().getConnection();
        try {
            JUserDAO userDAO = new JUserDAO();
            JParticipantDAO participantDAO = new JParticipantDAO();
            JRoleDAO roleDAO = new JRoleDAO();

            // If doesn't exsist the user then create it
            UserEntity user = userDAO.findUserByEmail(participant.getEmail(), conn);
            if (user.getEmail() == null) {
                userDAO.create(conn, participant);
            }
            participant.setPhoto(JUtil.setImageProfileIfIsNull(participant.getPhoto()));
            participantDAO.create(conn, participant);
            roleDAO.setUserRole(participant.getEmail(), ERole.PARTICIPANT_ID, conn);

            conn.commit();
            conn.close();
        } catch (Exception ex) {
            conn.rollback();
            conn.close();
            throw ex;
        }
    }

    @Override
    public JParticipant readById(Long id) throws Exception {
        JParticipant event;
        try (Connection conn = JConnectionDB.getInstance().getConnection()) {
            JParticipantDAO dao = new JParticipantDAO();
            event = dao.readById(conn, id);
            conn.commit();
            conn.close();
        }
        return event;
    }

    @Override
    public List<JParticipant> readByCriteria(Integer page, Integer pagesize, String filter) throws Exception {
        List<JParticipant> eventList = new ArrayList<>();
        Map<Long, Object> mapCriteria = getCriteria(filter);
        try {
            Connection conn = JConnectionDB.getInstance().getConnection();
            JParticipantDAO dao = new JParticipantDAO();
            eventList = dao.readByCriteria(conn, mapCriteria);
            conn.commit();
            conn.close();
        } catch (Exception ex) {
            throw ex;
        }
        return eventList;
    }

    @Override
    public void update(JParticipant e) throws Exception {
        Connection conn = JConnectionDB.getInstance().getConnection();
        try {
            JParticipantDAO dao = new JParticipantDAO();
            JUserDAO userDAO = new JUserDAO();

            userDAO.update(conn, e);
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
            JParticipantDAO dao = new JParticipantDAO();
            dao.delete(conn, id);
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
}
