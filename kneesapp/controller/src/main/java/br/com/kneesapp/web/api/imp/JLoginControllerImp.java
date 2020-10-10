package br.com.kneesapp.web.api.imp;

import br.com.kneesapp.entity.JLogin;
import br.com.kneesapp.web.api.ILoginController;
import br.com.kneesapp.web.api.security.JSecurityServiceValidate;
import com.google.gson.Gson;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author andre
 */
@CrossOrigin
@Controller
public class JLoginControllerImp extends AOAuthController implements ILoginController {

    public final Gson gson = new Gson();
    @Autowired
    public JSecurityServiceValidate securityService;

    @Override
    public ResponseEntity< String> logar(@RequestBody JLogin login) {
        HttpStatus httpStatus = HttpStatus.OK;
        String payload = "{}";
        try {
            OAuthResponse response = securityService.logarOAuth(login);
            switch (response.getResponseStatus()) {
                case HttpServletResponse.SC_OK:
                    payload = response.getBody();
                    httpStatus = HttpStatus.OK;
                    break;
                case HttpServletResponse.SC_UNAUTHORIZED:
                    payload = response.getBody();
                    httpStatus = HttpStatus.UNAUTHORIZED;
                    break;
                case HttpServletResponse.SC_BAD_REQUEST:
                    payload = response.getBody();
                    httpStatus = HttpStatus.BAD_REQUEST;
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            httpStatus = HttpStatus.BAD_REQUEST;
            payload = this.gson.toJson(ex.getMessage());
        }
        return ResponseEntity.status(httpStatus).body(payload);
    }

    @Override
    public String logout() {
        return "Geral: Acesso Permitido!";
    }

}
