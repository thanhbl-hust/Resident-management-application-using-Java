package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Label;

import Models.NhanKhau;
import Models.ThanhVienCuaHo;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/*
 * @author Vo Hoai Nam 4592
 * @version 1.0 11/2/2023
 * Class 136813, Teacher's name Trung.TT
 */
public class ThemNhanKhauController implements Initializable {

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
    private TamTruController tamTruController;

    public TamTruController getTamTruController() {
        return tamTruController;
    }

    public void setTamTruController(TamTruController tamTruController) {
        this.tamTruController = tamTruController;
    }

    public NhanKhau getNewNhanKhau() {
        return newNhanKhau;
    }

    public void setNewNhanKhau(NhanKhau newNhanKhau) {
        this.newNhanKhau = newNhanKhau;
    }

    private List<ThanhVienCuaHo> tV = new ArrayList<ThanhVienCuaHo>();
    private ObservableList<ThanhVienCuaHo> tVList;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ngaySinhDatePicker.setValue(LocalDate.now());

        BooleanBinding ismaHoKhauFieldEmpty = maHoKhauField.textProperty().isEmpty();
        BooleanBinding ishohoTenFieldEmpty = hoTenField.textProperty().isEmpty();
        BooleanBinding iscccdFieldEmpty = cccdField.textProperty().isEmpty();
        BooleanBinding areTextFieldsEmpty = ismaHoKhauFieldEmpty.or(ishohoTenFieldEmpty).or(iscccdFieldEmpty);
        saveButton.disableProperty().bind(areTextFieldsEmpty);

        maHoKhauField.textProperty().addListener((observable, oldValue, newValue) -> {

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
    public void Submit(ActionEvent e) throws IOException {
        try {
            Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                    SQLController.PASSWORD);
            Statement stmt = conn.createStatement();
            String query = "SELECT COUNT(*) FROM dbo.ThanhVienCuaHo WHERE MaHoKhau = '" + maHoKhauField.getText() + "'";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int iDTrongHoKhau = rs.getInt(1) + 1;

            System.out.println("id trong ho Khau: " + iDTrongHoKhau);

            stmt = conn.createStatement();
            query = "SELECT TOP 1 MaNhanKhau FROM dbo.NhanKhau ORDER BY MaNhanKhau DESC";
            rs = stmt.executeQuery(query);
            rs.next();
            String input = rs.getString(1);
            System.out.println(input);
            int dotIndex = input.indexOf(".");
            String prefix = input.substring(0, dotIndex + 1);
            int number = Integer.parseInt(input.substring(dotIndex + 1).trim()) + 1;
            String maNhanKhau = (prefix + String.format("%05d", number)).trim();
            System.out.println("ma nhan khau" + maNhanKhau);

            query = "INSERT INTO dbo.NhanKhau (MaNhanKhau,HoTen,BiDanh, CCCD, NgaySinh, GioiTinh, QueQuan, ThuongTru, Dantoc, NgheNghiep, NoiLamViec) VALUES ('"
                    + maNhanKhau + " ', N'" + hoTenField.getText() + " ', N'" + biDanhField.getText() + "', '"
                    + cccdField.getText() + "', '" + ngaySinhDatePicker.getValue().toString() + "', N'"
                    + gioiTinBox.getValue().toString() + "', N'" + queQuanField.getText() + "', N'"
                    + thuongTruField.getText() + "',N'"
                    + danTocBox.getValue().toString() + "', N'" + ngheNghiepField.getText() + "', N'"
                    + noiLamViecField.getText() + "')";
            stmt.execute(query);

            query = "INSERT INTO dbo.ThanhVienCuaHo(MaNhanKhau,MaHoKhau,QuanHeVoiCH,NoiThuongTruTruoc, MaTrongHoKhau) VALUES ('"
                    + maNhanKhau + "', '" + maHoKhauField.getText() + "',  N'" + quanHeVoiChuHoBox.getValue().toString()
                    + "',N'" + noiThuongTruTruocField.getText()
                    + "'," + String.valueOf(iDTrongHoKhau) + ")";
            stmt.execute(query);

            newNhanKhau = new NhanKhau(maNhanKhau, hoTenField.getText(), biDanhField.getText(), cccdField.getText(),
                    ngaySinhDatePicker.getValue(), gioiTinBox.getValue().toString(),
                    queQuanField.getText(), thuongTruField.getText(), danTocBox.getValue().toString(),
                    ngheNghiepField.getText(), noiLamViecField.getText());

            ThanhVienCuaHo thanhVienCuaHo = new ThanhVienCuaHo(maNhanKhau, hoTenField.getText(),
                    maHoKhauField.getText(), quanHeVoiChuHoBox.getValue().toString(), noiThuongTruTruocField.getText(),
                    iDTrongHoKhau);
            this.addList(thanhVienCuaHo);

            if (nhanKhauController != null)
                nhanKhauController.addList(newNhanKhau);
            else {
                query = "SELECT HK.Diachi FROM dbo.HoKhau AS HK INNER JOIN dbo.ThanhVienCuaHo AS TV ON TV.MaHoKhau = HK.MaHoKhau"
                        + " INNER JOIN dbo.NhanKhau AS NK ON NK.MaNhanKhau = TV.MaNhanKhau WHERE TV.MaNhanKhau = '"
                        + maNhanKhau + "'";
                rs = stmt.executeQuery(query);
                rs.next();
                Stage addStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("ThemTamTru.fxml"));
                Parent root = loader.load();
                ThemTamTruController controller = loader.getController();
                controller.maNKField.setText(maNhanKhau);
                controller.noiTamTruField.setText(rs.getNString(1));
                controller.setTamTruController(this.getTamTruController());
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
                addStage.setScene(scene);
                addStage.show();
            }

            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if (nhanKhauController != null) {
            Alert infoAlert = new Alert(AlertType.INFORMATION);
            infoAlert.setHeaderText("Tạo Nhân Khẩu Thành Công");
            // infoAlert.setContentText("Tạo Nhân Khẩu Thành Công")
            infoAlert.showAndWait();
        }

        // Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        // stage.close();
    }

    public void addList(ThanhVienCuaHo thanhVienCuaHo) {
        tVList.add(thanhVienCuaHo);
    }
}
