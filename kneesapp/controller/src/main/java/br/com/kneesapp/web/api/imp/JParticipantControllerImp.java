package br.com.kneesapp.web.api.imp;

import br.com.kneesapp.entity.JParticipant;
import br.com.kneesapp.service.JParticipantService;
import br.com.kneesapp.service.JUserService;
import br.com.kneesapp.web.api.IParticipantController;
import br.com.kneesapp.web.api.security.annotations.Public;
import com.google.gson.Gson;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author andre
 */
@CrossOrigin
@Controller
public class JParticipantControllerImp implements IParticipantController {

    JParticipantService participantService = new JParticipantService();
    JUserService userService = new JUserService();
    public final Gson gson = new Gson();

    @Public
    @Override
    public Iterable<JParticipant> readByCriteria(Integer page, Integer pagesize, String filter) {

        Iterable<JParticipant> participants = new ArrayList<>();

        try {
            participants = participantService.readByCriteria(page, pagesize, filter);
        } catch (Exception ex) {
            System.out.println("Erro" + ex);
        }
        return participants;
    }

    @Public
    //@Private(role = {EPermission.PARTICIPANTE})
    @Override
    public ResponseEntity<String> readById(@PathVariable Long id) {
        HttpStatus httpStatus = HttpStatus.OK;
        String payload = "{}";
        JParticipant participant;
        try {
            participant = participantService.readById(id);
            if (participant.getId() == null) {
                payload = this.gson.toJson(participant);
                httpStatus = HttpStatus.BAD_REQUEST;
            } else {
                payload = this.gson.toJson(participant);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return ResponseEntity.status(httpStatus).body(payload);
    }

    @Public
    // @Private(role = {EPermission.PARTICIPANTE})
    @Override
    public ResponseEntity<String> create(@RequestBody JParticipant participant) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        String payload = "{}";

        try {
            participantService.create(participant);
            payload = this.gson.toJson(participant);
        } catch (Exception ex) {
            httpStatus = HttpStatus.BAD_REQUEST;
            payload = this.gson.toJson(ex.getMessage());
        }
        return ResponseEntity.status(httpStatus).body(payload);
    }

    @Public
    //@Private(role = {EPermission.PARTICIPANTE})
    @Override
    public ResponseEntity<String> update(@RequestBody JParticipant participant
    ) {
        String payload = "{}";
        try {
            participantService.update(participant);
            payload = this.gson.toJson(participant);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar");
        }
        return ResponseEntity.status(HttpStatus.OK).body(payload);
    }

    @Public
    //@Private(role = {EPermission.PARTICIPANTE})
    @Override
    public ResponseEntity<String> delete(@PathVariable Long id
    ) {
        try {
            participantService.delete(id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao deletar");
        }
        return ResponseEntity.status(HttpStatus.OK).body("{}");
    }

}
