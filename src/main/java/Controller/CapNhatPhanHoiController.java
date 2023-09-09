package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

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

public class CapNhatPhanHoiController implements Initializable {
    @FXML
    Label maPALabel;

    @FXML
    Label hoTenLabel;

    @FXML
    Label ngayPALabel;

    @FXML
    TextField capPhanHoiField;

    @FXML
    DatePicker ngayPHDatePicker;

    @FXML
    TextArea phanHoiTextArea;

    @FXML
    Button saveButton;

    private PhanAnhKienNghiController pAKNController;
    public PhanAnhKienNghi pAKNEdit;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ngayPHDatePicker.setValue(LocalDate.now());
        BooleanBinding isnoidungAreaEmpty = phanHoiTextArea.textProperty().isEmpty();
        saveButton.disableProperty().bind(isnoidungAreaEmpty);
    }

    public PhanAnhKienNghiController getpAKNController() {
        return pAKNController;
    }

    public void setpAKNController(PhanAnhKienNghiController pAKNController) {
        this.pAKNController = pAKNController;
    }

    public PhanAnhKienNghi getpAKNEdit() {
        return pAKNEdit;
    }

    public void setpAKNEdit(PhanAnhKienNghi pAKNEdit) {
        this.pAKNEdit = pAKNEdit;
    }

    @FXML
    protected void submit(ActionEvent e) {
        PhanAnhKienNghi pAKN;
        try {
            Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                    SQLController.PASSWORD);
            Statement stmt = conn.createStatement();
            String query = "UPDATE dbo.PhanAnhKienNghi SET CapPhanHoi = N'" + capPhanHoiField.getText()
                    + "', NgayPhanHoi = '"
                    + ngayPHDatePicker.getValue().toString() + " ', PhanHoi = N'" + phanHoiTextArea.getText()
                    + "', TrangThai = N'Đã phản hồi' WHERE MaPA = '" + maPALabel.getText() + "'";
            System.out.println(query);
            stmt.execute(query);
            conn.close();
            pAKN = pAKNEdit;
            pAKN.setTrangThai("Đã phản hồi");
            pAKN.setCapPhanHoi(capPhanHoiField.getText());
            pAKN.setNgayPhanHoi(ngayPHDatePicker.getValue());
            pAKN.setPhanHoi(phanHoiTextArea.getText());
            pAKNController.editList(pAKNEdit, pAKN);

        } catch (Exception ex) {
            ex.getStackTrace();
        }
        Alert infoAlert = new Alert(AlertType.INFORMATION);
        infoAlert.setHeaderText("Cập Nhật Phản Hồi Thành Công");
        // infoAlert.setContentText("Tạo Nhân Khẩu Thành Công")
        infoAlert.showAndWait();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

}
