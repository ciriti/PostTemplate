# PostTemplate App

## Introduction

PostTemplate is an Android application designed to manage and navigate through various screens, leveraging modern architecture principles. The app includes authentication, home screens, and seamless navigation across multiple components.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [App Flow](#app-flow)
- [Architecture](#architecture)
- [Setup and Installation](#setup-and-installation)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [Dependencies](#dependencies)
- [Error Handling](#error-handling)

## Features

- **Authentication**: User login and authentication flow.
- **Navigation**: Navigate across screens with a structured NavGraph.
- **Home Screen**: Display data with reactive UI and view models.
- **State Management**: Handle state, intents, and effects for a clean UI logic.

## App Flow

- The app starts with the **Authentication Screen**, allowing users to log in.
- Upon successful login, users are directed to the **Home Screen**, which displays relevant data.
- Navigation is managed by a central `SetupNavGraph.kt` file, ensuring a smooth transition between different routes.

## Architecture

The application follows a clean architecture model to maintain scalability and testability.

### Layers

1. **Presentation Layer**: Contains UI and ViewModel logic.
2. **Domain Layer**: Contains state and intent models.
3. **Navigation Layer**: Manages navigation using `NavGraph`.
4. **Data Layer**: Handles external data sources.

### Directory Structure

```
.
├── App.kt
├── MainActivity.kt
├── data
│   ├── local
│   │   ├── Database.kt
│   │   ├── PostDao.kt
│   │   ├── PostEntity.kt
│   │   ├── UserDao.kt
│   │   └── UserEntity.kt
│   ├── models
│   │   ├── PostDto.kt
│   │   └── UserDto.kt
│   ├── remote
│   │   ├── ApiService.kt
│   │   └── NetworkClient.kt
│   └── repository
│       ├── AuthRepository.kt
│       ├── PostRepository.kt
│       └── UserRepository.kt
├── di
│   └── AppModule.kt
├── domain
│   ├── extensions
│   │   ├── PostExtensions.kt
│   │   └── UserExtensions.kt
│   ├── models
│   │   ├── Post.kt
│   │   └── User.kt
│   └── services
│       ├── PostService.kt
│       └── UserService.kt
├── ui
│   ├── components
│   │   ├── AdaptiveNavigationDrawer.kt
│   │   ├── BaseViewModel.kt
│   │   ├── DrawerContent.kt
│   │   ├── DrawerViewModel.kt
│   │   ├── GoogleButton.kt
│   │   ├── LoadingIndicator.kt
│   │   ├── PostItem.kt
│   │   ├── TopAppBar.kt
│   │   └── Utils.kt
│   ├── navigation
│   │   ├── Route.kt
│   │   └── SetupNavGraph.kt
│   ├── screens
│   │   ├── auth
│   │   │   ├── AuthStateEffectIntent.kt
│   │   │   ├── AuthenticationScreen.kt
│   │   │   └── AuthenticationViewModel.kt
│   │   ├── home
│   │   │   ├── HomeScreen.kt
│   │   │   ├── HomeStateIntentEffect.kt
│   │   │   └── HomeViewModel.kt
│   │   └── profile
│   │       ├── ProfileScreen.kt
│   │       ├── ProfileStateIntentEffect.kt
│   │       └── ProfileViewModel.kt
│   └── theme
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
└── utils


```

### Explanation

- **Authentication**: Handled in the `auth` package, with state management in `AuthStateEffectIntent.kt`.
- **Home Screen**: Managed in the `home` package, including ViewModel and UI components.
- **Navigation**: Defined in `Route.kt` and `SetupNavGraph.kt` for a centralized structure.

## Setup and Installation

### Prerequisites

- Android Studio installed with the latest SDK tools.
- Gradle configured for the project.

### Steps

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/your-repo.git
   cd your-repo
   ```

2. Open the project in Android Studio.
3. Sync Gradle and install dependencies.

## Running the Application

1. Ensure you have an emulator or device connected.
2. Build and run the app:

   ```bash
   ./gradlew assembleDebug
   ```

## Testing

- Unit tests are located in the `test` directory.
- Run tests using:

  ```bash
  ./gradlew test
  ```

## Dependencies

- **Jetpack Compose**: For modern UI development.
- **Kotlin Coroutines**: For asynchronous programming.
- **Navigation Component**: For managing app navigation.
- **Material Design**: For consistent UI components.

## Error Handling

The app handles errors gracefully using a state-effect-intent model, ensuring that errors are properly logged and displayed to the user.

--- 

If you'd like any modifications or specific enhancements, let me know!
