<p align="center">
  <img src="https://github.com/user-attachments/assets/37f18fd6-ae9c-4588-b39c-ba33285f6357" alt="Win My Argument Logo">
</p>

# 📌 Win My Argument - AI-Powered Debate Assistant

## 📝 Overview
**Win My Argument** is an AI-powered debate assistant that helps users find **structured, research-backed arguments** in real time. Whether you're debating with friends or preparing for a discussion, this app provides **fact-based, winnable responses** instantly.

---

## 🚀 Features
✅ **AI-Powered Argument Finder** – Get science-backed debate responses instantly.  
✅ **Saved Arguments (History)** – Store and revisit past debates using **Room DAO**.  
✅ **Firebase Authentication** – Secure **user login and profile management**.  
✅ **Friend Debate Mode** – Chat & challenge **friends** to see who wins the argument.  
✅ **Modern UI/UX** – Smooth and **interactive user interface**.  

---

## 🛠️ Tech Stack
- **Frontend:** Jetpack Compose (Kotlin)  
- **Backend:** Firebase (Authentication & Firestore Database)  
- **Local Storage:** Room DAO (For argument history)  
- **Machine Learning:** Large Language Model (LLM) for argument generation  

---

## 💂️ Project Structure
```
📦 WinMyArgument  
 ├ 📂 app/src/main  
 ┃ ├ 📂 java/com/yourpackage/winmyargument  
 ┃ ┃ ├ 📂 ui       # Jetpack Compose UI components  
 ┃ ┃ ├ 📂 auth     # Firebase authentication logic  
 ┃ ┃ ├ 📂 db       # Room DAO database setup  
 ┃ ┃ ├ 📂 chat     # Debate chat feature with Firebase  
 ┃ ┃ └ 📋 MainActivity.kt  
 ┃ └ 📂 res        # UI resources (drawables, layouts, etc.)  
 ├ 📃 README.md    # This file  
 └ 📃 build.gradle
```

---

## 🛠️ Setup & Installation

### 🔹 **Prerequisites**
Before you begin, ensure you have the following:
- ✅ **Android Studio (Latest Version)**
- ✅ **Firebase Project Setup** ([Guide](https://firebase.google.com/docs/android/setup))
- ✅ **Google Services JSON** (Place in `app/` directory)

### 🔹 **Steps to Run the Project**
#### 1️⃣ **Clone the Repository**
```
git clone https://github.com/your-username/WinMyArgument.git
cd WinMyArgument
```

#### 2️⃣ **Open in Android Studio**
- Open the project in **Android Studio**
- Sync Gradle & install dependencies

#### 3️⃣ **Setup Firebase**
- Register the app in **Firebase Console**
- Add `google-services.json` inside `app/` folder
- Enable **Authentication** & **Firestore Database**  

#### 4️⃣ **Run the App! 🚀**
- Click **Run** ▶️ in Android Studio
- Enjoy debating with **AI-powered arguments!** 🎧  

---

## 🛠️ Setup & Initialization

### **1️⃣ Configure Firebase**
1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click **"Add Project"** and create a new project
3. Register your Android App with your package name
4. Download the **`google-services.json`** file and place it in the `app/` directory
5. Enable **Authentication & Firestore Database** in Firebase settings

### **2️⃣ Add Firebase SDK**
Update your **build.gradle (Project level)**:
```gradle
classpath 'com.google.gms:google-services:4.3.10'
```
In **build.gradle (App level)**:
```gradle
apply plugin: 'com.google.gms.google-services'
dependencies {
    implementation 'com.google.firebase:firebase-auth:21.0.1'
    implementation 'com.google.firebase:firebase-firestore:23.0.3'
}
```

### **3️⃣ Setup Room Database**
Add dependencies in **build.gradle**:
```gradle
dependencies {
    implementation "androidx.room:room-runtime:2.4.0"
    kapt "androidx.room:room-compiler:2.4.0"
}
```
Initialize Room in your **Database Class**:
```kotlin
@Database(entities = [Argument::class], version = 1)
abstract class ArgumentDatabase : RoomDatabase() {
    abstract fun argumentDao(): ArgumentDao
}
```

## 📌 Future Improvements  
🔹 **More LLM Models** for better argument generation  
🔹 **Voice-to-text support** for spoken debates  
🔹 **Custom argument filters** (fact-based, opinion-based, or mixed)  
🔹 **Leaderboard system** for competitive debating  

---

## 💡 Contributing  
We welcome contributions! Follow these steps:  
1️⃣ **Fork** the repository  
2️⃣ **Create a new branch** (`feature-branch`)  
3️⃣ **Commit your changes** (`git commit -m "Added new feature"`)  
4️⃣ **Push and create a Pull Request**  

---

## 📧 Contact & Feedback  
📩 **Email:** saswat6437nms@gmail.com  
---

🚀 **Happy Debating!** 🏆
