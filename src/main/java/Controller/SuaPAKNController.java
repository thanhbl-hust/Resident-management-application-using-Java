package Controller;

import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SuaPAKNController implements Initializable {
    @FXML
    Label maPALabel;

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
    private PhanAnhKienNghi pAKNEdit;

    public PhanAnhKienNghi getpAKNEdit() {
        return pAKNEdit;
    }

    public void setpAKNEdit(PhanAnhKienNghi pAKNEdit) {
        this.pAKNEdit = pAKNEdit;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ngayPADatePicker.setValue(LocalDate.now());
        BooleanBinding isnoidungAreaEmpty = noidungArea.textProperty().isEmpty();
        saveButton.disableProperty().bind(isnoidungAreaEmpty);
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
            String query = "UPDATE dbo.PhanAnhKienNghi SET NoiDung = N'" + noidungArea.getText()
                    + "', NgayPA = '" + ngayPADatePicker.getValue().toString() + "' WHERE MaPA = '"
                    + maPALabel.getText() + " '";
            System.out.println(query);
            stmt.execute(query);
            conn.close();
            pAKN = new PhanAnhKienNghi(maPALabel.getText(), new NhanKhau(hoTenLabel.getText()), noidungArea.getText(),
                    ngayPADatePicker.getValue(), "Chưa xử lý", null, null, null);
            pAKNController.editList(pAKNEdit, pAKN);

        } catch (Exception ex) {
            ex.getStackTrace();
        }
        Alert infoAlert = new Alert(AlertType.INFORMATION);
        infoAlert.setHeaderText("Sửa Phản Ánh Kiến Nghị Thành Công");
        // infoAlert.setContentText("Tạo Nhân Khẩu Thành Công")
        infoAlert.showAndWait();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();

    }

}
