package br.com.kneesapp.service;

import static br.com.kneesapp.criteria.EventCriteria.ADVERTISER_IL;
import static br.com.kneesapp.criteria.EventCriteria.CATEGORY_IL;
import static br.com.kneesapp.criteria.EventCriteria.LIMIT_IL;
import static br.com.kneesapp.criteria.EventCriteria.NAME_IL;
import br.com.kneesapp.entity.EventEntity;
import br.com.kneesapp.entity.JAdvertiser;
import br.com.kneesapp.entity.JCategory;
import br.com.kneesapp.service.util.JUtil;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Event;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static br.com.kneesapp.criteria.EventCriteria.ADDRESS_IL;

/**
 *
 * @author flavio.henrique
 */
public class JExternalEventService {

    public EventEntity getEventById(String id, String accessToken) {
        EventEntity event = new EventEntity();
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.LATEST);
        try {
            Event eventSearch = facebookClient.fetchObject(id, Event.class, Parameter.with("fields", "start_time,name,description,cover,attending_count,type,interested_count,place"));
            event = setEvent(eventSearch);
        } catch (Exception ex) {
            System.out.println("Error:" + ex.getMessage());
        }

        return event;
    }

    public List<EventEntity> getEventByCriteria(Map<Long, Object> mapCriteria, String accessToken) {
        List<EventEntity> eventList = new ArrayList<>();
        try {
            FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.LATEST);
            Connection<Event> eventSearch = facebookClient.fetchConnection("search", Event.class, getParameter(mapCriteria));
            eventList = mappExternalEventoToOrigEvents(eventSearch);
        } catch (Exception ex) {
            System.out.println("Error:" + ex.getMessage());
        }
        return eventList;
    }

    private List<EventEntity> mappExternalEventoToOrigEvents(Connection<Event> externalEvent) {
        List<EventEntity> origEventList = new ArrayList<>();
        try {
            externalEvent.forEach((extEvent) -> extEvent.forEach((event) -> origEventList.add(setEvent(event))));
        } catch (NumberFormatException ex) {
            System.out.println("Error" + ex.getMessage());
        }
        return origEventList;
    }

    private EventEntity setEvent(Event event) {

        EventEntity origEvent = new EventEntity();

        if (event.getPlace() != null && event.getPlace().getLocation() != null) {
            //JAddress jAddress = new JAddress();
//            try {
//                String address[] = event.getPlace().getLocation().getStreet().split(",");
//                for (int i = 0; i < address.length; i++) {
//                    switch (i) {
//                        case 0:
//                            origEvent.setStreet(JUtil.removeIfNull(address[i]));
//                            break;
//                        case 1:
//                            origEvent.setNumber(JUtil.removeIfNull(address[i]));
//                            break;
//                        case 2:
//                            origEvent.setNeighborhood(JUtil.removeIfNull(address[i]));
//                            break;
//                    }
//                }
//
//            } catch (Exception e) {
//                System.out.println("Incomplete Address: " + e.getMessage());
//            }
//
//            origEvent.setCep(event.getPlace().getLocation().getZip());
//            origEvent.setState(event.getPlace().getLocation().getState());
//            origEvent.setCityName(event.getPlace().getLocation().getCity());

            origEvent.setAddress(event.getPlace().getLocation().getStreet() + "," + event.getPlace().getLocation().getCity() + "," + event.getPlace().getLocation().getState());
            origEvent.setLongitude(String.valueOf(event.getPlace().getLocation().getLongitude()));
            origEvent.setLatitude(String.valueOf(event.getPlace().getLocation().getLatitude()));
        }

        if (event.getCategory() != null) {
            JCategory jcategory = new JCategory();
            jcategory.setName(JUtil.removeIfNull(event.getCategory()));
            origEvent.setCategory(jcategory);
        }

        if (event.getOwner() != null) {
            JAdvertiser jAdvertiser = new JAdvertiser();
            jAdvertiser.setName(event.getOwner().getName());
            origEvent.setAdvertiser(jAdvertiser);
        }

        origEvent.setId(Long.parseLong(event.getId()));
        origEvent.setName(event.getName());
        origEvent.setDate(event.getStartTime());

        origEvent.setDescription(event.getDescription());
        origEvent.setPhoto(event.getCover().getSource());
        origEvent.setExternalEvent(true);

        return origEvent;
    }

    private Parameter[] getParameter(Map<Long, Object> mapCriteria) {
        int i = 0;
        Parameter parameter[] = new Parameter[mapCriteria.size() + 1];

        parameter[i++] = Parameter.with("type", "event");

        if (mapCriteria.containsKey(NAME_IL)) {
            parameter[i++] = Parameter.with("q", mapCriteria.get(NAME_IL));
        } else if (mapCriteria.containsKey(ADDRESS_IL)) {
            parameter[i++] = Parameter.with("q", mapCriteria.get(ADDRESS_IL));
        } else if (mapCriteria.containsKey(CATEGORY_IL)) {
            parameter[i++] = Parameter.with("q", mapCriteria.get(CATEGORY_IL));
        } else if (mapCriteria.containsKey(ADVERTISER_IL)) {
            parameter[i++] = Parameter.with("q", mapCriteria.get(ADVERTISER_IL));
        }

        if (mapCriteria.containsKey(LIMIT_IL)) {
            parameter[i++] = Parameter.with("limit", mapCriteria.get(LIMIT_IL));
        }
        parameter[i++] = Parameter.with("fields", "start_time,name,description,cover,attending_count,type,interested_count,place");
        return parameter;
    }
}
