package com.uit.controller;

import com.uit.Config;
import com.uit.EmployeeApplication;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;


public class SignInController implements Initializable {
    @FXML
    private TextField userName;

    @FXML
    private PasswordField password;

    @FXML
    private Label failureLogin;

    private Map<String,String> accountMaps;


    @FXML
    protected void onSignButtonClick() {
        validateLogin();
    }

    @FXML
    protected void onEnterPassWord() {
        password.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    validateLogin();
                }
            }
        });
    }

    private void validateLogin() {
        final String userNameValue = userName.getText().toString().trim();
        final String passwordValue = password.getText().toString().trim();
        if (userNameValue.equals("") || passwordValue.equals("")) {
            failureLogin.setText("User Name and Password not blank!");
            return;
        }

        String password =accountMaps.get(userNameValue);
        if (!passwordValue.equals(password)) {
            failureLogin.setText("Wrong userName or Passsword!");
            return;
        }
        failureLogin.setTextFill(Paint.valueOf("GREEN"));
        failureLogin.setFont(Font.font("Arial", 22));
        failureLogin.setText("Login Success!");
        EmployeeApplication.hiddenStage();
        renderStageMamagement();
    }

    private void renderStageMamagement() {
        ManagementController.renderStageManagement();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accountMaps = Config.getAccounts();
    }
}