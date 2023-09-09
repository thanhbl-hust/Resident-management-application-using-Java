package Models;

import java.time.LocalDate;

/*
 * @author Vo Hoai Nam 4592
 * @version 1.0 11/2/2023
 * Class 136813, Teacher's name Trung.TT
 */
public class NhanKhau {
   private String maNhanKhau;
   private String hoTen;
   private String biDanh;
   private String cccd;
   private LocalDate ngaySinh;
   private String gioiTinh;
   private String queQuan;
   private String thuongTru;
   private String danToc;
   private String ngheNghiep;
   private String noiLamViec;

   public NhanKhau(String maNhanKhau, String hoTen, String cccd, LocalDate ngaySinh, String queQuan) {
      this.maNhanKhau = maNhanKhau;
      this.hoTen = hoTen;
      this.cccd = cccd;
      this.ngaySinh = ngaySinh;
      this.queQuan = queQuan;
   }

   public NhanKhau() {

   }

   public NhanKhau(String maNhanKhau, String hoTen, String biDanh, String cccd, LocalDate ngaySinh, String gioiTinh,
         String queQuan, String thuongTru, String danToc, String ngheNghiep, String noiLamViec) {
      this.maNhanKhau = maNhanKhau;
      this.hoTen = hoTen;
      this.biDanh = biDanh;
      this.cccd = cccd;
      this.ngaySinh = ngaySinh;
      this.gioiTinh = gioiTinh;
      this.queQuan = queQuan;
      this.thuongTru = thuongTru;
      this.danToc = danToc;
      this.ngheNghiep = ngheNghiep;
      this.noiLamViec = noiLamViec;
   }

   public NhanKhau(String hoTen) {
      this.hoTen = hoTen;
   }

   public NhanKhau(String maNhanKhau, String hoTen, String cccd, LocalDate ngaySinh, String gioiTinh, String queQuan,
         String thuongTru, String danToc, String ngheNghiep) {
      this.maNhanKhau = maNhanKhau;
      this.hoTen = hoTen;
      this.cccd = cccd;
      this.ngaySinh = ngaySinh;
      this.gioiTinh = gioiTinh;
      this.queQuan = queQuan;
      this.thuongTru = thuongTru;
      this.danToc = danToc;
      this.ngheNghiep = ngheNghiep;
   }

   public String getMaNhanKhau() {
      return maNhanKhau;
   }

   public void setMaNhanKhau(String maNhanKhau) {
      this.maNhanKhau = maNhanKhau;
   }

   public String getHoTen() {
      return hoTen;
   }

   public void setHoTen(String hoTen) {
      this.hoTen = hoTen;
   }

   public String getBiDanh() {
      return biDanh;
   }

   public void setBiDanh(String biDanh) {
      this.biDanh = biDanh;
   }

   public String getCccd() {
      return cccd;
   }

   public void setCccd(String cccd) {
      this.cccd = cccd;
   }

   public LocalDate getNgaySinh() {
      return ngaySinh;
   }

   public void setNgaySinh(LocalDate ngaySinh) {
      this.ngaySinh = ngaySinh;
   }

   public String getGioiTinh() {
      return gioiTinh;
   }

   public void setGioiTinh(String gioiTinh) {
      this.gioiTinh = gioiTinh;
   }

   public String getQueQuan() {
      return queQuan;
   }

   public void setQueQuan(String queQuan) {
      this.queQuan = queQuan;
   }

   public String getThuongTru() {
      return thuongTru;
   }

   public void setThuongTru(String thuongTru) {
      this.thuongTru = thuongTru;
   }

   public String getDanToc() {
      return danToc;
   }

   public void setDanToc(String danToc) {
      this.danToc = danToc;
   }

   public String getNgheNghiep() {
      return ngheNghiep;
   }

   public void setNgheNghiep(String ngheNghiep) {
      this.ngheNghiep = ngheNghiep;
   }

   public String getNoiLamViec() {
      return noiLamViec;
   }

   public void setNoiLamViec(String noiLamViec) {
      this.noiLamViec = noiLamViec;
   }

}
