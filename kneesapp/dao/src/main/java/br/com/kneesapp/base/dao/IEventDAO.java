package br.com.kneesapp.base.dao;

import br.com.kneesapp.entity.EventEntity;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author andre
 */
public interface IEventDAO extends IBaseDAO<EventEntity>{
        
    List<EventEntity> readByAdvertiserId(Connection conn, Long id) throws Exception;
    
}
