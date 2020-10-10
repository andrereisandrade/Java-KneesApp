package br.com.kneesapp.web.api;

import br.com.kneesapp.entity.JAdvertiser;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author andre
 */
@RequestMapping(value = "/anunciante")
public interface IAdvertiserController extends IBaseCRUDController<JAdvertiser> {

    /**
     * http://localhost:8080/kneesapp/{E}/1
     *
     * @param email
     * @return
     */
    @RequestMapping(value = "/email", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getAdvertiserByEmail(@RequestBody String email);



}
