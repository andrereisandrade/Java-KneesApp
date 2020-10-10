package br.com.kneesapp.service.base;

import br.com.kneesapp.entity.EventEntity;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author andre
 */
public interface IEventService extends IBaseCRUDService<EventEntity> {

    String saveImage(MultipartFile image);
    
    List<EventEntity>  readByAdvertiserId(Long id) throws Exception;
    

}
