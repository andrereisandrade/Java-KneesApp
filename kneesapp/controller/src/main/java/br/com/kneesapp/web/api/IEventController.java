package br.com.kneesapp.web.api;

import br.com.kneesapp.entity.EventEntity;
import br.com.kneesapp.entity.JAdvertiser;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author andre_andrade
 */
@RequestMapping(value = "/evento")
public interface IEventController extends IBaseCRUDController<EventEntity>{
    
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    ResponseEntity<String> saveImage(MultipartFile file);
    
        /**
     * http://localhost:8080/kneesapp/{E}/1
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/anunciante/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    Iterable<JAdvertiser> readByCriteria(@PathVariable Long id);
    
}
