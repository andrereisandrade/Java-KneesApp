package br.com.kneesapp.dao;

import br.com.kneesapp.base.dao.IPathImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author andre.andrade
 */
public class PathImage implements IPathImage {

    @Override
    public String readPath(Connection conn) throws Exception {
        String sql = "select * from caminho_imagem where id = ?";
        String path = null;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, 1);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            path = rs.getString("name");
        }
        return path;
    }

}
