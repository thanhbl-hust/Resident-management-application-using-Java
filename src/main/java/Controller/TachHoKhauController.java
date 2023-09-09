package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Models.HoKhau;
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
public class TachHoKhauController implements Initializable {
    @FXML
    TextField maNKField;

    @FXML
    Label alertLabel;

    @FXML
    Label hoTenLabel;

    @FXML
    TextField diaChiHoField;

    @FXML
    Button saveButton;

    private HoKhau newHoKhau;
    private HoKhauController hoKhauController;

    public HoKhau getNewHoKhau() {
        return newHoKhau;
    }

    public void setNewHoKhau(HoKhau newHoKhau) {
        this.newHoKhau = newHoKhau;
    }

    public HoKhauController getHoKhauController() {
        return hoKhauController;
    }

    public void setHoKhauController(HoKhauController hoKhauController) {
        this.hoKhauController = hoKhauController;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        BooleanBinding ismaNKFieldEmpty = maNKField.textProperty().isEmpty();
        BooleanBinding isnoiTamTruFieldEmpty = diaChiHoField.textProperty().isEmpty();

        BooleanBinding areTextFieldEmpty = ismaNKFieldEmpty.or(isnoiTamTruFieldEmpty);
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
        try {
            Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                    SQLController.PASSWORD);
            Statement stmt = conn.createStatement();
            String query = "SELECT TOP 1 MaHoKhau FROM dbo.HoKhau ORDER BY MaHoKhau DESC";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String input = rs.getString(1);
            System.out.println(input);
            int dotIndex = input.indexOf(".");
            String prefix = input.substring(0, dotIndex + 1);
            int number = Integer.parseInt(input.substring(dotIndex + 1).trim()) + 1;
            String maHoKhau = (prefix + String.format("%05d", number)).trim();
            System.out.println("ma nhan khau" + maHoKhau);

            query = "DELETE FROM dbo.ThanhVienCuaHo WHERE MaNhanKhau = '" + maNKField.getText() + "'";
            stmt.execute(query);

            query = "INSERT INTO dbo.HoKhau (MaHoKhau, MaNKChuHo,Diachi) VALUES('"
                    + maHoKhau + "','" + maNKField.getText() + "', N'" + diaChiHoField.getText() + "')";
            stmt.execute(query);

            query = "SELECT CCCD FROM dbo.NhanKhau WHERE MaNhanKhau = '" + maNKField.getText() + "'";
            rs = stmt.executeQuery(query);
            rs.next();

            newHoKhau = new HoKhau(maHoKhau, hoTenLabel.getText(), maNKField.getText(), rs.getString(1),
                    diaChiHoField.getText());

            query = "INSERT INTO dbo.ThanhVienCuaHo(MaNhanKhau,MaHoKhau,QuanHeVoiCH,NoiThuongTruTruoc, MaTrongHoKhau)VALUES ('"
                    + maNKField.getText() + "', '" + maHoKhau + "',  N'Chủ hộ',N'" + "" + "'," + String.valueOf(1)
                    + ")";
            stmt.execute(query);

            hoKhauController.addList(newHoKhau);
            conn.close();
            Alert infoAlert = new Alert(AlertType.INFORMATION);
            infoAlert.setHeaderText("Tạo Nhân Khẩu Thành Công");
            infoAlert.setContentText("Bạn đã tạo thành công một Hộ Khẩu có mã " + maHoKhau);

            infoAlert.showAndWait();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    public TextField getMaNKField() {
        return maNKField;
    }

    public void setMaNKField(TextField maNKField) {
        this.maNKField = maNKField;
    }

    public Label getAlertLabel() {
        return alertLabel;
    }

    public void setAlertLabel(Label alertLabel) {
        this.alertLabel = alertLabel;
    }

    public Label getHoTenLabel() {
        return hoTenLabel;
    }

    public void setHoTenLabel(Label hoTenLabel) {
        this.hoTenLabel = hoTenLabel;
    }

    public TextField getDiaChiHoField() {
        return diaChiHoField;
    }

    public void setDiaChiHoField(TextField diaChiHoField) {
        this.diaChiHoField = diaChiHoField;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(Button saveButton) {
        this.saveButton = saveButton;
    }

}
