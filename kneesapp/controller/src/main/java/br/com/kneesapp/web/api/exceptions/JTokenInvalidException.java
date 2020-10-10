package br.com.kneesapp.web.api.exceptions;

public class JTokenInvalidException extends JOAuthException {

    public JTokenInvalidException(String message) {
        super(message);
    }

    public JTokenInvalidException(Throwable cause) {
        super(cause);
    }

}
