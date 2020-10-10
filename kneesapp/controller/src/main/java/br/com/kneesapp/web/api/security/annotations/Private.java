package br.com.kneesapp.web.api.security.annotations;

import br.com.kneesapp.web.api.enuns.EPermission;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Private {

    public EPermission[] role();
    
}
