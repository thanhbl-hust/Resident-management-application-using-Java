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
import java.util.Optional;
import java.util.ResourceBundle;

import Models.NhanKhau;
import Models.TamVang;
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

public class TamVangController implements Initializable {
   @FXML
   private TableView<TamVang> table;

   @FXML
   private TableColumn<TamVang, String> sTT;

   @FXML
   private TableColumn<TamVang, String> idTamVang;

   @FXML
   private TableColumn<TamVang, String> hoTen;

   @FXML
   private TableColumn<TamVang, String> noiTamVang;

   @FXML
   private TableColumn<TamVang, LocalDate> tuNgay;

   @FXML
   private TableColumn<TamVang, LocalDate> denNgay;

   @FXML
   private TableColumn<TamVang, String> lyDo;

   @FXML
   private TextField searchField;

   @FXML
   private Button editButton;

   @FXML
   private Button delButton;

   @FXML
   private Button addButton;

   @FXML
   private Button statisticalButton;

   private ObservableList<TamVang> tamVangList;
   private List<TamVang> tVList = new ArrayList<TamVang>();

   @Override
   public void initialize(URL arg0, ResourceBundle arg1) {
      try {
         Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
               SQLController.PASSWORD);
         Statement stmt = conn.createStatement();
         String query = "SELECT ID, HoTen, NoiTamTru, TuNgay, DenNgay, LyDo FROM dbo.TamVang INNER JOIN dbo.NhanKhau ON NhanKhau.MaNhanKhau = TamVang.MaNhanKhau";
         ResultSet rs = stmt.executeQuery(query);

         while (rs.next()) {
            tVList.add(new TamVang(rs.getString(1), new NhanKhau(rs.getNString(2)), rs.getNString(3),
                  rs.getDate(4).toLocalDate(), rs.getDate(5).toLocalDate(), rs.getNString(6)));
         }

      } catch (Exception e) {
         e.printStackTrace();
      }

      tamVangList = FXCollections.observableArrayList(
            tVList
      // new TamVang("TT.1", new NhanKhau("Nam"), "KA",
      // LocalDate.of(2021,8,9),LocalDate.of(2020, 10, 20), "ly do"),
      // new TamVang("TT.1", new NhanKhau("Nam"), "KA", LocalDate.of(2020, 10, 20),
      // LocalDate.of(2021, 9, 8), "Tro")
      );
      sTT.setCellValueFactory(column -> new ReadOnlyObjectWrapper(table.getItems().indexOf(column.getValue()) + 1));
      idTamVang.setCellValueFactory(new PropertyValueFactory<TamVang, String>("maTamVang"));
      hoTen.setCellValueFactory(new PropertyValueFactory<TamVang, String>("hoTen"));
      noiTamVang.setCellValueFactory(new PropertyValueFactory<TamVang, String>("noiTamTru"));
      tuNgay.setCellValueFactory(new PropertyValueFactory<TamVang, LocalDate>("tuNgay"));
      denNgay.setCellValueFactory(new PropertyValueFactory<TamVang, LocalDate>("denNgay"));
      lyDo.setCellValueFactory(new PropertyValueFactory<TamVang, String>("lyDo"));
      table.setItems(tamVangList);

      BooleanBinding isSelected = table.getSelectionModel().selectedItemProperty().isNull();
      delButton.disableProperty().bind(isSelected);
      editButton.disableProperty().bind(isSelected);

      searchField.textProperty().addListener((observable, oldValue, newValue) -> {
         if (searchField.getText().isEmpty()) {
            table.setItems(tamVangList);
         } else {
            String searchInfo = searchField.getText();
            List<TamVang> searchResult = new ArrayList<TamVang>();
            try {
               Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                     SQLController.PASSWORD);
               Statement stmt = conn.createStatement();
               String query = "SELECT ID, HoTen, NoiTamTru, TuNgay, DenNgay, LyDo FROM dbo.TamVang INNER JOIN dbo.NhanKhau ON NhanKhau.MaNhanKhau = dbo.TamVang.MaNhanKhau"
                     + " WHERE ID LIKE '%" + searchInfo + "%' OR HoTen LIKE N'%" + searchInfo + "%'";
               System.out.println(query);
               ResultSet rs = stmt.executeQuery(query);
               while (rs.next()) {
                  searchResult.add(new TamVang(rs.getString(1), new NhanKhau(rs.getNString(2)), rs.getNString(3),
                        rs.getDate(4).toLocalDate(), rs.getDate(5).toLocalDate(), rs.getNString(6)));
               }
               System.out.println("wtf");
               conn.close();

            } catch (Exception esss) {
               esss.printStackTrace();
            }

            ObservableList<TamVang> searchedTamVangList;
            searchedTamVangList = FXCollections.observableArrayList(searchResult);
            table.setItems(searchedTamVangList);
         }
      });
   }

   public void addList(TamVang tamVang) {
      tamVangList.add(tamVang);
   }

   public void removeList(TamVang tamVangCu, TamVang tamVangMoi) {
      int index = tamVangList.indexOf(tamVangCu);
      tamVangList.set(index, tamVangMoi);
   }

   @FXML
   protected void addEvent(ActionEvent e) throws IOException {
      Stage addStage = new Stage();
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("ThemTamVang.fxml"));
      Parent root = loader.load();
      ThemTamVangController controller = loader.getController();
      controller.setTamVangController(this);
      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
      addStage.setScene(scene);
      addStage.show();
   }

   @FXML
   protected void deleteEvent(ActionEvent e) throws IOException, SQLException {
      TamVang selected = table.getSelectionModel().getSelectedItem();
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Cofirmation");
      alert.setHeaderText("Bạn muốn xóa tạm trú của " + selected.getHoTen());
      // alert.setContentText("choose your option");

      ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
      ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
      // ButtonType buttonTypeCancel = new ButtonType("Cancel",
      // ButtonBar.ButtonData.CANCEL_CLOSE);

      alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

      Optional<ButtonType> result = alert.showAndWait();

      if (result.get() == buttonTypeYes) {
         Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
               SQLController.PASSWORD);
         Statement stmt;
         stmt = conn.createStatement();
         String query = "DELETE FROM dbo.TamVang WHERE  ID = '" + selected.getMaTamVang() + "\'";
         System.out.println(query);
         stmt.execute(query);
         String message = "Xóa Tạm vắng " + selected.getHoTen() + " thành công";
         Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
         alert1.setTitle("Information");
         alert1.setHeaderText("Notification");
         alert1.setContentText(message);
         alert1.show();
         tamVangList.remove(selected);
      } else if (result.get().getButtonData() == ButtonBar.ButtonData.NO)
         System.out.println("Code for no");
   }

   @FXML
   protected void editEvent(ActionEvent e) throws IOException {
      TamVang selected = table.getSelectionModel().getSelectedItem();
      Stage addStage = new Stage();
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("SuaTamVang.fxml"));
      Parent root = loader.load();
      SuaTamVangController controller = loader.getController();
      controller.setTamVangController(this);
      controller.maNKField.setText(selected.getMaTamVang());
      controller.hoTenLabel.setText(selected.getHoTen());
      controller.noiTamTruField.setText(selected.getNoiTamTru());
      controller.tuNgayDatePicker.setValue(selected.getTuNgay());
      controller.denNgayDatePicker.setValue(selected.getDenNgay());
      controller.lydoField.setText(selected.getLyDo());
      controller.setTamVangEdit(selected);
      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
      addStage.setScene(scene);
      addStage.show();

   }

   @FXML
   protected void statisticalevent(ActionEvent e) {
      Stage addStage = new Stage();
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("ThongKeTamVang.fxml"));
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
