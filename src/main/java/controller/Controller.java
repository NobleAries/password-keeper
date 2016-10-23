package controller;

import model.authentication.Authenticator;
import view.View;

public abstract class Controller {

    View view;
    Authenticator authenticator;

    public void setView(View view){
        this.view = view;
    }

    public void setModel(Authenticator authenticator){
        this.authenticator = authenticator;
    }
}
