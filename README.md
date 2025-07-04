
# ProTrack - Frontend

## **Hướng Dẫn Cài Đặt Dự Án FE ProTrack**

### **Yêu Cầu Hệ Thống**
- **Android Studio**: Tải và cài đặt [Android Studio](https://developer.android.com/studio).
- **JDK**: Cài đặt Java Development Kit (JDK) phiên bản 8 hoặc cao hơn.
- **Gradle**: Cài đặt Gradle (Android Studio đã bao gồm sẵn Gradle).
- **Repo BE**: Cài đặt tại Repo Github [protrack-be](https://github.com/SE114-ProTrack/protrack-be)

---

### **Cài Đặt Dự Án**
1. **Clone repository** từ GitHub hoặc tải project về máy của bạn:
```
   git clone https://github.com/username/protrack-fe.git
````
Hoặc tải trực tiếp **zip** từ GitHub.

2. **Giải nén và mở project** trong Android Studio.

---

### **Cài Đặt SDK Android**

1. **Mở Android Studio**.

2. Trong **File > Settings > Appearance & Behavior > System Settings > Android SDK**, chọn phiên bản SDK bạn muốn sử dụng (tốt nhất là phiên bản mới nhất của Android).

3. **SDK Đường Dẫn**: Đảm bảo bạn đã cấu hình đường dẫn SDK Android đúng. Trong trường hợp này, đường dẫn SDK có thể nằm trong file `local.properties` (tự động tạo khi bạn mở dự án lần đầu trong Android Studio):

   ```properties
   sdk.dir=C\:\\Users\\Admin\\AppData\\Local\\Android\\Sdk
   ```

---

### **Cấu Hình Gradle**

Dự án sử dụng **Gradle** để quản lý các phụ thuộc (dependencies) và build cấu hình. Bạn có thể **tùy chỉnh các thông số trong `gradle.properties` hoặc `build.gradle`**.

* **Thông số `gradle.properties`:**

  ```properties
  org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
  android.useAndroidX=true
  ```

* **Thông số SDK trong `local.properties`**: Cấu hình đúng đường dẫn SDK Android để Android Studio nhận diện.

  ```properties
  sdk.dir=C\:\\Users\\Admin\\AppData\\Local\\Android\\Sdk
  ```

---

### **Cài Đặt Dependencies**

1. Mở **Android Studio**, vào **File > Sync Project with Gradle Files** để tải về tất cả các phụ thuộc cần thiết.
2. Nếu có thông báo lỗi về các dependencies chưa được tải, hãy chắc chắn rằng **mạng ổn định** hoặc thử lại việc **sync Gradle**.

---

### **Thiết Lập Biến Môi Trường**

Trong **gradle.properties**, đảm bảo rằng các tham số như đường dẫn SDK và các cài đặt khác được thiết lập đúng. Ví dụ:

```properties
sdk.dir=C\:\\Users\\Admin\\AppData\\Local\\Android\\Sdk
android.useAndroidX=true
```
---

### **Cấu Hình Local BE trong FE**

Mở ApiClient.java trong dự án FE ProTrack.

Thay đổi BASE_URL thành địa chỉ của BE đang chạy trên máy local:
```
private static final String BASE_URL = "http://10.0.2.2:8080/api/";
```
10.0.2.2 là IP đặc biệt để kết nối từ Android Emulator tới localhost của máy tính.

---

### **Chạy Ứng Dụng**

1. **Chạy trên thiết bị giả lập**:

   * Tạo **Android Virtual Device (AVD)** trong Android Studio (Tools > AVD Manager).
   * Chạy ứng dụng bằng nút **Run** trong Android Studio.

2. **Chạy trên thiết bị thật**:

   * Kết nối thiết bị Android qua USB và bật chế độ **Developer Mode** và **USB Debugging**.
   * Chạy ứng dụng trực tiếp trên thiết bị thật từ Android Studio.

---

### **Kiểm Tra**

Sau khi ứng dụng chạy thành công:

* Đảm bảo tất cả các tính năng **đăng ký, đăng nhập, xem task, tạo project** hoạt động bình thường.
* Kiểm tra các API backend đã được gọi đúng và nhận dữ liệu chính xác.

---

### **Tóm Tắt**

* Clone dự án, cài đặt SDK Android, cài đặt BE.
* Sync Gradle để tải về các dependencies.
* Kiểm tra cấu hình SDK trong `local.properties`.
* Cài đặt và chạy trên thiết bị thật hoặc giả lập.
