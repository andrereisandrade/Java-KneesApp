package br.com.kneesapp.base.dao;

import br.com.kneesapp.entity.JAdvertiser;
import java.sql.Connection;

/**
 *
 * @author andre.andrade
 */
public interface IAdvertiserDAO extends IBaseDAO<JAdvertiser>{
    
     public JAdvertiser getAdvertiserByEmail(Connection conn, String email) throws Exception;
}
