package br.com.kneesapp.dao;

import br.com.kneesapp.base.dao.ICategoryDAO;
import static br.com.kneesapp.criteria.BaseCriteria.DESCRIPTION;
import static br.com.kneesapp.criteria.BaseCriteria.NAME;
import static br.com.kneesapp.criteria.CategoryCriteria.CATEGORY;
import static br.com.kneesapp.criteria.CategoryCriteria.CATEGORY_DESCRIPTION_IL;
import static br.com.kneesapp.criteria.CategoryCriteria.CATEGORY_NAME_IL;
import static br.com.kneesapp.criteria.EventCriteria.NAME_IL;
import br.com.kneesapp.entity.JCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author andre.andrade
 */
public class JCategoryDAO implements ICategoryDAO {

    @Override
    public void create(Connection conn, JCategory e) throws Exception {

        String sql = "INSERT INTO " + CATEGORY + " (" + NAME + "," + DESCRIPTION + ") VALUES(? , ?) returning id";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, e.getName());
        statement.setString(2, e.getDescricao());
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            System.out.println(rs.getLong("id"));
        }
        rs.close();
        statement.close();
    }

    @Override
    public JCategory readById(Connection conn, Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<JCategory> readByCriteria(Connection conn, Map<Long, Object> mapCriteria) throws Exception {
        String sql = "select * from categoria where 1=1";
        List<Object> paramList = new ArrayList<>();

        if (mapCriteria.containsKey(CATEGORY_NAME_IL)) {
            String name = (String) mapCriteria.get(CATEGORY_NAME_IL);
            sql += "AND name = ?";
            paramList.add(name);
        }
        
        
        if (mapCriteria.containsKey(CATEGORY_DESCRIPTION_IL)) {
            String name = (String) mapCriteria.get(CATEGORY_DESCRIPTION_IL);
            sql += "AND name = ?";
            paramList.add(name);
        }
        
         if (mapCriteria.containsKey(CATEGORY_DESCRIPTION_IL)) {
            String name = (String) mapCriteria.get(CATEGORY_DESCRIPTION_IL);
            sql += "AND name = ?";
            paramList.add(name);
        }

//        PreparedStatement statement = PreparedStatementBuild.build(conn, sql, paramList)
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Connection conn, JCategory e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Connection conn, Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
