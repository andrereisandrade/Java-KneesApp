
import br.com.kneesapp.base.BaseEvent;
import static br.com.kneesapp.criteria.CategoryCriteria.CATEGORY_NAME_IL;
import static br.com.kneesapp.criteria.EventCriteria.LIMIT_IL;
import static br.com.kneesapp.criteria.EventCriteria.OFFSET_IL;
import br.com.kneesapp.entity.EventEntity;
import br.com.kneesapp.entity.JCategory;
import br.com.kneesapp.service.JCategoryService;
import br.com.kneesapp.service.JEventService;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author flavio.henrique
 */
public class Main {
    private static final String PATH_EVENT = "/home/andre/Faculdade/Sistema de Informação/3°Ano/Faitec/Code/servidor/kneesapp/controller/src/main/webapp/resources/img/events/";

    public static void main(String[] args) {
        Main main = new Main();
        main.listEvent();


    }

    public void listCategory() {
        Map<Long, Object> criteria = new HashMap<>();
        JCategoryService eventService = new JCategoryService();
        criteria.put(CATEGORY_NAME_IL, "Festança");
        criteria.put(LIMIT_IL, 10);
        criteria.put(OFFSET_IL, 0);
        try {
            List<JCategory> categories = eventService.readByCriteria(criteria);
            for (JCategory category : categories) {
                System.out.println("Nome: " + category.getName());
                System.out.println("Descrição: " + category.getDescricao());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createCategory() {

    }

    public void readEventById() {
        JEventService eventService = new JEventService();
        EventEntity event = new EventEntity();
        String criteria = "cidade:Santa";
//        String criteria = "categoria:CUL";
        //String criteria = "filter=nome:Bloco";
        try {

            event = eventService.readById(1L);
            System.out.println("Nome Evento: " + event.getName());
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listEvent() {
        
//        File f = new File(PATH_EVENT);
//        
//        URL urlArquivo = getClass().getClassLoader().getResource("resources");
//        System.out.println (urlArquivo.getPath());
        
//        System.out.println (urlArquivo.getPath());
        JEventService eventService = new JEventService();
        List<EventEntity> eventList = new ArrayList<>();
        String criteria = "endereco:Santa Rita";
//        String criteria = "categoria:CUL";
        //String criteria = "filter=nome:Bloco";
        try {
            eventList = eventService.readByCriteria(10, 0, criteria);
            for (BaseEvent event : eventList) {
                System.out.println("Nome Evento: " + event.getName() + "   ID:" + event.getId());
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
