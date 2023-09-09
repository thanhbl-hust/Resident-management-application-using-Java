CREATE DATABASE QLDC
GO

--tạo các bảng

--tao bang user
CREATE TABLE TaiKhoan(
ID INT PRIMARY KEY,
TaiKhoan VARCHAR(20) NOT NULL,
MatKhau VARCHAR(20) NOT NULL
)

-- tao bang nhan khau
CREATE TABLE NhanKhau (
MaNhanKhau VARCHAR(20) NOT NULL PRIMARY KEY,
HoTen NVARCHAR(50) NOT NULL,
BiDanh NVARCHAR(50),
CCCD VARCHAR(20) NOT NULL,
NgaySinh DATE ,
GioiTinh NVARCHAR(6),
QueQuan NVARCHAR(200),
ThuongTru NVARCHAR(200),
Dantoc NVARCHAR(20),
NgheNghiep NVARCHAR(50),
NoiLamViec NVARCHAR(200)
)


--tao bang Ho Khau
CREATE TABLE HoKhau(
MaHoKhau VARCHAR(10) PRIMARY KEY,
MaNKChuHo VARCHAR(20) NOT NULL,
Diachi NVARCHAR(200) NOT NULL
)

--tao bang Thanh Vien cua Ho
CREATE TABLE ThanhVienCuaHo(
MaNhanKhau VARCHAR(20) NOT NULL,
MaHoKhau VARCHAR(10) NOT NULL,
QuanHeVoiCH NVARCHAR(10),
NoiThuongTruTruoc NVARCHAR(200),
NgayChuyenDi DATE,
NoiChuyenToi NVARCHAR(200),
GhiChu NVARCHAR(200),
MaTrongHoKhau INT NOT NULL,
PRIMARY KEY(MaNhanKhau, MaHoKhau)
)


--tao bang Tam Tru
CREATE TABLE TamTru(
ID VARCHAR(10) PRIMARY KEY,
MaNhanKhau VARCHAR(20) NOT NULL,
NoiTamTru NVARCHAR(200) NOT NULL,
TuNgay DATE,
DenNgay DATE,
LyDo NVARCHAR(100)
)


--tao bang Tam Vang
CREATE TABLE TamVang(
ID VARCHAR(10) PRIMARY KEY,
MaNhanKhau VARCHAR(20) NOT NULL,
NoiTamTru NVARCHAR(200) NOT NULL,
TuNgay DATE,
DenNgay DATE,
LyDo NVARCHAR(100)
)

--tao bang Phan Anh Kien Nghi
CREATE TABLE PhanAnhKienNghi(
MaPA VARCHAR(20) PRIMARY KEY,
MaNhanKhau VARCHAR(20) NOT NULL,
NoiDung NVARCHAR(1000) NOT NULL,
NgayPA DATE NOT NULL,
TrangThai NVARCHAR(30) NOT NULL,
CapPhanHoi NVARCHAR(50),
PhanHoi NVARCHAR(1000),
NgayPhanHoi DATE
)


--tao cac rang buoc
ALTER TABLE dbo.HoKhau
ADD	 FOREIGN KEY (MaNKChuHo) REFERENCES NhanKhau(MaNhanKhau)

ALTER TABLE dbo.TamTru
ADD FOREIGN KEY (MaNhanKhau) REFERENCES dbo.NhanKhau(MaNhanKhau)

ALTER TABLE	dbo.TamVang
ADD FOREIGN KEY (MaNhanKhau) REFERENCES dbo.NhanKhau(MaNhanKhau)

ALTER	TABLE dbo.PhanAnhKienNghi
ADD	FOREIGN KEY (MaNhanKhau) REFERENCES dbo.NhanKhau(MaNhanKhau)

ALTER TABLE dbo.ThanhVienCuaHo
ADD FOREIGN KEY (MaNhanKhau) REFERENCES dbo.NhanKhau(MaNhanKhau)

ALTER TABLE dbo.ThanhVienCuaHo
ADD FOREIGN KEY (MaHoKhau) REFERENCES dbo.HoKhau(MaHoKhau)




INSERT INTO dbo.NhanKhau (MaNhanKhau,HoTen,BiDanh, CCCD, NgaySinh, GioiTinh, QueQuan, ThuongTru, Dantoc, NgheNghiep, NoiLamViec)
VALUES
('NK.00001', N'Võ Hoài Nam',N'VhNam', '0000000001', '2002-04-20', N'Nam', N'Kỳ Thư - Kỳ Anh - Hà Tĩnh', N'Số 2 Tạ Quang Bửu- Hai Bà Trưng- Hà Nội',N'Kinh', N'Sinh viên', N'Đại học Bách Khoa Hà Nội'),
('NK.00002', N'Nguyễn Thị Hồng',N'Hồng', '0000000002', '1995-08-12', N'Nữ', N'Phú Vang - Thừa Thiên Huế', N'Trần Phú - Hà Đông - Hà Nội',N'Kinh', N'Nhân viên kinh doanh', N'CTY TNHH ABC'),
('NK.00003', N'Trần Văn Tân',N'Tân', '0000000003', '1980-05-10', N'Nam', N'Thạch Trị - Bố Trạch - Quảng Bình', N'Trung Tự - Đống Đa - Hà Nội',N'Kinh', N'Giáo viên', N'Trường THCS Tự Lập'),
('NK.00004', N'Đặng Văn Tùng',N'Tùng', '0000000004', '1945-09-01', N'Nam', N'Phú Yên - Sơn Tây - Quảng Bình', N'Phương Liệt - Thanh Xuân - Hà Nội',N'Kinh', N'Tài xế', N'Công ty Vận tải XYZ'),
('NK.00005', N'Nguyễn Thị Ngọc',N'Ngọc', '0000000005', '1988-02-14', N'Nữ', N'Hương Xuân - Hương Trà - Thừa Thiên Huế', N'Khuất Duy Tiến - Thanh Xuân - Hà Nội',N'Kinh', N'Kế toán', N'Công ty TNHH Kế toán ABC'),
('NK.00006', N'Trần Văn An',N'An', '0000000006', '2004-11-18', N'Nam', N'Thái Phúc - Đông Hà - Quảng Trị', N'Lê Trọng Tấn - Thanh Xuân - Hà Nội',N'Kinh', N'Học sinh', N'Trường THPT Chuyên Hà Nội'),
('NK.00007', N'Lê Thị Hoa',N'Hoa', '0000000007', '1999-03-22', N'Nữ', N'Lý Thái Tổ - Hoàn Kiếm - Hà Nội', N'Trần Duy Hưng - Cầu Giấy - Hà Nội',N'Kinh', N'Sinh viên', N'Đại học Ngoại Thương'),
('NK.00008', N'Nguyễn Văn A',N'A A', '0000000008', '1990-03-15', N'Nam', N'Hải Dương - Việt Nam', N'Thành phố Hà Nội',N'Kinh', N'Nhân viên văn phòng', N'Văn phòng công ty ABC'),
('NK.00009', N'Nguyễn Thị B',N'B B', '0000000009', '1995-12-10', N'Nữ', N'Quảng Bình - Việt Nam', N'Thành phố Đà Nẵng',N'Kinh', N'Sinh viên', N'Đại học Ngoại Thương - Hà Nội'),
('NK.00010', N'Hoàng Văn C',N'C C', '0000000010', '1985-02-25', N'Nam', N'Hà Tĩnh - Việt Nam', N'Thành phố Hồ Chí Minh',N'Kinh', N'Giáo viên', N'Trường THPT Nguyễn Trãi - Hà Nội'),
('NK.00011', N'Phạm Thị D',N'D D', '0000000011', '1998-08-08', N'Nữ', N'Hải Phòng - Việt Nam', N'Thành phố Hà Nội',N'Kinh', N'Sinh viên', N'Đại học Khoa Học Xã Hội và Nhân Văn - ĐHQGHN'),
('NK.00012', N'Nguyễn Minh Tâm',N'Tâm Tâm', '0000000012', '1992-09-20', N'Nam', N'Thừa Thiên Huế - Việt Nam', N'Thành phố Hà Nội',N'Kinh', N'Kỹ thuật viên', N'Công ty TNHH XYZ'),
('NK.00013', N'Nguyễn Hoàng Nam',N'Nam Nam', '0000000013', '1995-11-05', N'Nam', N'Vĩnh Long - Việt Nam', N'Thành phố Hồ Chí Minh',N'Kinh', N'Sinh viên', N'Đại học Kinh Tế Quốc Dân - ĐHQGHN'),
('NK.00014', N'Lê Hoàng Anh',N'Anh', '0000000014', '1997-05-12', N'Nam', N'Thanh Khê - Đà Nẵng', N'Số 45 Điện Biên Phủ - Ba Đình - Hà Nội',N'Kinh', N'Sinh viên', N'Đại học Công nghệ - ĐHQG Hà Nội'),
('NK.00015', N'Nguyễn Thị Anh',N'Anh', '0000000015', '1995-06-23', N'Nữ', N'Cầu Giấy - Hà Nội', N'Số 1 Tôn Thất Thuyết - Nam Từ Liêm - Hà Nội',N'Kinh', N'Kế toán', N'Công ty TNHH Tư vấn Đầu tư Xây dựng An Viên'),
('NK.00016', N'Trần Văn An',N'An', '0000000016', '1994-07-11', N'Nam', N'Đức Thọ - Hà Tĩnh', N'Số 8 Hoàng Cầu - Đống Đa - Hà Nội',N'Kinh', N'Kỹ sư', N'Tập đoàn Tân Hiệp Phát'),
('NK.00017', N'Phạm Văn Bình',N'Bình', '0000000017', '2001-02-14', N'Nam', N'Tân Kỳ - Nghệ An', N'Số 15 Nguyễn Trãi - Thanh Xuân - Hà Nội',N'Kinh', N'Sinh viên', N'Đại học Kinh tế Quốc dân'),
('NK.00018', N'Nguyễn Thị Thùy Dung',N'Dung', '0000000018', '1998-11-09', N'Nữ', N'Cẩm Xuyên - Hà Tĩnh', N'Số 6 Ngõ Văn Chương - Đống Đa - Hà Nội',N'Kinh', N'Sinh viên', N'Đại học Ngoại thương'),
('NK.00019', N'Lê Đức Đạt',N'Đạt', '0000000019', '2000-01-06', N'Nam', N'Yên Thành - Nghệ An', N'Số 10 Trần Duy Hưng - Cầu Giấy - Hà Nội',N'Kinh', N'Sinh viên', N'Đại học Thủy lợi'),
('NK.00020', N'Lê Thanh Tùng',N'Tùng Tùng', '0000000020', '1999-05-16', N'Nam', N'Nghi Xuân - Hà Tĩnh', N'Thành Phố Vinh - Nghệ An',N'Kinh', N'Sinh viên', N'Đại học Khoa Học Tự Nhiên - ĐHQGHN')

INSERT INTO dbo.HoKhau (MaHoKhau, MaNKChuHo,Diachi) VALUES
('HK.00001','NK.00001', N'Số 2 Tạ Quang Bửu- Hai Bà Trưng- Hà Nội'),
('HK.00002','NK.00004', N'Số 10 Tạ Quang Bửu- Hai Bà Trưng- Hà Nội'),
('HK.00003','NK.00006', N'Số 2 Tạ Quang Bửu- Hai Bà Trưng- Hà Nội'),
('HK.00004','NK.00009', N'Số 10 Tạ Quang Bửu- Hai Bà Trưng- Hà Nội'),
('HK.00005','NK.00014', N'Số 20 Hoàng Hoa Thám- Tây Hồ- Hà Nội'),
('HK.00006','NK.00018', N'Số 30 Hoàng Cầu- Đống Đa- Hà Nội');


INSERT INTO dbo.ThanhVienCuaHo(MaNhanKhau,MaHoKhau,QuanHeVoiCH,NoiThuongTruTruoc, MaTrongHoKhau)VALUES
('NK.00001', 'HK.00001',  N'Chủ hộ',N'Kỳ Thư - Kỳ Anh - Hà Tĩnh', 1),
('NK.00002', 'HK.00001',  N'Vợ',N'Nguyễn Trãi- Hai Bà Trưng- Hà Nội', 2),
('NK.00003', 'HK.00001',  N'Con ruột',N'Nguyễn Trãi- Hai Bà Trưng- Hà Nội', 3),
('NK.00004', 'HK.00002', N'Chủ hộ',N'Nguyễn Trãi- Hai Bà Trưng- Hà Nội', 1),
('NK.00005', 'HK.00002', N'Vợ',N'Nguyễn Trãi- Hai Bà Trưng- Hà Nội', 2),
('NK.00006', 'HK.00003', N'Chủ hộ',N'Phạm Hùng- Hai Bà Trưng- Hà Nội', 1),
('NK.00007', 'HK.00003', N'Con ruột',N'Phạm Hùng- Hai Bà Trưng- Hà Nội', 2),
('NK.00008', 'HK.00003', N'Con ruột',N'Phạm Hùng- Hai Bà Trưng- Hà Nội', 3),
('NK.00009', 'HK.00004', N'Chủ hộ',N'Lê Văn Sỹ- Hai Bà Trưng- Hà Nội', 1),
('NK.00010', 'HK.00004', N'Vợ',N'Lê Văn Sỹ- Hai Bà Trưng- Hà Nội', 2),
('NK.00011', 'HK.00004', N'Con ruột',N'Phạm Hùng- Hai Bà Trưng- Hà Nội', 3),
('NK.00012', 'HK.00004', N'Con ruột',N'Phạm Hùng- Hai Bà Trưng- Hà Nội', 4),
('NK.00013', 'HK.00004', N'Con ruột',N'Phạm Hùng- Hai Bà Trưng- Hà Nội', 5),
('NK.00014', 'HK.00005', N'Chủ hộ',N'Trần Minh- Hai Bà Trưng- Hà Nội', 1),
('NK.00015', 'HK.00005', N'Vợ',N'Ngô Quốc Đạt- Hai Bà Trưng- Hà Nội', 2),
('NK.00016', 'HK.00005', N'Chủ hộ',N'Phạm Hải Đăng- Hai Bà Trưng- Hà Nội', 3),
('NK.00017', 'HK.00005', N'Con ruột',N'Phạm Hải Đăng- Hai Bà Trưng- Hà Nội', 4),
('NK.00018', 'HK.00006', N'Chủ hộ',N'Ngô Quốc Đạt- Hai Bà Trưng- Hà Nội', 1),
('NK.00019', 'HK.00006', N'Vợ',N'Trần Nhật Tân- Hai Bà Trưng- Hà Nội', 2),
('NK.00020', 'HK.00006', N'Con ruột',N'Trần Nhật Tân- Hai Bà Trưng- Hà Nội', 2);



INSERT INTO dbo.TamTru(ID,MaNhanKhau,NoiTamTru,TuNgay,DenNgay,LyDo)
VALUES
('TT.00001', 'NK.00001',N'Số 2 Tạ Quang Bửu- Hai Bà Trưng- Hà Nội','2022-10-8','2022-11-8', NULL),
('TT.00002', 'NK.00002',N'Số 3 Định Công- Tây Hồ- Hà Nội','2022-1-10','2023-1-10', N'Đi du học'),
('TT.00003', 'NK.00003',N'Số 4 Hàng Bạc- Hoàn Kiếm- Hà Nội','2022-5-15','2022-9-15', N'Công tác'),
('TT.00004', 'NK.00004',N'Số 5 Lê Văn Hưu- Cầu Giấy- Hà Nội','2021-3-20','2022-9-20', N'Khám chữa bệnh'),
('TT.00005', 'NK.00005',N'Số 6 Trần Đại Nghĩa- Hoàng Mai- Hà Nội','2021-7-25','2022-7-25', N'Học tập');


INSERT INTO dbo.TamVang(ID,MaNhanKhau,NoiTamTru,TuNgay,DenNgay,LyDo)
VALUES
('TV.00001', 'NK.00001',N'Kỳ Thư - Kỳ Anh - Hà Tĩnh','2021-10-8','2022-10-8', NULL),
('TV.00002', 'NK.00002',N'Nguyễn Trãi- Hai Bà Trưng- Hà Nội','2021-12-5','2022-2-8', N'Công tác'),
('TV.00003', 'NK.00002',N'Nguyễn Trãi- Hai Bà Trưng- Hà Nội','2022-5-20','2022-10-30', N'Du lịch'),
('TV.00004', 'NK.00003',N'Kỳ Thư - Kỳ Anh - Hà Tĩnh','2021-8-15','2021-10-10', N'Học tập'),
('TV.00005', 'NK.00003',N'Kỳ Thư - Kỳ Anh - Hà Tĩnh','2022-1-5','2022-3-15', N'Học tập'),
('TV.00006', 'NK.00003',N'Kỳ Thư - Kỳ Anh - Hà Tĩnh','2022-8-1','2022-9-30', N'Học tập'),
('TV.00007', 'NK.00001',N'Nguyễn Trãi- Hai Bà Trưng- Hà Nội','2022-12-1','2023-1-15', N'Du lịch'),
('TV.00008', 'NK.00002',N'Kỳ Thư - Kỳ Anh - Hà Tĩnh','2023-1-5','2023-2-5', N'Du lịch'),
('TV.00009', 'NK.00003',N'Nguyễn Trãi- Hai Bà Trưng- Hà Nội','2022-9-1','2022-10-30', N'Học tập'),
('TV.00010', 'NK.00001',N'Kỳ Thư - Kỳ Anh - Hà Tĩnh','2022-12-15','2023-1-30', N'Du lịch');

INSERT INTO dbo.PhanAnhKienNghi
(MaPA,MaNhanKhau,NoiDung,NgayPA,TrangThai, CapPhanHoi, PhanHoi,NgayPhanHoi)
VALUES
('PA.00001','NK.00001',N'Phản ánh về an toàn giao thông ở đường TQB',GETDATE(),N'Chưa phản hồi',NULL,NULL, NULL),
('PA.00002','NK.00002',N'Kiến nghị về việc xây dựng công viên',GETDATE(),N'Đã phản hồi',NULL, NULL, NULL),
('PA.00003','NK.00003',N'Phản ánh về chất lượng dịch vụ của công ty điện nước',GETDATE(),N'Chưa phản hồi',NULL,NULL, NULL),
('PA.00004','NK.00004',N'Kiến nghị về việc xây dựng sân chơi cho trẻ em',GETDATE(),N'Chưa phản hồi',NULL,NULL,NULL),
('PA.00005','NK.00005',N'Phản ánh về tình trạng bẩn chất lộ rộng',GETDATE(),N'Đã phản hồi',NULL,NULL,NULL),
('PA.00006','NK.00006',N'Kiến nghị về việc tổ chức sự kiện văn hóa',GETDATE(),N'Chưa phản hồi',NULL,NULL,NULL),
('PA.00007','NK.00007',N'Phản ánh về tình trạng môi trường',GETDATE(),N'Chưa phản hồi',NULL,NULL,NULL);


INSERT INTO dbo.TaiKhoan
(ID,TaiKhoan,MatKhau)
VALUES
( 1,'Nam','123'),
( 2,'1','1'),
( 3,'','')



