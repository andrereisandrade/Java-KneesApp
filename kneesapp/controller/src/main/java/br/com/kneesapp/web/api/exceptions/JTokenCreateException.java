package br.com.kneesapp.web.api.exceptions;

import br.com.kneesapp.entity.JSecurity;

public class JTokenCreateException extends JOAuthException {

    public JSecurity segurancaAPI;

    public JTokenCreateException(JSecurity segurancaAPI) {
        super("Token ja criado para este usuario.");
        this.segurancaAPI = segurancaAPI;
    }

    public JSecurity getSegurancaAPI() {
        return segurancaAPI;
    }
}
