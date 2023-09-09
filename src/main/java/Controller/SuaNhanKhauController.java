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

public class SuaNhanKhauController implements Initializable {

    @FXML
    Label alertLabel;

    @FXML
    private TableView<ThanhVienCuaHo> tableView;

    @FXML
    private TableColumn<ThanhVienCuaHo, String> hoTen;

    @FXML
    private TableColumn<ThanhVienCuaHo, String> qHeChuHo;

    @FXML
    TextField maHoKhauField;

    @FXML
    TextField hoTenField;

    @FXML
    TextField biDanhField;

    @FXML
    DatePicker ngaySinhDatePicker;

    @FXML
    TextField cccdField;

    @FXML
    TextField queQuanField;

    @FXML
    TextField thuongTruField;

    @FXML
    ChoiceBox<String> gioiTinBox;

    @FXML
    ChoiceBox<String> danTocBox;

    @FXML
    TextField ngheNghiepField;

    @FXML
    TextField noiLamViecField;

    @FXML
    ChoiceBox<String> quanHeVoiChuHoBox;

    @FXML
    TextField noiThuongTruTruocField;

    @FXML
    Button saveButton;

    private NhanKhau newNhanKhau;
    private NhanKhauController nhanKhauController;
    private NhanKhau nhanKhauEdit;

    public SuaNhanKhauController() {

    }

    public NhanKhau getNhanKhauEdit() {
        return nhanKhauEdit;
    }

    public void setNhanKhauEdit(NhanKhau nhanKhauEdit) {
        this.nhanKhauEdit = nhanKhauEdit;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ngaySinhDatePicker.setValue(LocalDate.now());

        BooleanBinding ismaHoKhauFieldEmpty = maHoKhauField.textProperty().isEmpty();
        BooleanBinding ishohoTenFieldEmpty = hoTenField.textProperty().isEmpty();
        BooleanBinding iscccdFieldEmpty = cccdField.textProperty().isEmpty();
        BooleanBinding areTextFieldsEmpty = ismaHoKhauFieldEmpty.or(ishohoTenFieldEmpty).or(iscccdFieldEmpty);
        saveButton.disableProperty().bind(areTextFieldsEmpty);

        maHoKhauField.textProperty().addListener((observable, oldValue, newValue) -> {
            List<ThanhVienCuaHo> tV = new ArrayList<ThanhVienCuaHo>();
            ObservableList<ThanhVienCuaHo> tVList;

            if (newValue.length() == 8) {
                try {
                    Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                            SQLController.PASSWORD);
                    Statement stmt = conn.createStatement();
                    String query = "SELECT NK.HoTen, TV.QuanHeVoiCH FROM dbo.NhanKhau AS  NK INNER JOIN dbo.ThanhVienCuaHo AS TV ON TV.MaNhanKhau = NK.MaNhanKhau WHERE TV.MaHoKhau = '"
                            + newValue + "'";
                    // System.out.println(query);
                    ResultSet rs = stmt.executeQuery(query);
                    if (!rs.next()) {
                        alertLabel.setText("Mã Hộ khẩu sai");

                    } else {
                        alertLabel.setText("Mã Hộ khẩu đúng");
                        do {
                            tV.add(new ThanhVienCuaHo(rs.getNString(1), rs.getNString(2)));
                        } while (rs.next());
                    }
                    tVList = FXCollections.observableArrayList(tV);
                    hoTen.setCellValueFactory(new PropertyValueFactory<ThanhVienCuaHo, String>("hoTen"));
                    qHeChuHo.setCellValueFactory(new PropertyValueFactory<ThanhVienCuaHo, String>("quanHeVoiChuHo"));
                    tableView.setItems(tVList);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                alertLabel.setText("Mã Hộ khẩu có dạng: HK.xxxxx");
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
        // System.out.println(danTocBox.getValue());
        NhanKhau selectNhanKhau = nhanKhauController.getSelectNhanKhau();

        newNhanKhau = new NhanKhau(selectNhanKhau.getMaNhanKhau(), hoTenField.getText(), biDanhField.getText(),
                cccdField.getText(), ngaySinhDatePicker.getValue(), gioiTinBox.getValue().toString(),
                queQuanField.getText(),
                thuongTruField.getText(), danTocBox.getValue().toString(), ngheNghiepField.getText(),
                noiLamViecField.getText());

        Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                SQLController.PASSWORD);
        Statement stmt = conn.createStatement();

        String query = "UPDATE dbo.NhanKhau SET	HoTen = N'" + hoTenField.getText() + "', BiDanh = N'"
                + biDanhField.getText() + "', CCCD = '" + cccdField.getText()
                + "', NgaySinh = '" + ngaySinhDatePicker.getValue().toString() + "', GioiTinh = N'"
                + gioiTinBox.getValue().toString() + "', QueQuan = N'" + queQuanField.getText()
                + "', ThuongTru = N'" + thuongTruField.getText() + "', Dantoc = N'" + danTocBox.getValue().toString()
                + "', NgheNghiep = N'" + ngheNghiepField.getText()
                + "', NoiLamViec = N'" + noiLamViecField.getText() + "' WHERE MaNhanKhau = '"
                + selectNhanKhau.getMaNhanKhau() + "'";
        System.out.println(query);
        stmt.execute(query);

        query = "UPDATE dbo.ThanhVienCuaHo SET	MaHoKhau = '" + maHoKhauField.getText() + "', QuanHeVoiCH = N'"
                + quanHeVoiChuHoBox.getValue().toString()
                + "', NoiThuongTruTruoc = N'" + noiThuongTruTruocField.getText() + "' WHERE MaNhanKhau = '"
                + selectNhanKhau.getMaNhanKhau() + "'";
        // System.out.println(query);
        stmt.execute(query);
        conn.close();

        // System.out.println(selectNhanKhau.getHoTen());
        nhanKhauController.editList(selectNhanKhau, newNhanKhau);
        Alert infoAlert = new Alert(AlertType.INFORMATION);
        // NhanKhau nk = this.getNhanKhauEdit();

        infoAlert.setHeaderText("Sửa Nhân Khẩu Thành Công");

        infoAlert.showAndWait();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

}
