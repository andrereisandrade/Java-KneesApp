package br.com.kneesapp.service;

import br.com.kneesapp.dao.JEventDAO;
import br.com.kneesapp.dao.JUserDAO;
import br.com.kneesapp.entity.EventEntity;
import br.com.kneesapp.service.base.IEventService;
import br.com.kneesapp.service.util.JUtil;
import br.com.kneesapp.sql.JConnectionDB;
import java.io.BufferedOutputStream;
import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author andre
 */
public class JEventService implements IEventService {

    @Override
    public void create(EventEntity event) throws Exception {
        JEventDAO dao = new JEventDAO();
        Connection conn = JConnectionDB.getInstance().getConnection();

        try {
            event.setPhoto(JUtil.setImageEventIfIsNull(event.getPhoto()));
            dao.create(conn, event);
            conn.commit();
            conn.close();
        } catch (Exception ex) {
            conn.rollback();
            conn.close();
            throw ex;
        }
    }

    @Override
    public EventEntity readById(Long id) throws Exception {
        JEventDAO dao = new JEventDAO();
        JUserDAO userDAO = new JUserDAO();
        JExternalEventService externalEventService = new JExternalEventService();
        Connection conn = JConnectionDB.getInstance().getConnection();
        EventEntity event = dao.readById(conn, id);
        if (event == null) {
            event = externalEventService.getEventById(String.valueOf(id), userDAO.getToken(conn));
        }
        conn.close();
        return event;
    }

    @Override
    public List<EventEntity> readByCriteria(Integer page, Integer pagesize, String filter) throws Exception {
        JEventDAO eventDAO = new JEventDAO();
        JUserDAO userDAO = new JUserDAO();
        Map<Long, Object> mapCriteria = JUtil.getCriteria(page, pagesize, filter);

        Connection conn = JConnectionDB.getInstance().getConnection();
        JExternalEventService externalEventService = new JExternalEventService();

        List<EventEntity> eventList = eventDAO.readByCriteria(conn, mapCriteria);

        eventList.addAll(externalEventService.getEventByCriteria(mapCriteria, userDAO.getToken(conn)));
        conn.close();
        return eventList;
    }

    @Override
    public void update(EventEntity e) throws Exception {
        JEventDAO dao = new JEventDAO();
        Connection conn = JConnectionDB.getInstance().getConnection();
        try {
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
        JEventDAO dao = new JEventDAO();
        Connection conn = JConnectionDB.getInstance().getConnection();
        try {
            dao.delete(conn, id);
            conn.commit();
            conn.close();
        } catch (Exception ex) {
            conn.rollback();
            conn.close();
            throw ex;
        }
    }

    @Override
    public String saveImage(MultipartFile image) {
        String result;
        try {
            BufferedOutputStream stream;
            stream = JUtil.getFile(image.getOriginalFilename());
            stream.write(image.getBytes());
            result = "Sucess";
        } catch (IOException e) {
            result = "Falha" + e.getMessage();
        } catch (Exception ex) {
            result = "Falha" + ex.getMessage();
        }
        return result;
    }

    @Override
    public List<EventEntity> readByAdvertiserId(Long id) throws Exception {
        JEventDAO dao = new JEventDAO();
        List<EventEntity> eventList = new ArrayList<>();
        Connection conn = JConnectionDB.getInstance().getConnection();
        try {
            eventList = dao.readByAdvertiserId(conn, id);
        } catch (SQLException ex) {
            Logger.getLogger(JEventService.class.getName()).log(Level.SEVERE, null, ex);
        }
        conn.close();
        return eventList;
    }

}
