package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Models.HoKhau;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/*
 * @author Vo Hoai Nam 4592
 * @version 1.0 11/2/2023
 * Class 136813, Teacher's name Trung.TT
 */
public class ThemHoKhauController implements Initializable {

    @FXML
    TextField hoTenField;

    @FXML
    DatePicker ngaySinhDatePicker;

    @FXML
    TextField cMNField;

    @FXML
    TextField queQuanField;

    @FXML
    TextField thuongTruField;

    @FXML
    ChoiceBox gioiTinBox;

    @FXML
    ChoiceBox danTocBox;

    @FXML
    TextField ngheNghiepField;

    @FXML
    TextField diaChiHoField;

    @FXML
    Button saveButton;

    private HoKhau newHoKhau;
    private HoKhauController hoKhauController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ngaySinhDatePicker.setValue(LocalDate.now());

        BooleanBinding ismaHoKhauFieldEmpty = thuongTruField.textProperty().isEmpty();
        BooleanBinding ishohoTenFieldEmpty = hoTenField.textProperty().isEmpty();
        BooleanBinding iscMNFieldEmpty = cMNField.textProperty().isEmpty();
        BooleanBinding areTextFieldsEmpty = ismaHoKhauFieldEmpty.or(ishohoTenFieldEmpty).or(iscMNFieldEmpty);

        saveButton.disableProperty().bind(areTextFieldsEmpty);
    }

    public HoKhauController getHoKhauController() {
        return hoKhauController;
    }

    public void setHoKhauController(HoKhauController hoKhauController) {
        this.hoKhauController = hoKhauController;
    }

    @FXML
    protected void Submit(ActionEvent e) {
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

            stmt = conn.createStatement();
            query = "SELECT TOP 1 MaNhanKhau FROM dbo.NhanKhau ORDER BY MaNhanKhau DESC";
            rs = stmt.executeQuery(query);
            rs.next();
            input = rs.getString(1);
            System.out.println(input);
            dotIndex = input.indexOf(".");
            prefix = input.substring(0, dotIndex + 1);
            number = Integer.parseInt(input.substring(dotIndex + 1).trim()) + 1;
            String maNhanKhau = (prefix + String.format("%05d", number)).trim();
            System.out.println("ma nhan khau" + maNhanKhau);

            query = "INSERT INTO dbo.NhanKhau (MaNhanKhau, HoTen, CCCD, NgaySinh, GioiTinh, QueQuan, ThuongTru, Dantoc, NgheNghiep) VALUES ( '"
                    + maNhanKhau + "', N'" + hoTenField.getText() + "', '" + cMNField.getText() + "', '"
                    + ngaySinhDatePicker.getValue().toString() + "', N'"
                    + gioiTinBox.getValue().toString() + "', N'" + queQuanField.getText() + "', N'"
                    + thuongTruField.getText() + "',N'"
                    + danTocBox.getValue().toString() + "', N'" + ngheNghiepField.getText() + "')";
            stmt.execute(query);

            query = "INSERT INTO dbo.HoKhau (MaHoKhau, MaNKChuHo,Diachi) VALUES('"
                    + maHoKhau + "','" + maNhanKhau + "', N'" + diaChiHoField.getText() + "')";
            stmt.execute(query);
            newHoKhau = new HoKhau(maHoKhau, hoTenField.getText(), maNhanKhau, cMNField.getText(),
                    thuongTruField.getText());

            query = "INSERT INTO dbo.ThanhVienCuaHo(MaNhanKhau,MaHoKhau,QuanHeVoiCH,NoiThuongTruTruoc, MaTrongHoKhau)VALUES ('"
                    + maNhanKhau + "', '" + maHoKhau + "',  N'Chủ hộ',N'" + "" + "'," + String.valueOf(1) + ")";
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

}
