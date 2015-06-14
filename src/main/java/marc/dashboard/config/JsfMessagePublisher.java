package marc.dashboard.config;

//@ApplicationScoped
public class JsfMessagePublisher implements MessagePublisher {
    @Override
    public void sendMessage(String message) {
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//
//        facesContext.addMessage(null, new FacesMessage(message));
    }
}
