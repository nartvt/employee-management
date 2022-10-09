package com.uit;

import com.uit.common.StageCommon;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeApplication extends Application {

    private static Stage stg;

    @Override
    public void start(@SuppressWarnings("exports") Stage primaryStage) throws IOException {
        stg = primaryStage;
        StageCommon.displayStage(stg,"signin.fxml","Quản Lý Dự Án", 600,400);

    }

    public static void hiddenStage() {
        StageCommon.hiddenStage(stg);
    }

    public static void main(String[] args) {
        launch(args);
    }

}