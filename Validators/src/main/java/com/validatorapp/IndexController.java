package com.validatorapp;

import com.validatorapp.entity.UserData;
import com.validatorapp.service.UserService;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("indexController")
@ViewScoped
public class IndexController implements Serializable {

    private String userName;
    private UserData data;
    private String password;
    private boolean validationError;
    
    //EL kifejezéssel adjuk meg a vlidátort (pl.:tábla mezőjében tárolva DB-ben), nem pedig beégetve
    private String validator;
    
    @Inject
    private UserService service;

    public IndexController() {
        this.userName = "";
        this.data = new UserData();
        this.validator = "userNameValidator";
    }
    
    //Ajaxos validáció index2.xhtml oldalról
    public void checkUserName(AjaxBehaviorEvent event) {
        //Beviteli mező id-je, aminek listenerje van
        String id = (String) ((UIOutput) event.getSource()).getId();
        //beviteli mező értéke
        String value = ((UIOutput) event.getSource()).getValue().toString();
        
        //Validációs hiba
        validationError = false;
        if(value.equals("admin") || value.equals("test")) {
            String msg = "User name ["+value+"] already in use!";
            
            //Frissítjük a komponenshez csatolt üzenetet a komponens id alapján
            FacesContext.getCurrentInstance().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_WARN,msg,msg));
            
            //Volt validációs hiba
            validationError = true;
        } else {
            String msg = "User name is valid!";
            FacesContext.getCurrentInstance().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_INFO,msg,msg));
        }
    }
    
    //Form mentése ajax validációval index2.xhtml
    public void save() {
        data.setPassword(password);
        data.setUserName(userName);
        if(!validationError) {
            //Ha nincs validációs hiba, mentünk
            if(service.save(data)) {
                FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_INFO,"Save","Success"));
            } else {
                FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Save","Error"));
            }
            data = new UserData();
        } else {
            //Validációs hiba esetén hibaüzenet
            FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Save","There are validation errors on the page!"));
        }
    }
    
    //Form mentése saját validátorral index.xhtml
    public void save2() {
       data.setPassword(password);
       data.setUserName(userName); 
       if(service.save(data)) {
                FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_INFO,"Save","Success"));
        } else {
            FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Save","Error"));
        }
        data = new UserData();
    }

    public String getValidator() {
        return validator;
    }
    

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
