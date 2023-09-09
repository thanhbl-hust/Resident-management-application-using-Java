package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import Models.HoKhau;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SuaHoKhauController implements Initializable {

    @FXML
    Label maHoKhauLabel;

    @FXML
    Label maNhanKhaufField;

    @FXML
    Label hoTenLabel;

    @FXML
    TextField diaChiField;

    @FXML
    Button saveButton;

    private HoKhauController hoKhauController;
    private HoKhau hoKhauEdit;
    private String cCCD;

    public HoKhau getHoKhauEdit() {
        return hoKhauEdit;
    }

    public void setHoKhauEdit(HoKhau hoKhauEdit) {
        this.hoKhauEdit = hoKhauEdit;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        BooleanBinding isMaNhanKhauEmpty = maNhanKhaufField.textProperty().isEmpty();
        BooleanBinding isDiaChiEmpty = diaChiField.textProperty().isEmpty();
        BooleanBinding areTextFieldsEmpty = isMaNhanKhauEmpty.or(isDiaChiEmpty);

        saveButton.disableProperty().bind(areTextFieldsEmpty);
    }

    public HoKhauController getHoKhauController() {
        return hoKhauController;
    }

    public void setHoKhauController(HoKhauController hoKhauController) {
        this.hoKhauController = hoKhauController;
    }

    @FXML
    protected void Submit(ActionEvent e) throws SQLException {

        Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                SQLController.PASSWORD);
        Statement stmt = conn.createStatement();
        String query = "UPDATE dbo.HoKhau  SET Diachi = N'" + diaChiField.getText() + "' WHERE MaHoKhau = '"
                + maHoKhauLabel.getText() + "'";
        stmt.execute(query);

        query = "SELECT CCCD FROM dbo.NhanKhau WHERE MaNhanKhau = '" + maNhanKhaufField.getText() + "'";

        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            cCCD = rs.getString(1);
        }

        conn.close();

        HoKhau newHoKhau = new HoKhau(maHoKhauLabel.getText(), hoTenLabel.getText(), maNhanKhaufField.getText(), cCCD,
                diaChiField.getText());

        // System.out.println(selectNhanKhau.getHoTen());
        hoKhauController.editList(hoKhauEdit, newHoKhau);
        Alert infoAlert = new Alert(AlertType.INFORMATION);
        // NhanKhau nk = this.getNhanKhauEdit();

        infoAlert.setHeaderText("Sửa Hộ Khẩu Thành Công");

        infoAlert.showAndWait();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

}
