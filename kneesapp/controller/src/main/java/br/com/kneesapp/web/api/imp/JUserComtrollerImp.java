package br.com.kneesapp.web.api.imp;

import br.com.kneesapp.entity.UserEntity;
import br.com.kneesapp.service.JUserService;
import br.com.kneesapp.web.api.IUserController;
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
public class JUserComtrollerImp implements IUserController {

    private JUserService userService = new JUserService();
    public final Gson gson = new Gson();

    @Public
    @Override
    public Iterable<UserEntity> readByCriteria(Integer page, Integer pagesize, String filter) {
        Iterable<UserEntity> users = new ArrayList<>();

        try {
            users = userService.readByCriteria(page, pagesize, filter);
        } catch (Exception ex) {
            System.out.println("Erro" + ex);
        }
        return users;
    }

    @Override
    @Public
    public ResponseEntity<String> getPermitionByEmail(@RequestBody String email) {
        String permition;
        HttpStatus httpStatus = HttpStatus.OK;
        String payload = "{}";
        try {
            permition = String.valueOf(userService.getPermition(email));
            if (permition == null) {
                payload = this.gson.toJson(permition);
                httpStatus = HttpStatus.BAD_REQUEST;
            } else {
                payload = this.gson.toJson(permition);
            }
        } catch (Exception ex) {
            httpStatus = HttpStatus.BAD_REQUEST;
            payload = this.gson.toJson(ex.getMessage());
        }
        return ResponseEntity.status(httpStatus).body(payload);
    }

    @Public
    @Override
    public ResponseEntity<String> readById(Long id) {
        UserEntity user = new UserEntity();
        HttpStatus httpStatus = HttpStatus.OK;
        String payload = "{}";
        try {
            user = userService.readById(id);
            if (user.getId() == null) {
                payload = this.gson.toJson(user);
                httpStatus = HttpStatus.BAD_REQUEST;
            } else {
                payload = this.gson.toJson(user);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return ResponseEntity.status(httpStatus).body(payload);
    }

    @Public
    @Override
    public ResponseEntity<String> create(@RequestBody UserEntity entity) {
        try {
            userService.create(entity);
        } catch (Exception ex) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir");
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @Public
    @Override
    public ResponseEntity<String> update(@RequestBody UserEntity entity) {
        try {
            userService.update(entity);
        } catch (Exception ex) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar");
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @Public
    @Override
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            userService.delete(id);
        } catch (Exception ex) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao deeltar");
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @Public
    @Override
    public ResponseEntity<String> updateToken(@RequestBody String token) {
        try {
            userService.updateToken(token);
        } catch (Exception ex) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar token : " + ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

}
