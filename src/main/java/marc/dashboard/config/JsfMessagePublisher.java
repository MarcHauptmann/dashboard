package marc.dashboard.config;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@ApplicationScoped
public class JsfMessagePublisher implements MessagePublisher {
    @Override
    public void sendMessage(String message) {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        facesContext.addMessage(null, new FacesMessage(message));
    }
}
