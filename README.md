# StaffManagementApp

A modern Android application for staff management, built with Jetpack Compose, Navigation, and Retrofit.

Staff Management App: Login and Staff List Implementation The People Department aims to offer employees a secure and user-friendly mobile application.

## Screenshots
<img width="400" height="640" alt="Screenshot_20250925_140947" src="https://github.com/user-attachments/assets/63b46a0a-66f8-4dad-b969-11ccb1f18b02" />
<img width="400" height="640" alt="Screenshot_20250925_140958" src="https://github.com/user-attachments/assets/d88ceb7a-8446-4f09-bf90-31d2521d57ab" />

## Getting Started

### Prerequisites
- Android Studio Hedgehog (or newer)
- JDK 8 or higher
- Internet connection (for dependency resolution and API calls)

### Setup
1. **Clone the repository:**
   ```sh
   git clone <your-repo-url>
   cd StaffManagementApp
   ```
2. **Open in Android Studio:**
   - Open the project folder in Android Studio.
3. **Sync Gradle:**
   - Let Android Studio sync and download all dependencies.
4. **Run the app:**
   - Connect an Android device or start an emulator.
   - Click the Run button or use `Shift+F10`.

## Project Structure
- `app/src/main/java/com/example/myapplication/`
  - `MainActivity.kt`: App entry point and navigation setup
  - `view/`: Contains UI screens like `LoginPage.kt` and `StaffListPage.kt`
  - `managers/`: Handles business logic (e.g., `LoginManager`, `StaffManager`)
  - `core/utils/`: Utility classes (e.g., `ValidationUtil`)
- `app/src/main/res/`: Resources (layouts, drawables, values)
- `app/build.gradle.kts`: App-level Gradle config
- `build.gradle.kts`: Project-level Gradle config

## Dependencies
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Material3](https://m3.material.io/)
- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- [Gson](https://github.com/google/gson)

See `app/build.gradle.kts` and `gradle/libs.versions.toml` for full dependency list.

## API
- The app expects a backend API for login and staff list endpoints. Update `LoginManager` and `StaffManager` as needed for your backend.


