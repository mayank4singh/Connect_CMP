# 📱 College Management System – Android Application

A role-based Android application designed to digitalize college operations such as student management, faculty management, notices, and attendance tracking. This app was developed as my **BCA Final Year Project (2024)** using **Java, XML, and Firebase**.

---

## 🚀 Features

### 🔐 Authentication & Roles
- Secure Firebase Authentication
- Role-based access for:
  - **Admin**
  - **Teacher**
  - **Student**

### 👨‍💼 Admin Module
- Add / update / remove students and teachers
- Create and publish notices
- Manage subjects and classes
- Monitor overall attendance analytics

### 👨‍🏫 Teacher Module
- View assigned students
- Mark & update attendance
- View student progress
- Access notices

### 👨‍🎓 Student Module
- View personal attendance
- Get important notices & announcements
- Track academic progress
- Update profile

### 📊 Attendance System
- Subject-wise attendance
- Automated attendance percentage calculation
- Student performance visualization

### 🤖 AI-Enhanced (Planned)
- Face recognition-based attendance (Firebase ML Kit)
- Predictive performance analysis (AI models)
- Dropout-risk or low-attendance alerts

---

## 🛠 Tech Stack

- **Programming Language:** Java
- **UI Design:** XML, Material Design
- **Backend:** Firebase
- **Database:** Firebase Realtime Database / Firestore
- **Authentication:** Firebase Auth
- **Storage:** Firebase Storage
- **AI / ML:** Firebase ML Kit (Face Detection – Planned)
- **IDE:** Android Studio
- **Version Control:** Git & GitHub

---

## 🧩 System Architecture
1. Presentation Layer
   - Activities / Fragments
   - XML Layouts
   - User Dashboards (Admin, Teacher, Student)

2. Business Logic Layer
   - Role-based access control
   - Attendance calculation
   - Validation & data handling

3. Data Layer (Cloud)
   - Firebase Authentication
   - Firebase Realtime Database
   - Firebase Storage
---

## 📷 Screens 
 - ### Admin
Login|Admin Dashboard|Faculty View| Workflow
:-----:|:-------------------------------:|:------:|:------:
![Login](https://github.com/user-attachments/assets/ea917c45-9483-4a9c-bec3-efba63988c3f)|![Admin Dash](https://github.com/user-attachments/assets/c0e97ef6-34fe-4c87-a8c6-26a6620ee798)|![Faculty view](https://github.com/user-attachments/assets/584c030d-8917-4390-9ea8-73c4264fac10) |![Admin Slide](https://github.com/user-attachments/assets/eb13263f-eae3-4128-861f-3c35b6b63f56)
- ### Faculty
|Faculty Dashboard|Attendance|Workflow
:-----:|:-------------------------------:|:------:
![Teacher Dash](https://github.com/user-attachments/assets/a8753617-1751-4103-94d7-658404811211)|![Attendance](https://github.com/user-attachments/assets/c2149de6-60d8-4ef8-922a-fb55a1ec8d83)|![Slide Screen](https://github.com/user-attachments/assets/9a0e663b-4ee7-49a6-8fd5-c9fed796df9a)
- ### Student
|Student Dashboard|Attendance|Workflow
:-----:|:-------------------------------:|:------: 
![Student Dash](https://github.com/user-attachments/assets/fefffc9e-22c3-43d7-9acc-3ecd5ac12c69)|![Attendance](https://github.com/user-attachments/assets/fb27a6db-f77c-44ee-81c2-ae58def76eb9)|![Student Slide](https://github.com/user-attachments/assets/3e40140b-f806-467e-bf3e-22ae1b6a90cd)
---

## 🔮 Future Improvements

- Parent module for monitoring students
- Chat system between teacher and student
- Push notifications for attendance & notices
- AI-based recommendations for academic improvement
- Export reports in PDF/Excel

---

## 👨‍💻 Developed By

**Mayank Singh**  
Android Developer | Firebase & Java  


---

## ⚠️ Note

`google-services.json` is excluded from this repository for security reasons.  
If you want to run the project:

1. Clone the repository
2. Create a Firebase project
3. Add your own `google-services.json` file
4. Enable Firebase Authentication & Database
5. Run the app in Android Studio

---

⭐ *If you like this project, don't forget to star and fork the repo
