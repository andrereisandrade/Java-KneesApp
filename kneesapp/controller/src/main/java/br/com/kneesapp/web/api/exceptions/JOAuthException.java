package br.com.kneesapp.web.api.exceptions;

public class JOAuthException extends RuntimeException {

    public JOAuthException() {
        super();
    }

    public JOAuthException(String message) {
        super(message);
    }

    public JOAuthException(Throwable cause) {
        super(cause);
    }

}
