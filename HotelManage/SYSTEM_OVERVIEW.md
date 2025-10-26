# Tổng Quan Hệ Thống Quản Lý Khách Sạn

## 🎯 Đã Hoàn Thành

### 1. Hệ Thống Phân Quyền 3 Cấp

#### **MASTER_ADMIN** (Chủ khách sạn) - Cấp 3
- Quyền cao nhất trong hệ thống
- Quản lý toàn bộ nhân viên (thêm, sửa, xóa, phân quyền)
- Xem báo cáo chấm công của tất cả nhân viên  
- Quản lý tất cả phòng, dịch vụ
- Xem tất cả thống kê và báo cáo doanh thu
- Cấu hình hệ thống

#### **ADMIN** (Nhân viên khách sạn) - Cấp 2
- Chấm công hàng ngày (check-in/check-out)
- Quản lý phòng (trạng thái, giá, thông tin)
- Quản lý khách hàng (thông tin, lịch sử)
- Xử lý đặt phòng (xác nhận, check-in, check-out)
- Xử lý đặt dịch vụ
- Quản lý thanh toán
- Xem báo cáo của mình

#### **CUSTOMER** (Khách hàng) - Cấp 1
- Đăng ký tài khoản
- Tìm kiếm và xem thông tin phòng
- Đặt phòng online
- Đặt dịch vụ khách sạn (spa, gym, nhà hàng...)
- Thanh toán online
- Xem lịch sử đặt phòng của mình
- Xem và quản lý booking của mình

### 2. Các Model Đã Tạo

#### **User** (Người dùng)
```java
- id, username, password, fullName, email, phone
- role: MASTER_ADMIN / ADMIN / CUSTOMER
- position: Chức vụ (cho nhân viên)
- department: Phòng ban (cho nhân viên)
- active: Trạng thái hoạt động
```

#### **Room** (Phòng)
```java
- roomNumber, roomType, pricePerNight
- status: AVAILABLE / OCCUPIED / MAINTENANCE / CLEANING / RESERVED
- maxOccupancy, floor, description, amenities
```

#### **Guest** (Khách hàng - thông tin chi tiết)
```java
- fullName, email, phone, identityCard
- dateOfBirth, gender, address, nationality
- notes
```

#### **Booking** (Đặt phòng)
```java
- bookingCode, guest, room
- checkInDate, checkOutDate, numberOfGuests
- status: PENDING / CONFIRMED / CHECKED_IN / CHECKED_OUT / CANCELLED
- totalAmount, paidAmount
```

#### **Payment** (Thanh toán)
```java
- paymentCode, booking, amount
- paymentMethod: CASH / CREDIT_CARD / BANK_TRANSFER / MOMO / VNPAY
- paymentDate, status
```

#### **HotelService** (Dịch vụ khách sạn) ✨ MỚI
```java
- serviceName, serviceType, description
- price, unit: PER_USE / PER_HOUR / PER_DAY / PER_PERSON
- available
Loại dịch vụ: SPA, RESTAURANT, LAUNDRY, GYM, POOL, TRANSPORT, TOUR, MEETING_ROOM
```

#### **ServiceBooking** (Đặt dịch vụ) ✨ MỚI
```java
- booking, service, quantity
- price, totalAmount, serviceTime
- status: PENDING / CONFIRMED / IN_PROGRESS / COMPLETED / CANCELLED
```

#### **Attendance** (Chấm công) ✨ MỚI
```java
- user, date, checkInTime, checkOutTime
- workingMinutes, workingHours
- status: PRESENT / LATE / ABSENT / LEAVE / SICK_LEAVE / BUSINESS_TRIP
- approvedBy
```

### 3. Repository & Service Layer
Đã tạo đầy đủ Repository và Service cho tất cả các Model.

### 4. Thiết Kế Giao Diện
- Tông màu: Trắng, Xám, Đen (sang trọng, tối giản)
- Typography rõ ràng, professional
- Responsive, tương thích mọi thiết bị
- Hover effects mượt mà

## 🔄 Cần Hoàn Thành Tiếp

### 1. Security Config (ĐANG LÀM)
Cần cập nhật file `SecurityConfig.java` với:
- Role-based access control
- URL permissions theo role
- Custom login page
- Remember me
- Session management

### 2. Controllers Mới
Cần tạo:
- `HotelServiceController` (web & API)
- `ServiceBookingController` (web & API)
- `AttendanceController` (web & API)  
- `StaffController` (quản lý nhân viên)
- `CustomerPortalController` (giao diện khách hàng)

### 3. Templates Mới

#### Cho Master Admin:
- `staff/list.html` - Danh sách nhân viên
- `staff/form.html` - Thêm/sửa nhân viên
- `attendance/list.html` - Bảng chấm công
- `attendance/report.html` - Báo cáo chấm công
- `dashboard/master.html` - Dashboard tổng quan cho chủ

#### Cho Admin (Nhân viên):
- `attendance/checkin.html` - Chấm công
- `dashboard/staff.html` - Dashboard cho nhân viên

#### Cho Customer:
- `customer/home.html` - Trang chủ khách hàng
- `customer/rooms.html` - Danh sách phòng (tìm kiếm, lọc)
- `customer/services.html` - Danh sách dịch vụ
- `customer/booking-form.html` - Form đặt phòng
- `customer/my-bookings.html` - Booking của tôi
- `customer/profile.html` - Thông tin cá nhân

### 4. Cập Nhật DataInitializer
Thêm dữ liệu mẫu cho:
- Master Admin, Admin, Customer users
- Hotel Services
- Service Bookings
- Attendance records

## 📊 Các Tính Năng Mới

### Chấm Công (Attendance)
```
- Check-in: Tự động ghi nhận giờ vào
- Check-out: Tự động tính số giờ làm việc
- Late detection: Đánh dấu đi muộn nếu sau 8:15
- Báo cáo: Tổng giờ làm việc theo tháng
- Phê duyệt: Master Admin duyệt chấm công
```

### Dịch Vụ Khách Sạn
```
- Spa & Massage
- Nhà hàng (đặt bàn, order)
- Giặt là
- Phòng gym
- Hồ bơi
- Vận chuyển (xe đưa đón)
- Tour du lịch
- Phòng họp
```

### Đặt Dịch Vụ
```
- Customer có thể đặt dịch vụ khi có booking
- Chọn thời gian sử dụng
- Thanh toán cùng với phòng hoặc riêng
- Theo dõi trạng thái dịch vụ
```

## 🎨 Cấu Trúc Menu

### Master Admin Menu
```
📊 Dashboard
👥 Quản lý nhân viên
⏰ Chấm công
🏨 Quản lý phòng
👤 Quản lý khách hàng
📅 Đặt phòng
🛎️ Dịch vụ
💳 Thanh toán
📈 Báo cáo & Thống kê
⚙️ Cài đặt
```

### Admin Menu
```
📊 Dashboard
⏰ Chấm công của tôi
🏨 Quản lý phòng
👤 Quản lý khách hàng
📅 Đặt phòng
🛎️ Đặt dịch vụ
💳 Thanh toán
```

### Customer Menu
```
🏠 Trang chủ
🔍 Tìm phòng
🛎️ Dịch vụ
📅 Booking của tôi
👤 Thông tin cá nhân
💳 Lịch sử thanh toán
```

## 🔐 Endpoint API

### Hotel Services
```
GET    /api/services          - Danh sách dịch vụ
GET    /api/services/{id}     - Chi tiết dịch vụ
POST   /api/services          - Tạo dịch vụ (MASTER_ADMIN)
PUT    /api/services/{id}     - Cập nhật (MASTER_ADMIN/ADMIN)
DELETE /api/services/{id}     - Xóa (MASTER_ADMIN)
```

### Service Bookings
```
GET    /api/service-bookings            - Danh sách
POST   /api/service-bookings            - Đặt dịch vụ
PUT    /api/service-bookings/{id}       - Cập nhật
POST   /api/service-bookings/{id}/confirm - Xác nhận
POST   /api/service-bookings/{id}/complete - Hoàn thành
DELETE /api/service-bookings/{id}       - Hủy
```

### Attendance
```
GET    /api/attendance              - Danh sách chấm công
POST   /api/attendance/checkin      - Check-in
POST   /api/attendance/checkout     - Check-out
GET    /api/attendance/my           - Chấm công của tôi
GET    /api/attendance/report       - Báo cáo (MASTER_ADMIN)
```

## 🚀 Hướng Dẫn Chạy

### 1. Cài đặt
```bash
mvn clean install
```

### 2. Chạy
```bash
mvn spring-boot:run
```

### 3. Truy cập
- Web: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console

### 4. Tài khoản mặc định
```
Master Admin:
  username: master
  password: master123

Admin (Nhân viên):
  username: admin  
  password: admin123

Customer:
  username: customer
  password: customer123
```

## 📝 Lưu Ý Kỹ Thuật

1. **Security**: Chưa implement authentication đầy đủ, cần thêm:
   - Login page
   - JWT token cho API
   - Password encoding đã có
   - Role-based authorization

2. **Database**: 
   - Hiện dùng H2 in-memory
   - Có thể chuyển sang MySQL/PostgreSQL
   - Data khởi tạo tự động

3. **Frontend**:
   - Thymeleaf + Bootstrap 5
   - Có thể tách ra React/Vue nếu cần
   - Mobile responsive

4. **Performance**:
   - Lazy loading cho quan hệ
   - Pagination cần implement cho list lớn
   - Caching có thể thêm

## 🎯 Roadmap Tiếp Theo

### Phase 1 (Hiện tại)
- ✅ Core Models
- ✅ Repository & Service
- ⏳ Security & Authorization
- ⏳ Basic Controllers
- ⏳ Admin Templates

### Phase 2 (Tiếp theo)
- ⏳ Customer Portal
- ⏳ Service Booking
- ⏳ Attendance System
- ⏳ Reports & Analytics

### Phase 3 (Tương lai)
- 📱 Mobile App
- 🔔 Notifications
- 📧 Email Service
- 💰 Payment Gateway Integration
- 📊 Advanced Analytics
- 🌍 Multi-language

## 💡 Best Practices

1. **Code Organization**
   - Phân chia rõ ràng: model, repository, service, controller
   - Sử dụng DTO cho API nếu cần
   - Exception handling tập trung

2. **Security**
   - Luôn validate input
   - Encode password
   - Check authorization ở mọi endpoint

3. **Database**
   - Index các trường tìm kiếm thường xuyên
   - Sử dụng transaction đúng cách
   - Backup định kỳ

4. **UI/UX**
   - Consistent design
   - Clear error messages
   - Loading states
   - Confirmation dialogs

---

**Lưu ý**: Đây là hệ thống đang trong quá trình phát triển. Một số tính năng còn cần hoàn thiện. Vui lòng tham khảo code và docs để biết chi tiết.

