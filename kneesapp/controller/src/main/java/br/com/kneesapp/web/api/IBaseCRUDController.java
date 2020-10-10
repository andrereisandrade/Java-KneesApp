package br.com.kneesapp.web.api;

import br.com.kneesapp.base.ABaseEntity;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author andre
 * @param <E>
 */
@Controller
public interface IBaseCRUDController<E extends ABaseEntity> {

    /**
     *http://localhost:8080/kneesapp/{E}/lista/?page=10&pagesize=1&filter=categoria:AR
     *
     * @param page
     * @param pagesize
     * @param filter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/lista/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    Iterable<E> readByCriteria(@DefaultValue(value = "0") @QueryParam(value="page") Integer page, @DefaultValue(value = "100") @QueryParam(value="pagesize") Integer pagesize, @QueryParam(value="filter") String filter);

    /**
     * http://localhost:8080/kneesapp/{E}/1
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> readById(@PathVariable Long id);

    /**
     * http://localhost:8080/kneesapp/{E}/1
     *
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> create(@RequestBody E entity);

    /**
     * http://localhost:8080/kneesapp/
     *
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> update(@RequestBody E entity);

    /**
     * http://localhost:8080/kneesapp/{E}/1
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> delete(@PathVariable Long id);
}
