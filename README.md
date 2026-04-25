# Music App 🎵

A modern Android music fetchin application built with Android Material Design, Firebase, and Firestore.

## Features

- 🔐 **User Authentication** - Secure login with Firebase Authentication
- 🎵 **Music Streaming** - Browse and stream music content
- ☁️ **Cloud Database** - Firebase Firestore for real-time data sync
- 📱 **Material Design** - Modern UI with Material Design components
- 🔄 **Networking** - Efficient HTTP requests with Volley

## Tech Stack

- **Language**: Java 11
- **Build System**: Gradle (Kotlin DSL)
- **Min SDK**: Android 7.0 (API 24)
- **Target SDK**: Android 15 (API 36)
- **Architecture**: Android standard (Activity-based)

### Key Dependencies

- **Firebase**
  - Authentication
  - Firestore (Real-time Database)
  - BOM: 34.12.0

- **Android Libraries**
  - AndroidX AppCompat
  - Material Design
  - ConstraintLayout
  - Activity

- **Networking**
  - Volley 1.2.1

- **Testing**
  - JUnit
  - Espresso (UI Testing)

## Project Structure

```
MusicAPP/
├── app/                          # Main application module
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/            # Java source files
│   │   │   ├── res/             # Resources (layouts, strings, etc.)
│   │   │   └── AndroidManifest.xml
│   │   ├── test/                # Unit tests
│   │   └── androidTest/         # Instrumented tests
│   ├── build.gradle.kts         # App module configuration
│   └── google-services.json     # Firebase configuration
├── gradle/                       # Gradle wrapper and configs
├── settings.gradle.kts
├── build.gradle.kts             # Root build configuration
└── gradle.properties
```

## Prerequisites

- Android Studio 2023.1 or later
- JDK 11 or higher
- Android SDK 36 (API 36)
- Gradle 8.0 or later

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/Wls-barr17/music-fetch-android-app.git
cd music-fetch-android-app
```

### 2. Firebase Configuration

1. Create a Firebase project at [Firebase Console](https://console.firebase.google.com)
2. Register your Android app in Firebase project
3. Download `google-services.json`
4. Place it in the `app/` directory

### 3. Build and Run

```bash
# Build the project
./gradlew build

# Install and run on a connected device or emulator
./gradlew installDebug
```

### Windows Users
```bash
gradlew.bat build
gradlew.bat installDebug
```

## Building

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

### Run Tests
```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest
```

## Configuration Files

- **gradle.properties** - Project-wide Gradle settings
- **local.properties** - Local development settings (excluded from git)
- **app/google-services.json** - Firebase configuration (excluded from git)
- **keystore files** - For signing releases (excluded from git)



## API Integration

The app uses Volley for HTTP networking:

```java
RequestQueue queue = Volley.newRequestQueue(context);
// Add your requests to the queue
```

## Firebase Integration

### Authentication
- Firebase Authentication for user login/signup
- Secure session management

### Firestore
- Real-time database for music data
- User preferences and history storage

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support, email support@musicapp.com or open an issue in the GitHub repository.

## Authors

- **Wilson B** - Initial work

## Changelog

### Version 1.4
- Initial release
- Firebase authentication
- Music streaming functionality
- Firestore integration

