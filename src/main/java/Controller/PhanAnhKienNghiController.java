package Controller;

import Models.PhanAnhKienNghi;
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

public class PhanAnhKienNghiController implements Initializable {

   @FXML
   private TableView<PhanAnhKienNghi> table;

   @FXML
   private TableColumn<PhanAnhKienNghi, Integer> sTT;
   @FXML
   private TableColumn<PhanAnhKienNghi, String> maPA;

   @FXML
   private TableColumn<PhanAnhKienNghi, String> nguoiPhanAnh;

   @FXML
   private TableColumn<PhanAnhKienNghi, String> noiDung;

   @FXML
   private TableColumn<PhanAnhKienNghi, LocalDate> ngayGui;

   @FXML
   private TableColumn<PhanAnhKienNghi, String> trangThai;

   @FXML
   private TableColumn<PhanAnhKienNghi, String> capPhanHoi;

   @FXML
   private TableColumn<PhanAnhKienNghi, String> phanHoi;

   @FXML
   private TableColumn<PhanAnhKienNghi, LocalDate> ngayPhanHoi;

   @FXML
   private TextField searchField;

   @FXML
   private Button editButton;

   @FXML
   private Button delButton;

   @FXML
   private Button addButton;

   @FXML
   private Button updButton;

   @FXML
   private Button tKButton;

   private PhanAnhKienNghi selectPAKN;

   public PhanAnhKienNghi getSelectPAKN() {
      return selectPAKN;
   }

   public void setSelectPAKN(PhanAnhKienNghi selectPAKN) {
      this.selectPAKN = selectPAKN;
   }

   private ObservableList<PhanAnhKienNghi> paknList;
   private List<PhanAnhKienNghi> pAList = new ArrayList<PhanAnhKienNghi>();

   @Override
   public void initialize(URL arg0, ResourceBundle arg1) {

      try {
         Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
               SQLController.PASSWORD);
         Statement stmt = conn.createStatement();
         String query = "SELECT MaPA, HoTen, NoiDung, NgayPA, TrangThai, CapPhanHoi, PhanHoi, NgayPhanHoi FROM dbo.PhanAnhKienNghi INNER JOIN dbo.NhanKhau ON NhanKhau.MaNhanKhau = PhanAnhKienNghi.MaNhanKhau";
         ResultSet rs = stmt.executeQuery(query);

         while (rs.next()) {
            LocalDate ngayPhanHoi = rs.getDate(8) == null ? null : rs.getDate(8).toLocalDate();
            pAList.add(new PhanAnhKienNghi(rs.getString(1), new NhanKhau(rs.getNString(2)), rs.getNString(3),
                  rs.getDate(4).toLocalDate(), rs.getNString(5), rs.getNString(6), rs.getNString(7), ngayPhanHoi));

         }
      } catch (Exception e) {
         e.printStackTrace();
      }

      paknList = FXCollections.observableArrayList(
            pAList

      );
      sTT.setCellValueFactory(column -> new ReadOnlyObjectWrapper(table.getItems().indexOf(column.getValue()) + 1));
      maPA.setCellValueFactory(new PropertyValueFactory<PhanAnhKienNghi, String>("maPA"));
      nguoiPhanAnh.setCellValueFactory(new PropertyValueFactory<PhanAnhKienNghi, String>("ten"));
      noiDung.setCellValueFactory(new PropertyValueFactory<PhanAnhKienNghi, String>("noiDung"));
      ngayGui.setCellValueFactory(new PropertyValueFactory<PhanAnhKienNghi, LocalDate>("ngayPA"));
      trangThai.setCellValueFactory(new PropertyValueFactory<PhanAnhKienNghi, String>("trangThai"));
      capPhanHoi.setCellValueFactory(new PropertyValueFactory<PhanAnhKienNghi, String>("capPhanHoi"));
      phanHoi.setCellValueFactory(new PropertyValueFactory<PhanAnhKienNghi, String>("phanHoi"));
      ngayPhanHoi.setCellValueFactory(new PropertyValueFactory<PhanAnhKienNghi, LocalDate>("ngayPhanHoi"));
      table.setItems(paknList);

      BooleanBinding isSelected = table.getSelectionModel().selectedItemProperty().isNull();
      delButton.disableProperty().bind(isSelected);
      editButton.disableProperty().bind(isSelected);
      updButton.disableProperty().bind(isSelected);

      searchField.textProperty().addListener((observable, oldValue, newValue) -> {
         if (searchField.getText().isEmpty()) {
            table.setItems(paknList);
         } else {
            String searchInfo = searchField.getText();
            List<PhanAnhKienNghi> searchResult = new ArrayList<PhanAnhKienNghi>();
            try {
               Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                     SQLController.PASSWORD);
               Statement stmt = conn.createStatement();
               String query = "SELECT MaPA, HoTen, NoiDung, NgayPA, TrangThai, CapPhanHoi, PhanHoi, NgayPhanHoi FROM dbo.PhanAnhKienNghi INNER JOIN dbo.NhanKhau ON NhanKhau.MaNhanKhau = PhanAnhKienNghi.MaNhanKhau"
                     + " WHERE MaPA LIKE '%" + searchInfo + "%' OR HoTen LIKE N'%" + searchInfo + "%'";
               System.out.println(query);
               ResultSet rs = stmt.executeQuery(query);
               while (rs.next()) {
                  LocalDate ngayPhanHoi = rs.getDate(8) == null ? null : rs.getDate(8).toLocalDate();
                  searchResult.add(new PhanAnhKienNghi(rs.getString(1), new NhanKhau(rs.getNString(2)),
                        rs.getNString(3), rs.getDate(4).toLocalDate(), rs.getNString(5), rs.getNString(6),
                        rs.getNString(7), ngayPhanHoi));
               }
               System.out.println("wtf");
               conn.close();

            } catch (Exception esss) {
               esss.printStackTrace();
            }

            ObservableList<PhanAnhKienNghi> searchedPAKNList;
            searchedPAKNList = FXCollections.observableArrayList(searchResult);
            table.setItems(searchedPAKNList);
         }
      });

   }

   @FXML
   protected void addEvent(ActionEvent e) throws IOException {
      Stage addStage = new Stage();
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("ThemPhanAnhKienNghi.fxml"));
      Parent root = loader.load();
      ThemPAKNController controller = loader.getController();
      controller.setpAKNController(this);
      Scene scene = new Scene(root);
      addStage.setScene(scene);
      addStage.show();
   }

   @FXML
   protected void deleteEvent(ActionEvent e) throws IOException, SQLException {
      PhanAnhKienNghi selected = table.getSelectionModel().getSelectedItem();
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Cofirmation");
      alert.setHeaderText("Bạn muốn xóa phán ánh kiến nghị " + selected.getMaPA());
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
         Statement stmt = conn.createStatement();
         String query = "DELETE dbo.PhanAnhKienNghi WHERE MaPA = '" + selected.getMaPA() + "'";
         stmt.execute(query);
         conn.close();
         String message = "Xóa phán ánh kiến nghị " + selected.getMaPA() + " thành công";
         Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
         alert1.setTitle("Information");
         alert1.setHeaderText("Notification");
         alert1.setContentText(message);
         alert1.show();
         paknList.remove(selected);
      }
   }

   @FXML
   protected void editEvent(ActionEvent e) throws IOException {
      PhanAnhKienNghi selected = table.getSelectionModel().getSelectedItem();
      this.setSelectPAKN(selected);
      Stage addStage = new Stage();
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("SuaPhanAnhKienNghi.fxml"));
      Parent root = loader.load();
      SuaPAKNController controller = loader.getController();
      controller.setpAKNController(this);
      controller.setpAKNEdit(selected);
      controller.maPALabel.setText(selected.getMaPA());
      controller.hoTenLabel.setText(selected.getNguoiPA().getHoTen());
      controller.noidungArea.setText(selected.getNoiDung());

      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
      addStage.setScene(scene);
      addStage.show();
   }

   @FXML
   protected void updateEvent(ActionEvent e) throws IOException {
      PhanAnhKienNghi selected = table.getSelectionModel().getSelectedItem();
      this.setSelectPAKN(selected);
      Stage addStage = new Stage();
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("CapNhatPhanHoi.fxml"));
      Parent root = loader.load();
      CapNhatPhanHoiController controller = loader.getController();
      controller.setpAKNController(this);
      selected.setpAKNEdit(controller);
      controller.maPALabel.setText(selected.getMaPA());
      controller.hoTenLabel.setText(selected.getNguoiPA().getHoTen());
      controller.ngayPALabel.setText(selected.getNgayPA().toString());

      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
      addStage.setScene(scene);
      addStage.show();
   }

   @FXML
   protected void tKEvent(ActionEvent e) throws IOException {
      Stage stage = new Stage();
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("ThongKePAKN.fxml"));
      Parent root = loader.load();
      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
      stage.setScene(scene);
      stage.show();
   }

   public void addList(PhanAnhKienNghi phanAnhKienNghi) {
      paknList.add(phanAnhKienNghi);
   }

   public void editList(PhanAnhKienNghi cu, PhanAnhKienNghi moi) {
      int index = paknList.indexOf(cu);
      paknList.set(index, moi);
   }

   public PhanAnhKienNghi getpAKNEdit(CapNhatPhanHoiController capNhatPhanHoiController) {
      return capNhatPhanHoiController.pAKNEdit;
   }

}