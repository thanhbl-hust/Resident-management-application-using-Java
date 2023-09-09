package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class ThongKeNhanKhauController implements Initializable {
    @FXML
    ChoiceBox choiceBox;

    @FXML
    PieChart pieChart;

    @FXML
    Label caption;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        choiceBox.getItems().addAll("Theo giới tính", "Theo độ tuổi");

        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Theo giới tính"))
                printGenderPieChart();
            else
                printAgePieChart();
        });

    }

    private void printAgePieChart() {
        pieChart.getData().clear();
        try {
            int manNon = 0, tieuHoc = 0, thcs = 0, thpt = 0, tuoiLaoDong = 0, tuoiNghiHuu = 0;

            Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                    SQLController.PASSWORD);
            Statement stmt;
            stmt = conn.createStatement();
            String query = "SELECT COUNT(*) FROM dbo.NhanKhau WHERE YEAR(GETDATE()) - YEAR(NgaySinh) < 6";
            // System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                manNon = rs.getInt(1);
            }

            query = "SELECT COUNT(*) FROM dbo.NhanKhau WHERE (YEAR(GETDATE()) - YEAR(NgaySinh)) BETWEEN 6 AND 10";
            // System.out.println(query);
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                tieuHoc = rs.getInt(1);
            }

            query = "SELECT COUNT(*) FROM dbo.NhanKhau WHERE (YEAR(GETDATE()) - YEAR(NgaySinh)) BETWEEN 11 AND 14";
            // System.out.println(query);
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                thcs = rs.getInt(1);
            }

            query = "SELECT COUNT(*) FROM dbo.NhanKhau WHERE (YEAR(GETDATE()) - YEAR(NgaySinh)) BETWEEN 15 AND 17";
            // System.out.println(query);
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                thpt = rs.getInt(1);
            }

            query = "SELECT COUNT(*) FROM dbo.NhanKhau WHERE (GioiTinh = N'Nam' AND (YEAR(GETDATE()) - YEAR(NgaySinh)) BETWEEN 18 AND 60) OR (GioiTinh = N'Nữ' AND (YEAR(GETDATE()) - YEAR(NgaySinh)) BETWEEN 18 AND 55)";
            // System.out.println(query);
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                tuoiLaoDong = rs.getInt(1);
            }

            query = "SELECT COUNT(*) FROM dbo.NhanKhau WHERE (GioiTinh = N'Nam' AND (YEAR(GETDATE()) - YEAR(NgaySinh)) > 60) OR (GioiTinh = N'Nữ' AND (YEAR(GETDATE()) - YEAR(NgaySinh)) > 55)";
            // System.out.println(query);
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                tuoiNghiHuu = rs.getInt(1);
            }

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Mần non", manNon),
                    new PieChart.Data("Tiểu học", tieuHoc),
                    new PieChart.Data("THCS", thcs),
                    new PieChart.Data("THPT", thpt),
                    new PieChart.Data("Tuổi lao động", tuoiLaoDong),
                    new PieChart.Data("Tuổi nghỉ hưu", tuoiNghiHuu));

            pieChart.getData().addAll(pieChartData);
            pieChart.setLegendSide(Side.LEFT);

            caption.setVisible(false);
            caption.setTextFill(Color.RED);
            caption.setStyle("-fx-font: 24 arial;");

            for (final PieChart.Data data : pieChart.getData()) {
                data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                        new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent e) {
                                caption.setVisible(true);
                                System.out.println(e.getSceneY() + e.getSceneX());
                                caption.setTranslateX(e.getSceneX() - 50);
                                caption.setTranslateY(e.getSceneY() - 100);
                                caption.setText(String.valueOf(data.getPieValue()));
                            }
                        });
            }
        } catch (Exception ex) {
            // TODO: handle exception
        }
    }

    private void printGenderPieChart() {
        pieChart.getData().clear();
        try {
            int nam = 0, nu = 0, khac = 0;

            Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                    SQLController.PASSWORD);
            Statement stmt;
            stmt = conn.createStatement();
            String query = "SELECT COUNT(*) FROM dbo.NhanKhau WHERE GioiTinh = N'Nam'";
            // System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                nam = rs.getInt(1);
            }

            query = "SELECT COUNT(*) FROM dbo.NhanKhau WHERE GioiTinh = N'Nữ'";
            // System.out.println(query);
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                nu = rs.getInt(1);
            }

            query = "SELECT COUNT(*) FROM dbo.NhanKhau WHERE GioiTinh = N'Khac'";
            // System.out.println(query);
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                khac = rs.getInt(1);
            }

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Nam", nam),
                    new PieChart.Data("Nữ", nu),
                    new PieChart.Data("Khác", khac));

            pieChart.getData().addAll(pieChartData);
            pieChart.setLegendSide(Side.LEFT);

            caption.setVisible(false);
            caption.setTextFill(Color.RED);
            caption.setStyle("-fx-font: 24 arial;");

            for (final PieChart.Data data : pieChart.getData()) {
                data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                        new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent e) {
                                caption.setVisible(true);
                                System.out.println(e.getSceneY() + e.getSceneX());
                                caption.setTranslateX(e.getSceneX() - 50);
                                caption.setTranslateY(e.getSceneY() - 100);
                                caption.setText(String.valueOf(data.getPieValue()));
                            }
                        });
            }
        } catch (Exception ex) {
            // TODO: handle exception
        }
    }

}
