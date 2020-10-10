package br.com.kneesapp.service.base;

import br.com.kneesapp.base.ABaseEntity;
import java.util.List;
import java.util.Map;

/**
 *
 * @author andre
 * @param <E>
 */
public interface IBaseCRUDService<E extends ABaseEntity> {

    void create(E e) throws Exception;

    E readById(Long id) throws Exception;

    List<E> readByCriteria(Integer page, Integer pagesize, String filter) throws Exception;

    void update(E e) throws Exception;

    void delete(Long id) throws Exception;
}
