package br.com.kneesapp.web.api.imp;

import br.com.kneesapp.web.api.exceptions.JOAuthException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Superclasse de todo Controlador do sistema.
 */
public abstract class AOAuthController {


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true));
    }

    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ExceptionHandler(JOAuthException.class)
    @ResponseBody
    public String appException(HttpServletRequest req, Exception ex) {
        System.err.println("Request: " + req.getRequestURL() + " -> Lancou: " + ex);
        return "{mensagem: " + ex.getMessage() + "}";
    }
}
