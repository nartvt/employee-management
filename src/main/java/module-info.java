module com.uit {
    requires javafx.controls;
    requires javafx.web;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
	requires commons.csv;

    opens com.uit to javafx.fxml;
    exports com.uit;
    exports com.uit.controller;
    opens com.uit.controller to javafx.fxml;
    exports com.uit.util;
    opens com.uit.util to javafx.fxml;
    exports com.uit.entity;
    opens com.uit.entity to javafx.fxml;
    exports com.uit.repository;
    opens com.uit.repository to javafx.fxml;
}