package Controller;

import Models.HoKhau;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class HoKhauController implements Initializable {

   @FXML
   private TableView<HoKhau> table;

   @FXML
   private TableColumn<HoKhau, Integer> sTT;

   @FXML
   private TableColumn<HoKhau, String> idHoKhau;

   @FXML
   private TableColumn<HoKhau, String> hoTenChuHo;

   @FXML
   private TableColumn<HoKhau, String> cCCDChuHo;

   @FXML
   private TableColumn<HoKhau, String> diaChiHo;

   @FXML
   private TextField searchField;

   @FXML
   private Button showTVButton;

   @FXML
   private Button editButton;

   @FXML
   private Button delButton;

   @FXML
   private Button addButton;

   private HoKhau selectHoKhau;
   private String maNKChuHo;

   private ObservableList<HoKhau> hokhauList;

   private List<HoKhau> hKList = new ArrayList<HoKhau>();

   public String getMaNKChuHo() {
      return maNKChuHo;
   }

   public void setMaNKChuHo(String maNKChuHo) {
      this.maNKChuHo = maNKChuHo;
   }

   public HoKhau getSelectHoKhau() {
      return selectHoKhau;
   }

   public void setSelectHoKhau(HoKhau selectHoKhau) {
      this.selectHoKhau = selectHoKhau;
   }

   @Override
   public void initialize(URL arg0, ResourceBundle arg1) {

      try {
         Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
               SQLController.PASSWORD);
         Statement stmt = conn.createStatement();
         String query = "SELECT HK.maHoKhau, HoTen, NK.maNhanKhau, NK.CCCD, HK.Diachi FROM dbo.HoKhau AS HK INNER JOIN dbo.NhanKhau AS NK ON HK.maNKChuHo = NK.maNhanKhau";
         ResultSet rs = stmt.executeQuery(query);
         while (rs.next()) {
            hKList.add(
                  new HoKhau(rs.getString(1), rs.getNString(2), rs.getString(3), rs.getString(4), rs.getNString(5)));
         }
         conn.close();

      } catch (Exception e) {
         e.printStackTrace();
      }

      hokhauList = FXCollections.observableArrayList(hKList);
      sTT.setCellValueFactory(column -> new ReadOnlyObjectWrapper(table.getItems().indexOf(column.getValue()) + 1));
      idHoKhau.setCellValueFactory(new PropertyValueFactory<HoKhau, String>("idHoKhau"));
      hoTenChuHo.setCellValueFactory(new PropertyValueFactory<HoKhau, String>("hoTen"));
      cCCDChuHo.setCellValueFactory(new PropertyValueFactory<HoKhau, String>("cCCDChuHo"));
      diaChiHo.setCellValueFactory(new PropertyValueFactory<HoKhau, String>("diaChiHo"));
      table.setItems(hokhauList);

      BooleanBinding isSelected = table.getSelectionModel().selectedItemProperty().isNull();
      delButton.disableProperty().bind(isSelected);
      editButton.disableProperty().bind(isSelected);
      showTVButton.disableProperty().bind(isSelected);

      searchField.textProperty().addListener((observable, oldValue, newValue) -> {
         if (searchField.getText().isEmpty()) {
            table.setItems(hokhauList);
         } else {
            String searchInfo = searchField.getText();
            List<HoKhau> searchResult = new ArrayList<HoKhau>();
            try {
               Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                     SQLController.PASSWORD);
               Statement stmt = conn.createStatement();
               String query = "SELECT HK.maHoKhau, HoTen, NK.maNhanKhau, NK.CCCD, HK.Diachi FROM dbo.HoKhau AS HK INNER JOIN dbo.NhanKhau AS NK ON HK.maNKChuHo = NK.maNhanKhau"
                     + " WHERE NK.HoTen LIKE N'%" + searchInfo + "%' OR HK.MaHoKhau LIKE '%" + searchInfo
                     + "%' OR NK.CCCD LIKE '%" + searchInfo + "%'";
               System.out.println(query);
               ResultSet rs = stmt.executeQuery(query);
               while (rs.next()) {
                  searchResult.add(new HoKhau(rs.getString(1), rs.getNString(2), rs.getString(3), rs.getString(4),
                        rs.getNString(5)));
               }
               System.out.println("wtf");
               conn.close();

            } catch (Exception esss) {
               esss.printStackTrace();
            }

            ObservableList<HoKhau> searchedNhanKhauList;
            searchedNhanKhauList = FXCollections.observableArrayList(searchResult);
            table.setItems(searchedNhanKhauList);
         }
      });
   }

   public void addList(HoKhau hoKhau) {
      hokhauList.add(hoKhau);
   }

   public void editList(HoKhau cu, HoKhau moi) {
      // System.out.println(cu.getHoTen());
      // System.out.println(moi.getHoTen());
      int sz = hokhauList.size();
      for (int i = 0; i < sz; i++) {
         if (hokhauList.get(i).equals(cu)) {
            // System.out.println("day ne!!");
            hokhauList.set(i, moi);
            break;
         }
      }
   }

   @FXML
   protected void addEvent(ActionEvent e) throws IOException {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Cofirmation");
      alert.setHeaderText("Thêm Hộ khẩu mới");
      alert.setContentText("choose your option");

      ButtonType buttonTypeYes = new ButtonType("Tạo Hộ khẩu mới", ButtonBar.ButtonData.YES);
      ButtonType buttonTypeNo = new ButtonType("Tách Hộ Khẩu", ButtonBar.ButtonData.NO);

      alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

      Optional<ButtonType> result = alert.showAndWait();

      if (result.get() == buttonTypeYes) {
         Stage addStage = new Stage();
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(getClass().getResource("ThemHoKhau.fxml"));
         Parent root = loader.load();
         ThemHoKhauController controller = loader.getController();
         controller.setHoKhauController(this);
         Scene scene = new Scene(root);
         scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
         addStage.setScene(scene);
         addStage.show();

      } else if (result.get().getButtonData() == ButtonBar.ButtonData.NO) {
         Stage addStage = new Stage();
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(getClass().getResource("TachHoKhau.fxml"));
         Parent root = loader.load();
         TachHoKhauController controller = loader.getController();
         controller.setHoKhauController(this);
         Scene scene = new Scene(root);
         scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
         addStage.setScene(scene);
         addStage.show();
      }
   }

   @FXML
   protected void deleteEvent(ActionEvent e) throws IOException {
      HoKhau selected = table.getSelectionModel().getSelectedItem();
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Cofirmation");
      alert.setHeaderText("Bạn muốn xóa hộ khẩu có chủ hộ tên " + selected.getHoTenChuHo());
      // alert.setContentText("choose your option");

      ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
      ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
      // ButtonType buttonTypeCancel = new ButtonType("Cancel",
      // ButtonBar.ButtonData.CANCEL_CLOSE);

      alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

      Optional<ButtonType> result = alert.showAndWait();

      if (result.get() == buttonTypeYes) {
         try {
            hokhauList.remove(selected);
            Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                  SQLController.PASSWORD);
            Statement stmt = conn.createStatement();
            String query = "DELETE FROM dbo.ThanhVienCuaHo WHERE MaHoKhau = '" + selected.getIdHoKhau() + "'\n"
                  + "DELETE FROM HoKhau WHERE MaHoKhau = '" + selected.getIdHoKhau() + "'\n"
                  + "DELETE FROM dbo.NhanKhau WHERE MaNhanKhau = '" + selected.getMaNKChuHo() + "'";
            stmt.executeQuery(query);
            System.out.println(query);
            conn.close();
            String message = "Xóa Hộ khẩu " + selected.getHoTenChuHo() + " thành công";
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Information");
            alert1.setHeaderText("Notification");
            alert1.setContentText(message);
            alert1.show();

         } catch (Exception ex) {
            ex.getStackTrace();
         }

      } else if (result.get().getButtonData() == ButtonBar.ButtonData.NO)
         System.out.println("Code for no");
   }

   @FXML
   protected void editEvent(ActionEvent e) throws IOException, SQLException {
      HoKhau selected = table.getSelectionModel().getSelectedItem();
      setSelectHoKhau(selected);
      Stage addStage = new Stage();
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("SuaHoKhau.fxml"));
      Parent root = loader.load();

      SuaHoKhauController controller = loader.getController();
      controller.setHoKhauController(this);
      controller.setHoKhauEdit(selected);
      controller.maHoKhauLabel.setText(selected.getIdHoKhau());
      controller.maNhanKhaufField.setText(selected.getMaNKChuHo());
      controller.hoTenLabel.setText(selected.getHoTen());
      controller.diaChiField.setText(selected.getDiaChiHo());

      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
      addStage.setScene(scene);
      addStage.show();
   }

   @FXML
   protected void showEvent(ActionEvent e) throws IOException, SQLException {
      HoKhau selected = table.getSelectionModel().getSelectedItem();
      Stage addStage = new Stage();
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("XemThanhVien.fxml"));
      Parent root = loader.load();

      System.out.println(selected.getIdHoKhau());
      XemThanhVienController controller = loader.getController();
      controller.maHoKhauLabel.setText(selected.getIdHoKhau());
      controller.diaChiLabel.setText(selected.getDiaChiHo());
      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
      addStage.setScene(scene);
      addStage.show();
   }

}