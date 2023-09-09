package Models;

import java.time.LocalDate;

import Controller.CapNhatPhanHoiController;

public class PhanAnhKienNghi {
    private String maPA;
    private NhanKhau nguoiPA;
    private String noiDung;
    private LocalDate ngayPA;
    private String trangThai;
    private String capPhanHoi;
    private String phanHoi;
    private LocalDate ngayPhanHoi;

    public PhanAnhKienNghi(String maPA, NhanKhau nguoiPA, String noiDung, LocalDate ngayPA, String trangThai,
            String capPhanAnh, String phanHoi, LocalDate ngayPhanHoi) {
        this.maPA = maPA;
        this.nguoiPA = nguoiPA;
        this.noiDung = noiDung;
        this.ngayPA = ngayPA;
        this.trangThai = trangThai;
        this.capPhanHoi = capPhanAnh;
        this.phanHoi = phanHoi;
        this.ngayPhanHoi = ngayPhanHoi;
    }

    public String getMaPA() {
        return maPA;
    }

    public void setMaPA(String maPA) {
        this.maPA = maPA;
    }

    public NhanKhau getNguoiPA() {
        return nguoiPA;
    }

    public void setNguoiPA(NhanKhau nguoiPA) {
        this.nguoiPA = nguoiPA;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public LocalDate getNgayPA() {
        return ngayPA;
    }

    public void setNgayPA(LocalDate ngayPA) {
        this.ngayPA = ngayPA;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getCapPhanHoi() {
        return capPhanHoi;
    }

    public void setCapPhanHoi(String capPhanHoi) {
        this.capPhanHoi = capPhanHoi;
    }

    public String getPhanHoi() {
        return phanHoi;
    }

    public void setPhanHoi(String phanHoi) {
        this.phanHoi = phanHoi;
    }

    public LocalDate getNgayPhanHoi() {
        return ngayPhanHoi;
    }

    public void setNgayPhanHoi(LocalDate ngayPhanHoi) {
        this.ngayPhanHoi = ngayPhanHoi;
    }

    public String getTen() {
        return nguoiPA.getHoTen();
    }

    public void setpAKNEdit(CapNhatPhanHoiController capNhatPhanHoiController) {
        capNhatPhanHoiController.pAKNEdit = this;
    }

}
