package Models;

/*
 * @author Vo Hoai Nam 4592
 * @version 1.0 11/2/2023
 * Class 136813, Teacher's name Trung.TT
 */
public class HoKhau {
   private String idHoKhau;
   private String hoTenChuHo;
   private String maNKChuHo;
   private String cCCDChuHo;
   private String diaChiHo;

   public HoKhau(String idHoKhau, String hoTenChuHo, String maNKChuHo, String cCCDChuHo, String diaChiHo) {
      this.idHoKhau = idHoKhau;
      this.hoTenChuHo = hoTenChuHo;
      this.maNKChuHo = maNKChuHo;
      this.cCCDChuHo = cCCDChuHo;
      this.diaChiHo = diaChiHo;
   }

   public String getIdHoKhau() {
      return idHoKhau;
   }

   public void setIdHoKhau(String idHoKhau) {
      this.idHoKhau = idHoKhau;
   }

   public String getHoTenChuHo() {
      return hoTenChuHo;
   }

   public void setHoTenChuHo(String hoTenChuHo) {
      this.hoTenChuHo = hoTenChuHo;
   }

   public String getDiaChiHo() {
      return diaChiHo;
   }

   public void setDiaChiHo(String diaChiHo) {
      this.diaChiHo = diaChiHo;
   }

   public String getHoTen() {
      return this.hoTenChuHo;
   }

   public String getMaNKChuHo() {
      return maNKChuHo;
   }

   public void setMaNKChuHo(String maNKChuHo) {
      this.maNKChuHo = maNKChuHo;
   }

   public String getCCCDChuHo() {
      return cCCDChuHo;
   }

   public void setCCCDChuHo(String cCCDChuHo) {
      this.cCCDChuHo = cCCDChuHo;
   }
}
