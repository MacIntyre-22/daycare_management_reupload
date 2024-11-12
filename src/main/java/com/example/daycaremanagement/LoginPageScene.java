package com.example.daycaremanagement;

import com.example.daycaremanagement.pages.LoginPagePane;
import javafx.scene.Scene;

public class LoginPageScene extends Scene {

    public LoginPageScene(){
        super(new LoginPagePane(), AppConst.SCREEN_WIDTH, AppConst.SCREEN_HEIGHT);
    }
}
