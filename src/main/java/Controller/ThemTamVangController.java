package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Models.NhanKhau;
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

/*
 * @author Vo Hoai Nam 4592
 * @version 1.0 11/2/2023
 * Class 136813, Teacher's name Trung.TT
 */
public class ThemTamVangController implements Initializable {
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

    private TamVangController tamVangController;

    public TamVangController getTamVangController() {
        return tamVangController;
    }

    public void setTamVangController(TamVangController tamVangController) {
        this.tamVangController = tamVangController;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        // Set the tuNgay and denNgay date pickers to current date
        tuNgayDatePicker.setValue(LocalDate.now());
        denNgayDatePicker.setValue(LocalDate.now());

        // Validate the maNKField input
        BooleanBinding ismaNKFieldEmpty = maNKField.textProperty().isEmpty();
        BooleanBinding isnoiTamTruFieldEmpty = noiTamTruField.textProperty().isEmpty();
        BooleanBinding islydoFieldEmpty = lydoField.textProperty().isEmpty();
        BooleanBinding areTextFieldEmpty = ismaNKFieldEmpty.or(isnoiTamTruFieldEmpty.or(islydoFieldEmpty));
        saveButton.disableProperty().bind(areTextFieldEmpty);

        // Update alertLabel and hoTenLabel when text in maNKField changes
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
                        // Handled the excepition when entered wrong MaNhanKhau
                        alertLabel.setText("Mã Nhân khẩu sai");

                    } else {
                        // display the valid HoTen tilte for entered MaNhanKhau
                        alertLabel.setText("Mã Nhân khẩu đúng");
                        hoTenLabel.setText(rs.getNString(1));
                    }
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                // Display error message for entered MaNhanKhau code
                alertLabel.setText("Mã Nhân khẩu có dạng: NK.xxxxx");
            }
        });

    }

    // Submit method called on the press of saveButton
    @FXML
    protected void submit(ActionEvent e) {
        TamVang tamTru;
        try {

            Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                    SQLController.PASSWORD);
            Statement stmt = conn.createStatement();
            String query = "SELECT TOP 1 ID FROM dbo.TamVang ORDER BY ID DESC";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String input = rs.getString(1);
            System.out.println(input);
            int dotIndex = input.indexOf(".");
            String prefix = input.substring(0, dotIndex + 1);
            int number = Integer.parseInt(input.substring(dotIndex + 1).trim()) + 1;
            String maTamVang = (prefix + String.format("%05d", number)).trim();
            System.out.println("ma tam tru" + maTamVang);
            tamTru = new TamVang(maTamVang, new NhanKhau(hoTenLabel.getText()), noiTamTruField.getText(),
                    tuNgayDatePicker.getValue(), denNgayDatePicker.getValue(), lydoField.getText());

            query = "INSERT INTO dbo.TamVang(ID,MaNhanKhau,NoiTamTru,TuNgay,DenNgay,LyDo) VALUES ('"
                    + maTamVang + "', '" + maNKField.getText() + "',N'" + noiTamTruField.getText() + "','"
                    + tuNgayDatePicker.getValue().toString() + "','" + denNgayDatePicker.getValue().toString() + "',N'"
                    + lydoField.getText() + "')";
            System.out.println(query);
            stmt.execute(query);
            conn.close();

            tamVangController.addList(tamTru);

        } catch (Exception ex) {
            ex.getStackTrace();
        }
        Alert infoAlert = new Alert(AlertType.INFORMATION);
        infoAlert.setHeaderText("Tạo Tạm vắng Thành Công");
        // infoAlert.setContentText("Tạo Nhân Khẩu Thành Công")
        infoAlert.showAndWait();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();

    }

}
