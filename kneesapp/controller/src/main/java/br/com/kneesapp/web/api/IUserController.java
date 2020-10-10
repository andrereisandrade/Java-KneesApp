package br.com.kneesapp.web.api;

import br.com.kneesapp.entity.UserEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author andre
 */
@RequestMapping(value = "/usuario")
public interface IUserController extends IBaseCRUDController<UserEntity> {

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    ResponseEntity<String> updateToken(@RequestBody String token);

    @RequestMapping(value = "/permition", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getPermitionByEmail(@RequestBody String email);

}
