package br.com.kneesapp.web.api.imp;

import br.com.kneesapp.entity.EventEntity;
import br.com.kneesapp.entity.JAdvertiser;
import br.com.kneesapp.service.JEventService;
import br.com.kneesapp.web.api.IEventController;
import br.com.kneesapp.web.api.security.annotations.Public;
import com.google.gson.Gson;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author andre
 */
@CrossOrigin
@Controller
public class JEventControllerImp implements IEventController {

    JEventService eventService = new JEventService();
    public final Gson gson = new Gson();

    @Public
    @Override
    public Iterable<EventEntity> readByCriteria(Integer page, Integer pagesize, String filter) {
        Iterable<EventEntity> events = new ArrayList<>();
        try {
            events = eventService.readByCriteria(page, pagesize, filter);
        } catch (Exception ex) {
            System.out.println("Erro" + ex);
        }
        return events;
    }
    

    @Public
    //@Private(role = {EPermission.PARTICIPANTE, EPermission.ANUNCIANTE})
    @Override
    public ResponseEntity<String> readById(@PathVariable Long id) {
        HttpStatus httpStatus = HttpStatus.OK;
        String payload = "{}";
        EventEntity event;
        try {
            event = eventService.readById(id);
            if (event.getId() == null) {
                payload = this.gson.toJson(event);
                httpStatus = HttpStatus.BAD_REQUEST;
            } else {
                payload = this.gson.toJson(event);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return ResponseEntity.status(httpStatus).body(payload);
    }

    @Public
//    @Private(role = {EPermission.ANUNCIANTE})
    @Override
    public ResponseEntity<String> create(@RequestBody EventEntity entity) {
        try {
            eventService.create(entity);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @Public
//    @Private(role = {EPermission.ANUNCIANTE})
    @Override
    public ResponseEntity<String> update(EventEntity entity) {
        try {
            eventService.update(entity);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @Public
//    @Private(role = {EPermission.ANUNCIANTE})
    @Override
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            eventService.delete(id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @Public
//    @Private(role = {EPermission.ANUNCIANTE})
    @Override
    public ResponseEntity<String> saveImage(@RequestParam("file") MultipartFile image) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.saveImage(image));
    }

    @Override
    public Iterable<JAdvertiser> readByCriteria(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
