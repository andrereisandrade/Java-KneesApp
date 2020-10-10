package br.com.kneesapp.service.base;

import br.com.kneesapp.entity.JAdvertiser;

/**
 *
 * @author andre
 */
public interface IAdvertiserService extends IBaseCRUDService<JAdvertiser> {

    JAdvertiser getAdvertiserByEmail(String email) throws Exception;
}
