package Controller;

import Models.ThanhVienCuaHo;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/*
 * @author Vo Hoai Nam 4592
 * @version 1.0 11/2/2023
 * Class 136813, Teacher's name Trung.TT
 */
public class XemThanhVienController implements Initializable {

   @FXML
   private TableView<ThanhVienCuaHo> table;

   @FXML
   private TableColumn<ThanhVienCuaHo, Integer> sTT;

   @FXML
   private TableColumn<ThanhVienCuaHo, String> maNhanKhau;

   @FXML
   private TableColumn<ThanhVienCuaHo, String> hoTen;

   @FXML
   private TableColumn<ThanhVienCuaHo, String> cCCD;

   @FXML
   private TableColumn<ThanhVienCuaHo, LocalDate> ngaySinh;

   @FXML
   private TableColumn<ThanhVienCuaHo, String> queQuan;

   @FXML
   private TableColumn<ThanhVienCuaHo, String> quanHeVoiCH;

   @FXML
   private TableColumn<ThanhVienCuaHo, String> thuongTruTruoc;

   @FXML
   private TableColumn<ThanhVienCuaHo, LocalDate> ngayChuyenDi;

   @FXML
   private TableColumn<ThanhVienCuaHo, String> noiChuyenToi;

   @FXML
   private TableColumn<ThanhVienCuaHo, String> ghiChu;

   @FXML
   Label maHoKhauLabel;

   @FXML
   Label diaChiLabel;

   private String maHoKhau;

   public String getMaHoKhau() {
      return maHoKhau;
   }

   public void setMaHoKhau(String maHoKhau) {
      this.maHoKhau = maHoKhau;
   }

   @Override
   public void initialize(URL arg0, ResourceBundle arg1) {
      maHoKhauLabel.textProperty().addListener((observable, oldValue, newValue) -> {
         try {
            System.out.println("|" + maHoKhauLabel.getText() + "|");
            List<ThanhVienCuaHo> tV = new ArrayList<ThanhVienCuaHo>();
            ObservableList<ThanhVienCuaHo> tVList;
            Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                  SQLController.PASSWORD);
            Statement stmt = conn.createStatement();
            String query = "SELECT NK.MaNhanKhau, NK.HoTen, NK.CCCD, NK.NgaySinh, NK.QueQuan, TV.QuanHeVoiCH, TV.NoiThuongTruTruoc, TV.NgayChuyenDi, TV.NoiChuyenToi,"
                  + " TV.GhiChu FROM dbo.ThanhVienCuaHo AS TV INNER JOIN dbo.NhanKhau AS NK ON NK.MaNhanKhau = TV.MaNhanKhau WHERE TV.MaHoKhau = '"
                  + newValue.toString().trim() + "'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
               LocalDate ngaySinh = rs.getDate(4) == null ? null : rs.getDate(4).toLocalDate();
               LocalDate ngayChuyenDi = rs.getDate(8) == null ? null : rs.getDate(8).toLocalDate();
               tV.add(new ThanhVienCuaHo(rs.getString(1), rs.getNString(2), rs.getString(3), ngaySinh, rs.getNString(5),
                     rs.getNString(6),
                     rs.getNString(7), ngayChuyenDi, rs.getNString(9), rs.getNString(10)));
            }

            tVList = FXCollections.observableArrayList(tV);
            sTT.setCellValueFactory(
                  column -> new ReadOnlyObjectWrapper(table.getItems().indexOf(column.getValue()) + 1));
            maNhanKhau.setCellValueFactory(new PropertyValueFactory<ThanhVienCuaHo, String>("maNhanKhau"));
            hoTen.setCellValueFactory(new PropertyValueFactory<ThanhVienCuaHo, String>("hoTen"));
            cCCD.setCellValueFactory(new PropertyValueFactory<ThanhVienCuaHo, String>("cccd"));
            ngaySinh.setCellValueFactory(new PropertyValueFactory<ThanhVienCuaHo, LocalDate>("ngaySinh"));
            queQuan.setCellValueFactory(new PropertyValueFactory<ThanhVienCuaHo, String>("queQuan"));
            quanHeVoiCH.setCellValueFactory(new PropertyValueFactory<ThanhVienCuaHo, String>("quanHeVoiChuHo"));
            thuongTruTruoc.setCellValueFactory(new PropertyValueFactory<ThanhVienCuaHo, String>("noithuongtrutruoc"));
            ngayChuyenDi.setCellValueFactory(new PropertyValueFactory<ThanhVienCuaHo, LocalDate>("ngayChuyenDi"));
            noiChuyenToi.setCellValueFactory(new PropertyValueFactory<ThanhVienCuaHo, String>("noiChuyenDi"));
            ghiChu.setCellValueFactory(new PropertyValueFactory<ThanhVienCuaHo, String>("ghiChu"));

            table.setItems(tVList);
            conn.close();
         } catch (Exception e) {
            // TODO: handle exception
         }
      });

   }

}