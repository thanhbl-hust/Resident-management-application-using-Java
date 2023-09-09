package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Models.NhanKhau;
import Models.TamTru;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/*
 * @author Vo Hoai Nam 4592
 * @version 1.0 11/2/2023
 * Class 136813, Teacher's name Trung.TT
 */
public class ThemTamTruController implements Initializable {
    @FXML
    TextField maNKField;

    @FXML
    Label alertLabel;

    @FXML
    Label hoTenLabel;

    @FXML
    TextField noiTamTruField;

    @FXML
    DatePicker tuNgayDatePicker;

    @FXML
    DatePicker denNgayDatePicker;

    @FXML
    TextField lydoField;

    @FXML
    Button saveButton;

    private TamTruController tamTruController;

    public TamTruController getTamTruController() {
        return tamTruController;
    }

    public void setTamTruController(TamTruController tamTruController) {
        this.tamTruController = tamTruController;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        tuNgayDatePicker.setValue(LocalDate.now());
        denNgayDatePicker.setValue(LocalDate.now());
        BooleanBinding ismaNKFieldEmpty = maNKField.textProperty().isEmpty();
        BooleanBinding isnoiTamTruFieldEmpty = noiTamTruField.textProperty().isEmpty();
        BooleanBinding islydoFieldEmpty = lydoField.textProperty().isEmpty();
        BooleanBinding areTextFieldEmpty = ismaNKFieldEmpty.or(isnoiTamTruFieldEmpty.or(islydoFieldEmpty));
        saveButton.disableProperty().bind(areTextFieldEmpty);

        maNKField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.length() == 8) {
                try {
                    Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                            SQLController.PASSWORD);
                    Statement stmt = conn.createStatement();
                    String query = "SELECT HoTen FROM NhanKhau WHERE MaNhanKhau = '" + newValue + "'";
                    // System.out.println(query);
                    ResultSet rs = stmt.executeQuery(query);

                    if (!rs.next()) {
                        alertLabel.setText("Mã Nhân khẩu sai");

                    } else {
                        alertLabel.setText("Mã Nhân khẩu đúng");
                        hoTenLabel.setText(rs.getNString(1));
                    }
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                alertLabel.setText("Mã Nhân khẩu có dạng: HK.xxxxx");
            }
        });

    }

    @FXML
    protected void submit(ActionEvent e) {
        TamTru tamTru;
        try {

            Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                    SQLController.PASSWORD);
            Statement stmt = conn.createStatement();
            String query = "SELECT TOP 1 ID FROM dbo.TamTru ORDER BY ID DESC";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String input = rs.getString(1);
            System.out.println(input);
            int dotIndex = input.indexOf(".");
            String prefix = input.substring(0, dotIndex + 1);
            int number = Integer.parseInt(input.substring(dotIndex + 1).trim()) + 1;
            String maTamTru = (prefix + String.format("%05d", number)).trim();
            System.out.println("ma tam tru" + maTamTru);
            tamTru = new TamTru(maTamTru, new NhanKhau(hoTenLabel.getText()), noiTamTruField.getText(),
                    tuNgayDatePicker.getValue(), denNgayDatePicker.getValue(), lydoField.getText());

            query = "INSERT INTO dbo.TamTru(ID,MaNhanKhau,NoiTamTru,TuNgay,DenNgay,LyDo) VALUES ('"
                    + maTamTru + "', '" + maNKField.getText() + "',N'" + noiTamTruField.getText() + "','"
                    + tuNgayDatePicker.getValue().toString() + "','" + denNgayDatePicker.getValue().toString() + "',N'"
                    + lydoField.getText() + "')";
            System.out.println(query);
            stmt.execute(query);
            conn.close();

            tamTruController.addList(tamTru);

        } catch (Exception ex) {
            ex.getStackTrace();
        }
        Alert infoAlert = new Alert(AlertType.INFORMATION);
        infoAlert.setHeaderText("Thêm Tạm Trú Thành Công");
        // infoAlert.setContentText("Tạo Nhân Khẩu Thành Công")
        infoAlert.showAndWait();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();

    }

}
