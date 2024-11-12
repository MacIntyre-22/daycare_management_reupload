package com.example.daycaremanagement.scenes;

import com.example.daycaremanagement.AppConst;
import javafx.scene.Scene;

public class LoginPageScene extends Scene {

    public LoginPageScene(){
        super(new LoginPagePane(), AppConst.SCREEN_WIDTH, AppConst.SCREEN_HEIGHT);
    }
}
