package br.com.kneesapp.web.api.security;

import br.com.kneesapp.entity.UserEntity;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import br.com.kneesapp.web.api.security.annotations.Public;
import br.com.kneesapp.web.api.security.annotations.Private;

public class JSecurityInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JSecurityServiceValidate securityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod met = (HandlerMethod) handler;

            Public publicAnnotation = met.getMethodAnnotation(Public.class);
            if (publicAnnotation != null) {
                return true;
            }

            Private privateAnnotation = met.getMethodAnnotation(Private.class);
            if (privateAnnotation != null) {
                UserEntity user = securityService.verifyValidateToken(request);

                if (securityService.validateRole(user, privateAnnotation.role())) {
                    return true;
                }
            }

            return this.accessDenied(response);
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    private boolean accessDenied(HttpServletResponse response) throws IOException {
        response.getWriter().println("Sem acesso ao recurso.");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return false;
    }

}
