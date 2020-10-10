package br.com.kneesapp.web.api.security;

import br.com.kneesapp.entity.JLogin;
import br.com.kneesapp.entity.JRole;
import br.com.kneesapp.entity.JSecurity;
import br.com.kneesapp.entity.UserEntity;
import br.com.kneesapp.service.JSecurityService;
import br.com.kneesapp.service.JUserService;
import br.com.kneesapp.web.api.enuns.EPermission;
import br.com.kneesapp.web.api.exceptions.JTokenCreateException;
import br.com.kneesapp.web.api.exceptions.JTokenExpirationException;
import br.com.kneesapp.web.api.exceptions.JTokenInvalidException;
import br.com.kneesapp.web.api.exceptions.JLoginInvalidException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError.CodeResponse;
import org.apache.oltu.oauth2.common.error.OAuthError.ResourceResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import br.com.kneesapp.web.api.util.JDateUtils;
import br.com.kneesapp.web.api.util.JSecurityAPIThreadLocal;

/**
 * Contexto de seguranca.
 */
@Service
public class JSecurityServiceValidate {

    private final String APP_CLIENT_ID = "kneesappAPI";
    private final String APP_CLIENT_PASSWD = "9834ba657bb2c60b5bb53de6f4201905";

    public JUserService userService = new JUserService();
    public JSecurityService securityService = new JSecurityService();

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized UserEntity verifyValidateToken(HttpServletRequest request) throws JTokenExpirationException, JTokenInvalidException, Exception {
        UserEntity user = new UserEntity();
        try {
            OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.HEADER);
            String token = oauthRequest.getAccessToken();

            if (token.isEmpty()) {
                throw new JTokenInvalidException("Token vazio.");
            }

            JSecurity securityAPI = securityService.findByToken(token);
            if (securityAPI == null) {
                throw new JTokenInvalidException("Token invalido.");
            }

            user = securityAPI.getUser();
            if (user == null) {
                throw new JTokenInvalidException("Problema interno no retorno do usuario: nulo.");
            }

            if (securityAPI.getToken().contains(token)) {
                if (securityAPI.expiration()) {
                    securityAPI.tokenExpiration();
                    this.securityService.create(securityAPI);
                    throw new JTokenExpirationException("Token de acesso expirado. Gere um novo token e tente novamente.");
                } else {
                    JSecurityAPIThreadLocal.setSecurityAPI(securityAPI);
                }
            } else {
                throw new JTokenInvalidException("Token invalido. Tente novamente.");
            }
        } catch (OAuthProblemException e) {
            throw new JTokenInvalidException("Login invalido. Tente novamente.");
        } catch (OAuthSystemException ex) {
            throw new RuntimeException(ex);
        }

        return user;
    }

    private synchronized void updateToken(UserEntity user, String token, Date proximaDataExpiracao) throws JTokenInvalidException, JTokenExpirationException, JTokenCreateException {
        try {
            if (user == null) {
                throw new JTokenInvalidException("Problema interno ao criar token: usuario nulo.");
            }
            if (token.isEmpty()) {
                throw new JTokenInvalidException("Problema interno ao criar token: token vazio.");
            }
            if (proximaDataExpiracao == null) {
                throw new JTokenInvalidException("Problema interno ao criar token: proximaDataExpiracao nula.");
            }

            JSecurity securityAPI = securityService.findByUsuario(user);

            securityAPI = new JSecurity(token, proximaDataExpiracao, user);

            securityService.create(securityAPI);

            if (securityAPI.isSalvo()) {
                if (securityAPI.expiration()) {
                    throw new JTokenExpirationException("Token de acesso expirado. Gere um novo token e tente novamente.");
                } else {
                    throw new JTokenCreateException(securityAPI);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(JSecurityServiceValidate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Date returnNextExpirationDate() {
        Date agora = new Date();
        int day = JDateUtils.returnUnit(agora, JDateUtils.DAY);
        int month = JDateUtils.returnUnit(agora, JDateUtils.MONTH);
        int year = JDateUtils.returnUnit(agora, JDateUtils.YEAR);
        return JDateUtils.returnDate(day + 7 + "/" + month + "/" + year + " 23:59:59", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));
    }

    private void validateAccess(JLogin login) throws JTokenInvalidException {
        if (login.getClientId().isEmpty()) {
            throw new JTokenInvalidException("Atributo clientId nulo.");
        }
        if (login.getClientSecurity().isEmpty()) {
            throw new JTokenInvalidException("Atributo clientSecret nulo.");
        }
        if (!login.getClientId().equalsIgnoreCase(APP_CLIENT_ID) && !login.getClientSecurity().equalsIgnoreCase(APP_CLIENT_PASSWD)) {
            throw new JTokenInvalidException("Seguranca: aplicativo nao autorizado.");
        }
    }

    private UserEntity returnByUsernameAndPassword(JLogin login) throws JLoginInvalidException, Exception {
        UserEntity user = userService.login(login);
        if (user == null) {
            throw new JLoginInvalidException("Usuário não encontrado.");
        }

        return user;
    }

    private Integer verifyPermtion(String email) throws Exception {
        Integer role = userService.getPermition(email);
        if (role == null) {
            throw new JLoginInvalidException("Usuario não possui permissao.");
        }
        return role;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public OAuthResponse logarOAuth(JLogin login) throws OAuthProblemException, Exception {
        try {
            try {
                this.validateAccess(login);
            } catch (JTokenInvalidException e) {
                return this.returnError(HttpServletResponse.SC_UNAUTHORIZED, CodeResponse.UNAUTHORIZED_CLIENT, e);
            }

            try {
                login.setPermition(this.verifyPermtion(login.getEmail()));
            } catch (JLoginInvalidException e) {
                return this.returnError(HttpServletResponse.SC_UNAUTHORIZED, CodeResponse.UNAUTHORIZED_CLIENT, e);
            }

            UserEntity user = null;

            try {
                user = this.returnByUsernameAndPassword(login);
            } catch (JLoginInvalidException e) {
                return this.returnError(HttpServletResponse.SC_UNAUTHORIZED, CodeResponse.UNAUTHORIZED_CLIENT, e);
            }

            String accessToken = new OAuthIssuerImpl(new MD5Generator()).accessToken();
            Date nextDateExpiration = this.returnNextExpirationDate();

            try {
                this.updateToken(user, accessToken, nextDateExpiration);
            } catch (JTokenExpirationException e) {
                return this.returnError(HttpServletResponse.SC_UNAUTHORIZED, ResourceResponse.EXPIRED_TOKEN, e);
            } catch (JTokenInvalidException e) {
                return this.returnError(HttpServletResponse.SC_BAD_REQUEST, ResourceResponse.INVALID_TOKEN, e);
            } catch (JTokenCreateException e) {
                //token jah criado anteriormente, somente retorna.
                nextDateExpiration = e.getSegurancaAPI().getTokenExpiration();
            }

            return OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK).setAccessToken(accessToken).setExpiresIn(
                    changeNextExpirationDate(new Date(), nextDateExpiration))
                    .setParam("name", user.getName())
                    .setParam("email", user.getEmail())
                    .setParam("photo", user.getPhoto())
                    .setParam("id", String.valueOf(user.getId()))
                    .setParam("permition", String.valueOf(login.getPermition()))
                    .buildJSONMessage();

        } catch (OAuthProblemException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    public OAuthResponse returnError(int errorCode, String error, Exception e) {
        try {
            String descricao = e.getMessage();
            return OAuthASResponse.errorResponse(errorCode).setError(error + (descricao != null ? " - " + descricao : "")).setErrorDescription(descricao).buildJSONMessage();
        } catch (OAuthSystemException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String changeNextExpirationDate(Date now, Date next) {
        int hours = JDateUtils.getTimeDifferent(now, next);
        return "" + (hours * 60 * 60);
    }

    public JSecurity getUserLogged() throws JTokenInvalidException {
        JSecurity security = JSecurityAPIThreadLocal.getSecurityAPI();
        if (security == null) {
            throw new JTokenInvalidException("Usuário não logado.");
        } else {
            return security;
        }
    }

    public boolean validateRole(UserEntity user, EPermission[] roles) {

        for (EPermission configuredRole : roles) {
            for (JRole role : user.getPermitions()) {
                if (role.getName().equals(EPermission.ADMIN.toString()) || role.getName().equals(configuredRole.toString())) {
                    return true;
                }
            }
        }

        return false;
    }

}
