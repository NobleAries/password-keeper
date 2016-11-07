package controller;

import model.authentication.Authenticator;
import view.View;

public class Controller {

    View view;
    Authenticator authenticator;

    public void setView(View view){
        this.view = view;
    }

    public void setAuthenticator(Authenticator authenticator){
        this.authenticator = authenticator;
    }
}
