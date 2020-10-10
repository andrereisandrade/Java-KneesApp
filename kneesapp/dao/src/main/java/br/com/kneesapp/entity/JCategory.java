package br.com.kneesapp.entity;

import br.com.kneesapp.base.ABaseEntity;

/**
 *
 * @author andre
 */
public class JCategory extends ABaseEntity{
    
    private String name;
    private String descricao;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    } 
    
}
