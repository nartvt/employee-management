package com.uit.common;

import com.uit.util.Utils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public final class StageCommon {

    public static FXMLLoader displayStage(final Stage stage, String scenneName, String title, int width, int height) {
        try {
            return renderStage(stage, scenneName, title, width, height);
        } catch (IOException e) {
            System.out.println("Stage show failure " + e.getMessage());
        }
        return null;
    }

    public static void hiddenStage(final Stage stage) {
        stage.close();
    }

    private static FXMLLoader renderStage(final Stage primaryStage, String scenneName, String title, int width, int height) throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Objects.requireNonNull(StageCommon.class.getClassLoader().getResource(scenneName)));
        final Parent root = fxmlLoader.load();
        final Scene scene = new Scene(root, width, height);
        primaryStage.sizeToScene();
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
        return fxmlLoader;
    }

    public static void styleCurrentStage(Button btnCurrentStage,
                                         List<Button> resetButton) {
        btnCurrentStage.setStyle(Utils.defaultHightlyCurrentStageColorStyle);
        btnCurrentStage.setTextFill(Utils.defaultHightlyCurrentStageTextFill);

        for(Button btn:resetButton){
            btn.setStyle(Utils.defaultStyleStageInit);
            btn.setTextFill(Utils.defaultTextFillStageInit);
        }
    }
}
