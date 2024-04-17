-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th4 17, 2024 lúc 08:58 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `quanlycongviec`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `congviec`
--

CREATE TABLE `congviec` (
  `id` int(11) NOT NULL,
  `tenCongViec` varchar(255) NOT NULL,
  `nguoiThucHien` varchar(100) DEFAULT NULL,
  `trangThai` enum('COMPLETED','REJECT','PENDING','PROCESSING') DEFAULT 'PENDING',
  `version` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `congviec`
--

INSERT INTO `congviec` (`id`, `tenCongViec`, `nguoiThucHien`, `trangThai`, `version`) VALUES
(8, 'Cong viec 8', 'user2', 'PROCESSING', 9),
(9, 'Cong viec 9', 'user2', 'REJECT', 6),
(10, 'Cong viec 10', 'admin', 'REJECT', 4),
(11, 'Cong viec 11', 'admin', 'PROCESSING', 2),
(12, 'Cong viec 12', 'admin', 'COMPLETED', 3),
(15, 'Cong viec 15', '', 'PENDING', 1),
(16, 'Cong viec 16', '', 'PENDING', 1),
(17, 'Cong viec 17', '', 'PENDING', 1),
(18, 'Cong viec 18', '', 'PENDING', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `taikhoan`
--

CREATE TABLE `taikhoan` (
  `tenDangNhap` varchar(100) NOT NULL,
  `matKhau` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `taikhoan`
--

INSERT INTO `taikhoan` (`tenDangNhap`, `matKhau`) VALUES
('admin', 'admin'),
('user2', 'user2'),
('user3', 'user3'),
('user4', 'user4'),
('user5', 'user5');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `congviec`
--
ALTER TABLE `congviec`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`tenDangNhap`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `congviec`
--
ALTER TABLE `congviec`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
