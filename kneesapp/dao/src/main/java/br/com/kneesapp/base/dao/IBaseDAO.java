package br.com.kneesapp.base.dao;

import br.com.kneesapp.base.ABaseEntity;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author andre
 * @param <E>
 */
public interface IBaseDAO<E extends ABaseEntity> {

    void create(Connection conn, E e ) throws Exception;

    E readById(Connection conn, Long id) throws Exception;

    List<E> readByCriteria( Connection conn, Map<Long, Object> mapCriteria) throws Exception;

    void update( Connection conn, E e) throws Exception;

    void delete( Connection conn, Long id) throws Exception;
}
