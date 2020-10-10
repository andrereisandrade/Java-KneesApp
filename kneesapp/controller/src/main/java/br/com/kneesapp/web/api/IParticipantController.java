package br.com.kneesapp.web.api;

import br.com.kneesapp.entity.JParticipant;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author andre
 */
@RequestMapping(value = "/participante")
public interface IParticipantController extends IBaseCRUDController<JParticipant>{

}
