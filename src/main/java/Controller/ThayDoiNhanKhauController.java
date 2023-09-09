package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Models.NhanKhau;
import Models.ThanhVienCuaHo;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ThayDoiNhanKhauController implements Initializable {

    @FXML
    Label alertLabel;

    @FXML
    private TableView<ThanhVienCuaHo> tableView;

    @FXML
    private TableColumn<ThanhVienCuaHo, String> hoTen;

    @FXML
    private TableColumn<ThanhVienCuaHo, String> qHeChuHo;

    @FXML
    private TableColumn<ThanhVienCuaHo, String> ghiChu;

    @FXML
    Label maHoKhauLabel;

    @FXML
    Label hoTenLabel;

    @FXML
    Label qHVoiChuHoLabel;

    @FXML
    DatePicker ngayChuyenDiPicker;

    @FXML
    TextField noiChuyenDenField;

    @FXML
    TextField ghiChuField;

    @FXML
    Button saveButton;

    private NhanKhauController nhanKhauController;
    private NhanKhau nhanKhauEdit;

    public ThayDoiNhanKhauController() {

    }

    public NhanKhau getNhanKhauEdit() {
        return nhanKhauEdit;
    }

    public void setNhanKhauEdit(NhanKhau nhanKhauEdit) {
        this.nhanKhauEdit = nhanKhauEdit;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ngayChuyenDiPicker.setValue(LocalDate.now());

        BooleanBinding isghiChuFieldEmpty = ghiChuField.textProperty().isEmpty();
        saveButton.disableProperty().bind(isghiChuFieldEmpty);

        maHoKhauLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            List<ThanhVienCuaHo> tV = new ArrayList<ThanhVienCuaHo>();
            ObservableList<ThanhVienCuaHo> tVList;

            try {
                Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                        SQLController.PASSWORD);
                Statement stmt = conn.createStatement();
                String query = "SELECT NK.HoTen, TV.QuanHeVoiCH, TV.GhiChu FROM dbo.NhanKhau AS  NK INNER JOIN dbo.ThanhVienCuaHo AS TV ON TV.MaNhanKhau = NK.MaNhanKhau WHERE TV.MaHoKhau = '"
                        + maHoKhauLabel.getText() + "'";
                // System.out.println(query);
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    tV.add(new ThanhVienCuaHo(rs.getNString(1), rs.getNString(2), rs.getNString(3)));
                }

                tVList = FXCollections.observableArrayList(tV);
                hoTen.setCellValueFactory(new PropertyValueFactory<ThanhVienCuaHo, String>("hoTen"));
                qHeChuHo.setCellValueFactory(new PropertyValueFactory<ThanhVienCuaHo, String>("quanHeVoiChuHo"));
                ghiChu.setCellValueFactory(new PropertyValueFactory<ThanhVienCuaHo, String>("ghiChu"));
                tableView.setItems(tVList);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    public NhanKhauController getNhanKhauController() {
        return nhanKhauController;
    }

    public void setNhanKhauController(NhanKhauController nhanKhauController) {
        this.nhanKhauController = nhanKhauController;
    }

    @FXML
    protected void Submit(ActionEvent e) throws SQLException {
        // //System.out.println(danTocBox.getValue());
        // NhanKhau selectNhanKhau = nhanKhauController.getSelectNhanKhau();

        Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                SQLController.PASSWORD);
        Statement stmt = conn.createStatement();
        String query = "UPDATE dbo.ThanhVienCuaHo SET	 NgayChuyenDi = '" + ngayChuyenDiPicker.getValue().toString()
                + "', NoiChuyenToi = N'"
                + noiChuyenDenField.getText() + "', GhiChu = N'" + ghiChuField.getText() + "' WHERE maNhanKhau = '"
                + nhanKhauEdit.getMaNhanKhau() + "'";
        System.out.println(query);
        stmt.execute(query);
        conn.close();

        // System.out.println(selectNhanKhau.getHoTen());
        // nhanKhauController.editList(selectNhanKhau, newNhanKhau);
        Alert infoAlert = new Alert(AlertType.INFORMATION);
        // NhanKhau nk = this.getNhanKhauEdit();

        infoAlert.setHeaderText("Thay Đổi Nhân Khẩu Thành Công");

        infoAlert.showAndWait();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

}
