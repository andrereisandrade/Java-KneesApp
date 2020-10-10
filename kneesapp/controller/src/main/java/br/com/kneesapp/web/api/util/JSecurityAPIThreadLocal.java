package br.com.kneesapp.web.api.util;

import br.com.kneesapp.entity.JSecurity;

public class JSecurityAPIThreadLocal {

    private static final ThreadLocal<JSecurity> threadLocal = new ThreadLocal<>();

    public static void setSecurityAPI(JSecurity segurancaAPI) {
        threadLocal.remove();
        threadLocal.set(segurancaAPI);
    }

    public static JSecurity getSecurityAPI() {
        return threadLocal.get();
    }
}
