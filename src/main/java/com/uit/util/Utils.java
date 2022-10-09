package com.uit.util;

import javafx.scene.control.Label;
import javafx.scene.paint.Paint;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class Utils {

    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public final static String defaultHightlyCurrentStageColorStyle = "-fx-background-color:#ffffff";
    public final static String defaultStyleStageInit = "-fx-background-color: #0a4969";

    public static final int defaultLimit = 20;
    public static final int defaultPage = 1;

    public final static Paint defaultHightlyCurrentStageTextFill = Paint.valueOf("#000000");
    public final static Paint defaultTextFillStageInit = Paint.valueOf("#ffffff");


    public static LocalDate convertFromText(String date) {
        return LocalDate.parse(date, formatter);
    }

    public static String convertFromLocalDate(LocalDate date) {
        return formatter.format(date);
    }

    public static Timestamp getLocalDateFromString(String date) {
        LocalDate localDate = LocalDate.parse(date, formatter);
        return Timestamp.valueOf(localDate.atStartOfDay());
    }

    public static void renderFailureMessage(Label alertMessage, String message, Paint color) {
        alertMessage.setTextFill(color);
        alertMessage.setText(message);
    }
}
