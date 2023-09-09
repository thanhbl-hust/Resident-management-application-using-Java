package Models;

import java.time.LocalDate;

/*
 * @author Vo Hoai Nam 4592
 * @version 1.0 11/2/2023
 * Class 136813, Teacher's name Trung.TT
 */
public class ThanhVienCuaHo extends NhanKhau {
    private String idNhanKhau;
    private String hoTen;
    private String idHoKhau;
    private String quanHeVoiChuHo;
    private String noithuongtrutruoc;
    private LocalDate ngayChuyenDi;
    private String noiChuyenDi;
    private String ghiChu;
    private int idTrongHoKhau;

    public ThanhVienCuaHo(String maNhanKhau, String hoTen, String cccd, LocalDate ngaySinh, String queQuan,
            String quanHeVoiChuHo, String noithuongtrutruoc, LocalDate ngayChuyenDi, String noiChuyenDi,
            String ghiChu) {
        super(maNhanKhau, hoTen, cccd, ngaySinh, queQuan);
        this.quanHeVoiChuHo = quanHeVoiChuHo;
        this.noithuongtrutruoc = noithuongtrutruoc;
        this.ngayChuyenDi = ngayChuyenDi;
        this.noiChuyenDi = noiChuyenDi;
        this.ghiChu = ghiChu;
        this.hoTen = hoTen;
    }

    public ThanhVienCuaHo(String hoTen, String quanHeVoiChuHo) {
        this.hoTen = hoTen;
        this.quanHeVoiChuHo = quanHeVoiChuHo;
    }

    public ThanhVienCuaHo(String idNhanKhau, String hoTen, String idHoKhau, String quanHeVoiChuHo,
            String noithuongtrutruoc, LocalDate ngayChuyenDi, String noiChuyenDi, String ghiChu, int idTrongHoKhau) {
        this.idNhanKhau = idNhanKhau;
        this.hoTen = hoTen;
        this.idHoKhau = idHoKhau;
        this.quanHeVoiChuHo = quanHeVoiChuHo;
        this.noithuongtrutruoc = noithuongtrutruoc;
        this.ngayChuyenDi = ngayChuyenDi;
        this.noiChuyenDi = noiChuyenDi;
        this.ghiChu = ghiChu;
        this.idTrongHoKhau = idTrongHoKhau;
    }

    public ThanhVienCuaHo(String idNhanKhau, String hoTen, String idHoKhau, String quanHeVoiChuHo,
            String noithuongtrutruoc, int idTrongHoKhau) {
        this.idNhanKhau = idNhanKhau;
        this.hoTen = hoTen;
        this.idHoKhau = idHoKhau;
        this.quanHeVoiChuHo = quanHeVoiChuHo;
        this.noithuongtrutruoc = noithuongtrutruoc;
        this.idTrongHoKhau = idTrongHoKhau;
    }

    public ThanhVienCuaHo(String hoTen, String quanHeVoiChuHo, String ghiChu) {
        this.hoTen = hoTen;
        this.quanHeVoiChuHo = quanHeVoiChuHo;
        this.ghiChu = ghiChu;
    }

    public ThanhVienCuaHo() {
    }

    public LocalDate getNgayChuyenDi() {
        return ngayChuyenDi;
    }

    public void setNgayChuyenDi(LocalDate ngayChuyenDi) {
        this.ngayChuyenDi = ngayChuyenDi;
    }

    public String getNoiChuyenDi() {
        return noiChuyenDi;
    }

    public void setNoiChuyenDi(String noiChuyenDi) {
        this.noiChuyenDi = noiChuyenDi;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getNoithuongtrutruoc() {
        return noithuongtrutruoc;
    }

    public void setNoithuongtrutruoc(String noithuongtrutruoc) {
        this.noithuongtrutruoc = noithuongtrutruoc;
    }

    public String getIdHoKhau() {
        return idHoKhau;
    }

    public void setIdHoKhau(String idHoKhau) {
        this.idHoKhau = idHoKhau;
    }

    public String getQuanHeVoiChuHo() {
        return quanHeVoiChuHo;
    }

    public void setQuanHeVoiChuHo(String quanHeVoiChuHo) {
        this.quanHeVoiChuHo = quanHeVoiChuHo;
    }

    public String getIdNhanKhau() {
        return idNhanKhau;
    }

    public void setIdNhanKhau(String idNhanKhau) {
        this.idNhanKhau = idNhanKhau;
    }

    public int getIdTrongHoKhau() {
        return idTrongHoKhau;
    }

    public void setIdTrongHoKhau(int idTrongHoKhau) {
        this.idTrongHoKhau = idTrongHoKhau;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

}
