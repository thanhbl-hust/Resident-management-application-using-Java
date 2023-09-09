package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Models.NhanKhau;
import Models.PhanAnhKienNghi;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/*
 * @author Vo Hoai Nam 4592
 * @version 1.0 11/2/2023
 * Class 136813, Teacher's name Trung.TT
 */
public class ThemPAKNController implements Initializable {
    @FXML
    TextField maNKField;

    @FXML
    Label alertLabel;

    @FXML
    Label hoTenLabel;

    @FXML
    TextArea noidungArea;

    @FXML
    DatePicker ngayPADatePicker;

    @FXML
    Button saveButton;

    private PhanAnhKienNghiController pAKNController;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ngayPADatePicker.setValue(LocalDate.now());
        BooleanBinding ismaNKFieldEmpty = maNKField.textProperty().isEmpty();
        BooleanBinding isnoidungAreaEmpty = noidungArea.textProperty().isEmpty();
        BooleanBinding areTextFieldEmpty = ismaNKFieldEmpty.or(isnoidungAreaEmpty);
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
                alertLabel.setText("Mã Nhân khẩu có dạng: NK.xxxxx");
            }
        });

    }

    public PhanAnhKienNghiController getpAKNController() {
        return pAKNController;
    }

    public void setpAKNController(PhanAnhKienNghiController pAKNController) {
        this.pAKNController = pAKNController;
    }

    @FXML
    protected void submit(ActionEvent e) {
        PhanAnhKienNghi pAKN;
        try {
            Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                    SQLController.PASSWORD);
            Statement stmt = conn.createStatement();
            String query = "SELECT TOP 1 MaPA FROM dbo.PhanAnhKienNghi ORDER BY MaPA DESC";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String input = rs.getString(1);
            System.out.println(input);
            int dotIndex = input.indexOf(".");
            String prefix = input.substring(0, dotIndex + 1);
            int number = Integer.parseInt(input.substring(dotIndex + 1).trim()) + 1;
            String maPA = (prefix + String.format("%05d", number)).trim();

            pAKN = new PhanAnhKienNghi(maPA, new NhanKhau(hoTenLabel.getText()), noidungArea.getText(),
                    ngayPADatePicker.getValue(), "Chưa xử lý", null, null, null);

            query = "INSERT INTO dbo.PhanAnhKienNghi (MaPA,MaNhanKhau,NoiDung,NgayPA,TrangThai, CapPhanHoi, PhanHoi,NgayPhanHoi) VALUES ('"
                    + maPA + "','" + maNKField.getText() + "',N'" + noidungArea.getText() + "','"
                    + ngayPADatePicker.getValue().toString() + "',N'Chưa phản hồi',NULL,NULL, NULL)";
            System.out.println(query);
            stmt.execute(query);
            conn.close();

            pAKNController.addList(pAKN);

        } catch (Exception ex) {
            ex.getStackTrace();
        }
        Alert infoAlert = new Alert(AlertType.INFORMATION);
        infoAlert.setHeaderText("Tạo Nhân Khẩu Thành Công");
        // infoAlert.setContentText("Tạo Nhân Khẩu Thành Công")
        infoAlert.showAndWait();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();

    }

}
