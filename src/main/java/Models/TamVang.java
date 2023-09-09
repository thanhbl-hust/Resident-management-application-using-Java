package Models;

import java.time.LocalDate;

public class TamVang {
   private String maTamVang;
   private NhanKhau nguoiTamVang;
   private String noiTamTru;
   private LocalDate tuNgay;
   private LocalDate denNgay;
   private String lyDo;

   public TamVang(String maTamVang, NhanKhau nguoiTamVang, String noiTamTru, LocalDate tuNgay,
         LocalDate denNgay, String lyDo) {
      this.maTamVang = maTamVang;
      this.nguoiTamVang = nguoiTamVang;
      this.noiTamTru = noiTamTru;
      this.tuNgay = tuNgay;
      this.denNgay = denNgay;
      this.lyDo = lyDo;
   }

   public String getMaTamVang() {
      return maTamVang;
   }

   public void setMaTamVang(String maTamVang) {
      this.maTamVang = maTamVang;
   }

   public NhanKhau getNguoiTamVang() {
      return nguoiTamVang;
   }

   public void setNguoiTamVang(NhanKhau nguoiTamVang) {
      this.nguoiTamVang = nguoiTamVang;
   }

   public String getNoiTamTru() {
      return noiTamTru;
   }

   public void setNoiTamTru(String noiTamTru) {
      this.noiTamTru = noiTamTru;
   }

   public LocalDate getTuNgay() {
      return tuNgay;
   }

   public void setTuNgay(LocalDate tuNgay) {
      this.tuNgay = tuNgay;
   }

   public LocalDate getDenNgay() {
      return denNgay;
   }

   public void setDenNgay(LocalDate denNgay) {
      this.denNgay = denNgay;
   }

   public String getLyDo() {
      return lyDo;
   }

   public void setLyDo(String lyDo) {
      this.lyDo = lyDo;
   }

   public String getHoTen() {
      return nguoiTamVang.getHoTen();
   }

}
