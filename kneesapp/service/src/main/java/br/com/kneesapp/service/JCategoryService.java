package br.com.kneesapp.service;

import br.com.kneesapp.dao.JCategoryDAO;
import br.com.kneesapp.entity.JCategory;
import br.com.kneesapp.service.base.ICategoryService;
import br.com.kneesapp.sql.JConnectionDB;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author andre
 */
public class JCategoryService implements ICategoryService {

    @Override
    public void create(JCategory category) throws Exception {
        Connection conn = JConnectionDB.getInstance().getConnection();
        try {
            JCategoryDAO categoryDAO = new JCategoryDAO();
            categoryDAO.create(conn, category);
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        }
        conn.close();
    }

    @Override
    public JCategory readById(Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<JCategory> readByCriteria(Map<Long , Object> mapCriteria) throws Exception {
        Connection conn = JConnectionDB.getInstance().getConnection();
        List<JCategory> categories = new ArrayList<>();
        JCategoryDAO categoryDAO = new JCategoryDAO();
        categories = categoryDAO.readByCriteria(conn, mapCriteria);
        return  categories;
    }
    
    @Override
    public List<JCategory> readByCriteria(Integer page, Integer pagesize, String filter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(JCategory e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
