package br.com.kneesapp.web.api.exceptions;

public class JTokenExpirationException extends JOAuthException {

        public JTokenExpirationException(String message) {
                super(message);
        }

        public JTokenExpirationException(Throwable cause) {
                super(cause);
        }
        
}
