# MangaReader

A simple and intuitive Manga Reader Android application built with Kotlin. The application consumes the public MangaDex API to browse, search, and read manga, while leveraging Firebase Authentication for user accounts.

## Features

*   **User Authentication**: Register, Login, and Logout functionality using Firebase.
*   **Manga Dashboard**: View a grid list of available manga.
*   **Search Manga**: Search for specific manga titles.
*   **Manga Details**: View manga information and the list of available chapters.
*   **Manga Reader**: Read pages of selected manga chapters directly within the app.
*   **User Profile**: View current logged-in user information.

## Tech Stack

*   **Frontend**: Android (Kotlin, XML Layouts)
*   **Backend**: MangaDex API (External API)
*   **Database**: None (Local caching handled by Glide, no Room DB implemented despite dependencies)
*   **Authentication**: Firebase Authentication
*   **API**: RESTful API via Retrofit
*   **State Management**: Android Fragment Lifecycle & Kotlin Coroutines
*   **Styling**: Material Components & Custom XML Styling
*   **Testing**: JUnit, Espresso
*   **Deployment**: Android APK/AAB build system
*   **Other Tools**: Glide (Image Loading), OkHttp (Network Interceptor), Jetpack Navigation (Routing)

## Project Structure

*   `app/src/main/java/.../data/api/`: Contains Retrofit interface definitions and data models for the MangaDex API.
*   `app/src/main/java/.../data/auth/`: Contains the Firebase Authentication repository logic.
*   `app/src/main/java/.../ui/`: Contains the UI presentation layer, organized by feature modules:
    *   `auth/`: Login and Registration fragments.
    *   `dashboard/`: Home, Profile, About, and Search fragments.
    *   `detail/`: Manga Detail and Chapter List fragments.
    *   `reader/`: Manga Reader and Page viewing fragments.
    *   `splash/`: Splash Screen fragment.
*   `app/src/main/res/`: Contains Android resources including layout XMLs, navigation graphs, drawables, values, and themes.

## Installation

1.  **Clone repository**
    ```bash
    git clone https://github.com/rzhfXV/mangareader
    cd MangaReader
    ```
2.  **Open Project**
    Open the project using Android Studio.
3.  **Setup Firebase Environment**
    *   Create a project on the [Firebase Console](https://console.firebase.google.com/).
    *   Add an Android app to the Firebase project with the package name `com.rzhf.mangareader`.
    *   Enable **Email/Password** sign-in method in Firebase Authentication.
    *   Download the `google-services.json` file.
    *   Place the `google-services.json` file inside the `app/` directory.
4.  **Sync Dependencies**
    Click "Sync Project with Gradle Files" in Android Studio to download all required libraries.
5.  **Run Development Server**
    Run the app on an Android Emulator or physical device using Android Studio (`Shift + F10`).

## Environment Variables

| Variable / File | Description | Required |
| --- | --- | --- |
| `google-services.json` | Firebase configuration file containing API keys and project settings. Must be placed in the `app/` directory. | Yes |

## Running the Project

You can run the following commands via the Android Studio terminal (`./gradlew` on Linux/Mac, `gradlew.bat` on Windows):

*   **Development (Install on Device)**: `./gradlew installDebug`
*   **Build APK (Debug)**: `./gradlew assembleDebug`
*   **Build APK (Release)**: `./gradlew assembleRelease`
*   **Test**: `./gradlew test`
*   **Lint**: `./gradlew lint`

## API Documentation

The application relies on the [MangaDex API](https://api.mangadex.org/docs/). Here is a summary of the main endpoints utilized:

*   `GET /manga`: Fetches a paginated list of manga (includes cover art).
*   `GET /manga/{id}`: Retrieves detailed information about a specific manga.
*   `GET /manga/{id}/feed`: Fetches the chapter list for a manga, ordered ascending and filtered for English language.
*   `GET /at-home/server/{chapterId}`: Retrieves the base URL and page hashes to construct the image URLs for reading a chapter.

## Database

The project does not use a local SQLite database or Room entities for persistent data storage. Data management relies primarily on network responses from the MangaDex API and image caching handled internally by Glide. User session and authentication states are managed directly via **Firebase Authentication**.

## Architecture

The project follows a **Feature-based / MVC** architecture:
*   **Feature Modules**: The UI packages are grouped by functional features (e.g., `auth`, `dashboard`, `reader`).
*   **MVC Pattern**: Fragments act as Controllers that handle UI events, manage the View (XML Layouts), and directly invoke data operations (e.g., calling the API using Coroutines or communicating with the Auth Repository) without intermediate ViewModels.

## Dependencies

*   **Kotlin Coroutines (`kotlinx-coroutines`)**: For asynchronous programming and handling background network requests.
*   **Retrofit 2**: For defining and consuming the REST API.
*   **OkHttp 3 Logging Interceptor**: For logging HTTP requests and responses during development.
*   **Glide**: For asynchronous image loading, displaying cover arts, and manga pages.
*   **Jetpack Navigation Component**: For handling in-app navigation and fragment transactions.
*   **Firebase Authentication (`firebase-auth`)**: For managing user sign-up, sign-in, and sessions.

## Deployment

To deploy the application to the Google Play Store:
1.  Open Android Studio.
2.  Navigate to **Build > Generate Signed Bundle / APK...**.
3.  Choose **Android App Bundle** (recommended) or **APK**.
4.  Provide your Keystore file, alias, and passwords.
5.  Select the **release** build variant and finish.
6.  Upload the generated `.aab` or `.apk` to the Google Play Console.

## Contributing

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE.md) file for details.

This project uses publicly available third-party APIs. All trademarks, data, and content provided by those APIs remain the property of their respective owners. Users of this project are responsible for complying with the terms of service of any third-party APIs they use.
