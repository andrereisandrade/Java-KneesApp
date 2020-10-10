package br.com.kneesapp.web.api.exceptions;

public class JLoginInvalidException extends JOAuthException {

    public JLoginInvalidException(String message) {
        super(message);
    }

    public JLoginInvalidException(Throwable cause) {
        super(cause);
    }

}
