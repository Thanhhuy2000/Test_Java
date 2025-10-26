# Hệ thống Quản lý Cuộc thi Nhảy (Dance Competition Management System)

## Mô tả
Hệ thống quản lý cuộc thi nhảy được xây dựng theo mô hình OOP, cho phép quản lý nhiều đội thi đấu với các vũ công có phong cách khác nhau (HipHop, Ballet, Contemporary).

## Cấu trúc thư mục
```
Dance/
├── src/
│   ├── model/
│   │   ├── Dancer.java (abstract)
│   │   ├── HipHopDancer.java
│   │   ├── BalletDancer.java
│   │   ├── ContemporaryDancer.java
│   │   ├── Performance.java
│   │   ├── Team.java
│   │   └── CompetitionState.java
│   ├── controller/
│   │   └── CompetitionController.java
│   ├── exception/
│   │   └── InvalidDancerException.java
│   └── Main.java
├── ClassDiagram.md
└── README.md
```

## Tính năng chính

### 1. Quản lý đội và vũ công
- Nhập số lượng đội tùy ý
- Mỗi đội có 3 vũ công với 4 chỉ số: Power, Grace, Emotion, Energy
- 3 phong cách nhảy: HipHop, Ballet, Contemporary

### 2. Hệ thống tính điểm
- **HipHop**: Power ảnh hưởng mạnh nhất
- **Ballet**: Grace ảnh hưởng mạnh nhất  
- **Contemporary**: Emotion ảnh hưởng mạnh nhất + yếu tố ngẫu nhiên
- **Combo**: Nếu 3 vũ công cùng biểu diễn, điểm +10%

### 3. Thi đấu và kết quả
- 8 lượt thi đấu
- Chọn vũ công biểu diễn từng lượt
- Bảng xếp hạng cuối cùng
- Thống kê vũ công điểm cao nhất

## Hướng dẫn sử dụng

### 1. Biên dịch
```bash
javac -d out src/model/*.java src/controller/*.java src/exception/*.java src/Main.java
```

### 2. Chạy chương trình
```bash
java -cp out Main
```

### 3. Quy trình sử dụng
1. **Nhập số lượng đội**
2. **Với mỗi đội:**
   - Nhập tên đội
   - Nhập thông tin 3 vũ công (tên, phong cách, 4 chỉ số)
3. **Trong mỗi lượt:**
   - Chọn vũ công biểu diễn cho từng đội
   - Xem điểm lượt và combo
4. **Sau mỗi lượt:**
   - Menu xem danh sách đội
   - Menu xem điểm từng đội
5. **Kết thúc:**
   - Bảng xếp hạng cuối cùng
   - Vũ công điểm cao nhất

## Công thức tính điểm

### HipHop Dancer
- Technical: `power×8 + grace×20 + emotion×2`
- Artistic: `grace×30 + power×3 + emotion×3`
- Emotional: `emotion×5 + power×2 + grace×10`

### Ballet Dancer
- Technical: `grace×40 + power×4 + emotion×2`
- Artistic: `grace×50 + power×2 + emotion×4`
- Emotional: `emotion×6 + grace×20 + power×2`

### Contemporary Dancer
- Technical: `power×5 + grace×25 + emotion×4 + random(10)`
- Artistic: `grace×35 + power×3 + emotion×5 + random(10)`
- Emotional: `emotion×8 + power×2 + grace×15 + random(10)`

## Mô hình OOP

### Kế thừa
- `Dancer` (abstract) ← `HipHopDancer`, `BalletDancer`, `ContemporaryDancer`

### Đóng gói
- Tất cả thuộc tính private/protected
- Getter/setter cho truy cập dữ liệu

### Đa hình
- `performDance()` được override ở mỗi lớp con
- Công thức tính điểm khác nhau cho từng phong cách

### Trừu tượng
- Lớp `Dancer` abstract với phương thức `performDance()` trừu tượng

## Các lớp chính

### Model
- **Dancer**: Lớp trừu tượng cho vũ công
- **Team**: Quản lý đội và điểm số
- **Performance**: Biểu diễn và tính điểm
- **CompetitionState**: Trạng thái cuộc thi

### Controller
- **CompetitionController**: Điều khiển luồng chính

### Exception
- **InvalidDancerException**: Xử lý ngoại lệ

## Mở rộng có thể
- Thêm phong cách nhảy mới
- Thay đổi công thức tính điểm
- Thêm hiệu ứng đặc biệt
- Lưu/tải dữ liệu từ file
- Giao diện đồ họa
- Kết nối cơ sở dữ liệu

## Tác giả
Hệ thống được phát triển theo yêu cầu bài tập OOP. 