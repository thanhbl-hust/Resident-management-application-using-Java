package Models;

import java.time.LocalDate;

public class TamTru {
   private String maTamTru;
   private NhanKhau nguoitamtru;
   private String noiTamTru;
   private LocalDate tuNgay;
   private LocalDate denNgay;
   private String lyDo;

   public TamTru(String maTamTru, NhanKhau nguoitamtru, String noiTamTru, LocalDate tuNgay, LocalDate denNgay,
         String lyDo) {
      this.maTamTru = maTamTru;
      this.nguoitamtru = nguoitamtru;
      this.noiTamTru = noiTamTru;
      this.tuNgay = tuNgay;
      this.denNgay = denNgay;
      this.lyDo = lyDo;
   }

   public String getMaTamTru() {
      return maTamTru;
   }

   public void setMaTamTru(String maTamTru) {
      this.maTamTru = maTamTru;
   }

   public NhanKhau getNguoitamtru() {
      return nguoitamtru;
   }

   public void setNguoitamtru(NhanKhau nguoitamtru) {
      this.nguoitamtru = nguoitamtru;
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

   public String getHoten() {
      return nguoitamtru.getHoTen();
   }

}
