package Controller;

import Models.NhanKhau;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/*
 * @author Vo Hoai Nam 4592
 * @version 1.0 11/2/2023
 * Class 136813, Teacher's name Trung.TT
 */
public class NhanKhauController implements Initializable {

     @FXML
     private TableView<NhanKhau> table;

     @FXML
     private TableColumn<NhanKhau, Integer> sTT;

     @FXML
     private TableColumn<NhanKhau, String> maNhanKhau;

     @FXML
     private TableColumn<NhanKhau, String> hoten;

     @FXML
     private TableColumn<NhanKhau, String> bidanh;

     @FXML
     private TableColumn<NhanKhau, String> cccd;

     @FXML
     private TableColumn<NhanKhau, LocalDate> ngaysinh;

     @FXML
     private TableColumn<NhanKhau, String> gioitinh;

     @FXML
     private TableColumn<NhanKhau, String> quequan;

     @FXML
     private TableColumn<NhanKhau, String> thuongtru;

     @FXML
     private TableColumn<NhanKhau, String> dantoc;

     @FXML
     private TableColumn<NhanKhau, String> nghenghiep;

     @FXML
     private TableColumn<NhanKhau, String> noilamviec;

     @FXML
     private Button editButton;

     @FXML
     private Button delButton;

     @FXML
     private Button addButton;

     @FXML
     private Button changeButton;

     @FXML
     private Button statisticalButton;

     private ObservableList<NhanKhau> nhankhauList;
     private NhanKhau selectNhanKhau;

     private List<NhanKhau> NkList = new ArrayList<NhanKhau>();

     @FXML
     private TextField searchField;

     @Override
     public void initialize(URL arg0, ResourceBundle arg1) {
          try {
               Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                         SQLController.PASSWORD);
               Statement stmt = conn.createStatement();
               String query = "SELECT MaNhanKhau, HoTen, BiDanh, CCCD, NgaySinh, GioiTinh, QueQuan, ThuongTru, Dantoc, NgheNghiep, NoiLamViec FROM dbo.NhanKhau";
               ResultSet rs = stmt.executeQuery(query);
               while (rs.next()) {
                    NkList.add(new NhanKhau(rs.getString(1), rs.getNString(2), rs.getNString(3), rs.getString(4),
                              rs.getDate(5).toLocalDate(),
                              rs.getNString(6), rs.getNString(7), rs.getNString(8), rs.getNString(9), rs.getNString(10),
                              rs.getNString(11)));
               }
               conn.close();

          } catch (Exception e) {
               e.printStackTrace();
          }

          nhankhauList = FXCollections.observableArrayList(NkList);

          sTT.setCellValueFactory(column -> new ReadOnlyObjectWrapper(table.getItems().indexOf(column.getValue()) + 1));
          maNhanKhau.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("maNhanKhau"));
          hoten.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("hoTen"));
          bidanh.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("biDanh"));
          cccd.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("cccd"));
          ngaysinh.setCellValueFactory(new PropertyValueFactory<NhanKhau, LocalDate>("ngaySinh"));
          gioitinh.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("gioiTinh"));
          quequan.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("queQuan"));
          thuongtru.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("thuongTru"));
          dantoc.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("danToc"));
          nghenghiep.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("ngheNghiep"));
          noilamviec.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("noiLamViec"));
          table.setItems(nhankhauList);

          BooleanBinding isSelected = table.getSelectionModel().selectedItemProperty().isNull();
          delButton.disableProperty().bind(isSelected);
          editButton.disableProperty().bind(isSelected);
          changeButton.disableProperty().bind(isSelected);

          searchField.textProperty().addListener((observable, oldValue, newValue) -> {
               if (searchField.getText().isEmpty()) {
                    table.setItems(nhankhauList);
               } else {
                    String searchInfo = searchField.getText();
                    List<NhanKhau> searchResult = new ArrayList<NhanKhau>();
                    try {
                         Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                                   SQLController.PASSWORD);
                         Statement stmt = conn.createStatement();
                         String query = "SELECT MaNhanKhau, HoTen, CCCD, NgaySinh, GioiTinh, QueQuan, ThuongTru, Dantoc, NgheNghiep FROM dbo.NhanKhau WHERE HoTen LIKE N'%"
                                   + searchInfo + "%' OR MaNhanKhau LIKE '%" + searchInfo + "%' OR CCCD LIKE'%"
                                   + searchInfo + "%'";
                         System.out.println(query);
                         ResultSet rs = stmt.executeQuery(query);
                         while (rs.next()) {
                              searchResult.add(new NhanKhau(rs.getString(1), rs.getNString(2), rs.getString(3),
                                        rs.getDate(4).toLocalDate(),
                                        rs.getNString(5), rs.getNString(6), rs.getNString(7), rs.getNString(8),
                                        rs.getNString(9)));
                         }
                         System.out.println("wtf");
                         conn.close();

                    } catch (Exception esss) {
                         esss.printStackTrace();
                    }

                    ObservableList<NhanKhau> searchedNhanKhauList;
                    searchedNhanKhauList = FXCollections.observableArrayList(searchResult);
                    table.setItems(searchedNhanKhauList);
               }
          });

     }

     public void addList(NhanKhau nhanKhau) {
          nhankhauList.add(nhanKhau);
     }

     public void editList(NhanKhau cu, NhanKhau moi) {
          int sz = nhankhauList.size();
          for (int i = 0; i < sz; i++) {
               if (nhankhauList.get(i).equals(cu)) {
                    // System.out.println("day ne!!");
                    nhankhauList.set(i, moi);
                    break;
               }
          }
     }

     public NhanKhau getSelectNhanKhau() {
          return selectNhanKhau;
     }

     public void setSelectNhanKhau(NhanKhau selectNhanKhau) {
          this.selectNhanKhau = selectNhanKhau;
     }

     @FXML
     protected void addEvent(ActionEvent e) {
          Stage addStage = new Stage();
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("ThemNhanKhau.fxml"));
          Parent root;
          try {
               root = loader.load();
               Scene scene = new Scene(root);
               ThemNhanKhauController controller = loader.getController();
               controller.setNhanKhauController(this);
               scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
               addStage.setScene(scene);
               addStage.show();
          } catch (IOException e1) {
               System.out.println(e1.getMessage());
          }
     }

     @FXML
     protected void deleteEvent(ActionEvent e) throws SQLException {
          Alert alert;
          NhanKhau selected = table.getSelectionModel().getSelectedItem();
          Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                    SQLController.PASSWORD);
          Statement stmt;
          stmt = conn.createStatement();
          String query = "SELECT  COUNT(*) FROM dbo.HoKhau WHERE MaNKChuHo = '" + selected.getMaNhanKhau() + "'";
          ResultSet rs = stmt.executeQuery(query);
          rs.next();
          if (rs.getInt(1) != 0) {
               alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Lỗi");
               alert.setHeaderText("Xóa nhân khẩu là Chủ hộ");
               query = "SELECT MaHoKhau FROM dbo.HoKhau WHERE MaNKChuHo= '" + selected.getMaNhanKhau() + "'";
               rs = stmt.executeQuery(query);
               rs.next();
               alert.setContentText("Hãy xóa hộ khẩu " + rs.getString(1) + " trước!");
               alert.show();

          } else {
               alert = new Alert(Alert.AlertType.CONFIRMATION);
               alert.setTitle("Cofirmation");
               alert.setHeaderText("Bạn muốn xóa Nhân Khẩu " + selected.getHoTen());
               // alert.setContentText("choose your option");
               ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
               ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
               // ButtonType buttonTypeCancel = new ButtonType("Cancel",
               // ButtonBar.ButtonData.CANCEL_CLOSE);

               alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
               Optional<ButtonType> result = alert.showAndWait();

               if (result.get() == buttonTypeYes) {
                    String message = "Xóa Nhân Khẩu " + selected.getHoTen() + " thành công";
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Information");
                    alert1.setHeaderText("Notification");
                    alert1.setContentText(message);
                    alert1.show();
                    nhankhauList.remove(selected);
                    query = "DELETE FROM dbo.ThanhVienCuaHo WHERE MaNhanKhau = \'" + selected.getMaNhanKhau() + "\'"
                              + "\nDELETE FROM dbo.PhanAnhKienNghi WHERE MaNhanKhau = \'" + selected.getMaNhanKhau()
                              + "\'"
                              + "\nDELETE FROM dbo.TamTru WHERE MaNhanKhau = \'" + selected.getMaNhanKhau() + "\'"
                              + "\nDELETE FROM dbo.TamVang WHERE MaNhanKhau = \'" + selected.getMaNhanKhau() + "\'"
                              + "\n DELETE FROM NhanKhau WHERE MaNhanKhau = '" + selected.getMaNhanKhau() + "'";
                    System.out.println(query);
                    stmt.execute(query);

               } else if (result.get().getButtonData() == ButtonBar.ButtonData.NO)
                    System.out.println("Code for no");

          }
          conn.close();
     }

     @FXML
     protected void editEvent(ActionEvent e) throws IOException, SQLException {
          NhanKhau selected = table.getSelectionModel().getSelectedItem();
          setSelectNhanKhau(selected);
          Stage addStage = new Stage();
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("SuaNhanKhau.fxml"));
          Parent root = loader.load();
          SuaNhanKhauController controller = loader.getController();
          controller.setNhanKhauController(this);
          controller.setNhanKhauEdit(selected);

          controller.hoTenField.setText(selected.getHoTen());
          controller.biDanhField.setText(selected.getBiDanh());
          controller.cccdField.setText(selected.getCccd());
          controller.ngaySinhDatePicker.setValue(selected.getNgaySinh());
          controller.ngheNghiepField.setText(selected.getNgheNghiep());
          controller.danTocBox.setValue(selected.getDanToc());
          controller.thuongTruField.setText(selected.getThuongTru());
          controller.queQuanField.setText(selected.getQueQuan());
          controller.noiLamViecField.setText(selected.getNoiLamViec());

          Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                    SQLController.PASSWORD);
          Statement stmt = conn.createStatement();
          String query = "SELECT MaHoKhau FROM dbo.ThanhVienCuaHo WHERE MaNhanKhau = '"
                    + selected.getMaNhanKhau().trim() + "'";
          ResultSet rs = stmt.executeQuery(query);
          if (rs.next()) {
               controller.maHoKhauField.setText(rs.getString(1));
               System.out.println("xxx");
          }
          ;

          query = "SELECT NoiThuongTruTruoc FROM dbo.ThanhVienCuaHo WHERE MaNhanKhau = '"
                    + selected.getMaNhanKhau().trim() + "'";
          rs = stmt.executeQuery(query);
          if (rs.next()) {
               controller.noiThuongTruTruocField.setText(rs.getString(1));
               System.out.println("xxx");
          }
          ;

          Scene scene = new Scene(root);
          scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
          addStage.setScene(scene);
          addStage.show();

          conn.close();
     }

     @FXML
     protected void changeEvent(ActionEvent e) throws IOException, SQLException {
          NhanKhau selected = table.getSelectionModel().getSelectedItem();
          setSelectNhanKhau(selected);
          Stage addStage = new Stage();
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("ThayDoiNhanKhau.fxml"));
          Parent root = loader.load();
          ThayDoiNhanKhauController controller = loader.getController();
          controller.setNhanKhauController(this);
          controller.setNhanKhauEdit(selected);

          controller.hoTenLabel.setText(selected.getHoTen());

          Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                    SQLController.PASSWORD);
          Statement stmt = conn.createStatement();
          String query = "SELECT MaHoKhau, QuanHeVoiCH, NgayChuyenDi, NoiChuyenToi, GhiChu FROM dbo.ThanhVienCuaHo WHERE MaNhanKhau = '"
                    + selected.getMaNhanKhau().trim() + "'";
          ResultSet rs = stmt.executeQuery(query);
          if (rs.next()) {
               controller.maHoKhauLabel.setText(rs.getString(1));
               controller.qHVoiChuHoLabel.setText(rs.getNString(2));
               controller.ngayChuyenDiPicker
                         .setValue(rs.getDate(3) == null ? LocalDate.now() : rs.getDate(3).toLocalDate());
               controller.noiChuyenDenField.setText(rs.getNString(4));
               controller.ghiChuField.setText(rs.getNString(5));
          }
          ;

          Scene scene = new Scene(root);
          scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
          addStage.setScene(scene);
          addStage.show();

          conn.close();
     }

     @FXML
     protected void statisticalevent(ActionEvent e) {
          Stage addStage = new Stage();
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("ThongKeNhanKhau.fxml"));
          Parent root;
          try {
               root = loader.load();
               Scene scene = new Scene(root);
               scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
               addStage.setScene(scene);
               addStage.show();
          } catch (IOException e1) {
               System.out.println(e1.getMessage());
          }

     }

}