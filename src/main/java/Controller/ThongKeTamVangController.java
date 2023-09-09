package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class ThongKeTamVangController implements Initializable {

    @FXML
    BarChart<String, Number> barChart;

    @FXML
    CategoryAxis xAxis;

    @FXML
    NumberAxis yAxis;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        try {
            String arrays[] = new String[12];

            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM yyyy");
            for (int i = 0; i < 12; i++) {
                LocalDate month = today.minusMonths(i);
                arrays[11 - i] = month.format(formatter);

            }

            // Defining the x axis
            // CategoryAxis xAxis = new CategoryAxis();

            xAxis.setCategories(FXCollections.<String>observableArrayList(arrays));
            xAxis.setLabel("Tháng");
            // Defining the y axis
            // NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("Số lượng tạm vắng");

            // barChart = new BarChart<>()

            // barChart.setTitle("Comparison between various cars");
            XYChart.Series<String, Number> series1 = new XYChart.Series<>();
            series1.setName("Số lượng tạm vắng");

            Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                    SQLController.PASSWORD);
            Statement stmt = conn.createStatement();
            String query;
            ResultSet rs;
            for (int i = 0; i < 12; i++) {
                String year = arrays[i].substring(3);
                String month = arrays[i].substring(0, 3);
                query = "SELECT COUNT(*) FROM dbo.TamVang WHERE (MONTH(TuNgay) + YEAR(TuNgay)*100) <= (" + month +" + " + year + "*100) AND  (MONTH(DenNgay) + YEAR(DenNgay)*100) >= (" + month +" + " + year + "*100)";
                rs = stmt.executeQuery(query);
                System.out.println(year + " " + month);
                if (rs.next()) {
                    series1.getData().add(new XYChart.Data<>(arrays[i], rs.getInt(1)));
                }
            }
            conn.close();
            barChart.getData().add(series1);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}