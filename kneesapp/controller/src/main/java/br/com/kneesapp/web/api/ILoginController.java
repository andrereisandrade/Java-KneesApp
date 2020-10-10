package br.com.kneesapp.web.api;

import br.com.kneesapp.entity.JLogin;
import br.com.kneesapp.web.api.enuns.EPermission;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import br.com.kneesapp.web.api.security.annotations.Public;
import br.com.kneesapp.web.api.security.annotations.Private;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author andre
 */
@Controller
public interface ILoginController {

    @Public
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity< String> logar(JLogin login);

    @Private(role = {EPermission.PARTICIPANTE, EPermission.ANUNCIANTE})
    @ResponseBody
    @RequestMapping(value = "/sair", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String logout();

}
