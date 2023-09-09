module Controller {
        requires javafx.controls;
        requires javafx.fxml;

        requires org.controlsfx.controls;
        requires org.kordamp.bootstrapfx.core;
        requires  com.microsoft.sqlserver.jdbc;
        requires java.sql;
        requires java.naming;
        requires java.net.http;

        opens Controller to javafx.fxml;
        opens Models to javafx.base;

        exports Controller;
}