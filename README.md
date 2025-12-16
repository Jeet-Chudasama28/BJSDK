# 🎬 BJSDK – Hollywood Movies SDK for Android

BJSDK is an Android SDK library that allows you to easily display **Hollywood movies** inside your application. The SDK is built using **Kotlin** and **XML**, uses **Retrofit** for networking, and follows an **API-driven forward & backward pagination** approach to reduce memory consumption. Dependency Injection is handled using **Koin**.

---

## ✨ Features

* 📽️ Display Hollywood movies with ready-to-use UI
* ⚡ Built with Kotlin & XML
* 🌐 Network calls using Retrofit
* 🔄 Forward & backward pagination (API-based, memory efficient)
* 🧩 Dependency Injection using Koin
* 📦 Easy integration via JitPack

---

## 📦 Installation

### Step 1: Add JitPack Repository

Add the JitPack repository in your **settings.gradle.kts** file:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

---

### Step 2: Add SDK Dependency

Add the following dependency in your **app-level build.gradle.kts**:

```kotlin
implementation("com.github.Jeet-Chudasama28:BJSDK:1.0.0")
```

Sync your project after adding the dependency.

---

## 🚀 Usage

### 1️⃣ Initialize the SDK

Initialize the SDK once, preferably in your `Activity` or `Application` class:

```kotlin
MySdk.init(application)
```

---

### 2️⃣ Open the Movies Screen

Call the SDK screen wherever you want to display the Hollywood movies:

```kotlin
MySdk.openMainScreen(this)
```

---

## 📌 Complete Example

```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initialize SDK
        MySdk.init(application)

        // Open Movies Screen
        MySdk.openMainScreen(this)
    }
}
```

---

## 🧠 Architecture & Design

* **Architecture:** MVVM
* **Language:** Kotlin
* **UI:** XML
* **Networking:** Retrofit
* **Pagination:** Forward & Backward pagination (API-driven)
* **Dependency Injection:** Koin

This approach ensures:

* Reduced memory consumption
* Smooth scrolling experience
* Clean and modular SDK design

---

⭐ If you like this SDK, don’t forget to give it a star on GitHub!
