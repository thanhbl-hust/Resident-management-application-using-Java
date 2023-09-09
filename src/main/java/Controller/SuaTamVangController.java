package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

import Models.TamVang;
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

public class SuaTamVangController implements Initializable {
    @FXML
    Label maNKField;

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

    private TamVangController tamVangController;
    private TamVang tamVangEdit;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        BooleanBinding isnoiTamTruFieldEmpty = noiTamTruField.textProperty().isEmpty();
        BooleanBinding islydoFieldEmpty = lydoField.textProperty().isEmpty();
        BooleanBinding areTextFieldEmpty = isnoiTamTruFieldEmpty.or(islydoFieldEmpty);
        saveButton.disableProperty().bind(areTextFieldEmpty);
    }

    public TamVangController getTamVangController() {
        return tamVangController;
    }

    public void setTamVangController(TamVangController tamVangController) {
        this.tamVangController = tamVangController;
    }

    public TamVang getTamVangEdit() {
        return tamVangEdit;
    }

    public void setTamVangEdit(TamVang tamVangEdit) {
        this.tamVangEdit = tamVangEdit;
    }

    @FXML
    protected void submit(ActionEvent e) {
        try {
            Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                    SQLController.PASSWORD);
            Statement stmt = conn.createStatement();
            String query = "UPDATE dbo.TamVang SET NoiTamTru = N'" + noiTamTruField.getText() + "', TuNgay = '"
                    + tuNgayDatePicker.getValue().toString() + "', DenNgay = '"
                    + denNgayDatePicker.getValue().toString()
                    + "' WHERE ID = '" + tamVangEdit.getMaTamVang() + "'";
            System.out.println(query);
            stmt.execute(query);
            conn.close();

            TamVang tamTru = new TamVang(tamVangEdit.getMaTamVang(), tamVangEdit.getNguoiTamVang(),
                    noiTamTruField.getText(), tuNgayDatePicker.getValue(), denNgayDatePicker.getValue(),
                    lydoField.getText());
            tamVangController.removeList(tamVangEdit, tamTru);

        } catch (Exception ex) {
            ex.getStackTrace();
        }
        Alert infoAlert = new Alert(AlertType.INFORMATION);
        infoAlert.setHeaderText("Sửa Tạm Vắng Thành Công");
        // infoAlert.setContentText("Tạo Nhân Khẩu Thành Công")
        infoAlert.showAndWait();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();

    }
}
