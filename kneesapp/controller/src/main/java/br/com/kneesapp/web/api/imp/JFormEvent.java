package br.com.kneesapp.web.api.imp;

import br.com.kneesapp.entity.EventResponse;
import javax.ws.rs.FormParam;

/**
 *
 * @author andre
 */
public class JFormEvent {

    @FormParam("event")
    private EventResponse event;
    @FormParam("file")
    private byte[] attachment;

    public EventResponse getEvent() {
        return event;
    }

    public void setEvent(EventResponse event) {
        this.event = event;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }
    
}
