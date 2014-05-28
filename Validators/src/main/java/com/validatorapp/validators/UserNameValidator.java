package com.validatorapp.validators;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("userNameValidator")
public class UserNameValidator implements Validator, Serializable {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        //A komponens id-je, amihez validátor van társítva
        String id = component.getId();
        
        //Komponens értéke
        String userName = value.toString();
        
        if(userName.equals("admin") || userName.equals("test")) {
            //Frissítjük a komponenshez csatolt üzenetet
            FacesMessage facesMsg = new FacesMessage("Username is taken");
            facesMsg.setSeverity(FacesMessage.SEVERITY_WARN);
            
            //Ha nem dob hibát, hibás form is elmenthető
            throw new ValidatorException(facesMsg);
        } else {
            
            //Frissítjük a komponenshez csatolt üzenetet a komponens id alapján
            String msg = "User name is valid!";
            FacesContext.getCurrentInstance().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_INFO,msg,msg));
        }
    }
    
}
