package br.com.kneesapp.web.api.imp;

import br.com.kneesapp.entity.JAdvertiser;
import br.com.kneesapp.service.JAdvertiserService;
import br.com.kneesapp.service.JEventService;
import br.com.kneesapp.service.JUserService;
import br.com.kneesapp.web.api.IAdvertiserController;
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
public class JAdvertiserControllerImp implements IAdvertiserController {

    JAdvertiserService advertiserService = new JAdvertiserService();
    JEventService eventService = new JEventService();
    JUserService userService = new JUserService();
    public final Gson gson = new Gson();

    @Public
    @Override
    public Iterable<JAdvertiser> readByCriteria(Integer page, Integer pagesize, String filter) {
        Iterable<JAdvertiser> advertiseres = new ArrayList<>();
        try {
            advertiseres = advertiserService.readByCriteria(page, pagesize, filter);
        } catch (Exception ex) {
            System.out.println("Erro" + ex);
        }
        return advertiseres;
    }

    @Public
    @Override
    public ResponseEntity<String> readById(@PathVariable Long id) {
        HttpStatus httpStatus = HttpStatus.OK;
       String payload;
        JAdvertiser adevertiser = null;
        try {
            adevertiser = advertiserService.readById(id);
            if (adevertiser.getId() == null) {
                payload = this.gson.toJson(adevertiser);
                httpStatus = HttpStatus.BAD_REQUEST;
            } else {
                payload = this.gson.toJson(adevertiser);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return ResponseEntity.status(httpStatus).body(payload);

    }

    @Public
    @Override
    public ResponseEntity<String> create(@RequestBody JAdvertiser entity) {
        String payload;
        try {
            advertiserService.create(entity);
            payload = this.gson.toJson(entity);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"" + ex.toString() + "\"}");
        }
        return ResponseEntity.status(HttpStatus.OK).body(payload);
    }

    @Public
    @Override
    public ResponseEntity<String> update(@RequestBody JAdvertiser entity) {
        HttpStatus httpStatus = HttpStatus.OK;
        String payload;
        try {
            advertiserService.update(entity);
            payload = this.gson.toJson(entity);
        } catch (Exception ex) {
            httpStatus = HttpStatus.BAD_REQUEST;
            payload = this.gson.toJson(ex.getMessage());
        }
        return ResponseEntity.status(httpStatus).body(payload);
    }

    @Public
    @Override
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            advertiserService.delete(id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("{}");
    }

    @Public
    @Override
    public ResponseEntity<String> getAdvertiserByEmail(@RequestBody String email) {
         String payload;
         HttpStatus httpStatus = HttpStatus.OK;
        try {
            JAdvertiser advertiser = advertiserService.getAdvertiserByEmail(email);
             payload = this.gson.toJson(advertiser);
        } catch (Exception ex) {
            payload = this.gson.toJson(ex.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(httpStatus).body(payload);
    }
}
