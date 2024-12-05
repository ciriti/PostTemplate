[![Android CI](https://github.com/ciriti/MultiMuduleBestPractices/actions/workflows/android-ci.yml/badge.svg)](https://github.com/ciriti/MultiMuduleBestPractices/actions/workflows/android-ci.yml)

# MultiMuduleProject

## Introduction

`MultiMuduleProject` is an Android application designed with a modular architecture to promote scalability, testability, and maintainability. It demonstrates best practices for structuring multi-module Android apps using modern Android development tools like Jetpack Compose, Kotlin Coroutines, and Dependency Injection (DI).

---

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [App Flow](#app-flow)
- [Architecture](#architecture)
- [Modules Overview](#modules-overview)
- [Setup and Installation](#setup-and-installation)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [Dependencies](#dependencies)
- [Error Handling](#error-handling)

---

## Features

- **Multi-Module Architecture**: A modular structure for improved reusability and team collaboration.
- **Authentication**: Secure login and session management.
- **Navigation**: Centralized navigation with a flexible NavGraph.
- **Home and Profile Screens**: Feature-specific modules for core app functionality.
- **State Management**: Clean separation of UI state and business logic.
- **Local and Remote Data Handling**: Uses Room Database and Retrofit for managing data sources.

---

## App Flow

- The app begins with the **Authentication Screen** for user login.
- After authentication, users can access the **Home Screen** and **Profile Screen**, where feature-specific data is displayed.
- The navigation is centrally managed using a `NavGraph`, ensuring smooth transitions between modules.

---

## Architecture

The application follows **Clean Architecture** principles, organized into feature modules. Each module is further divided into **data**, **domain**, and **presentation layers** for better separation of concerns.

---

### Layers

1. **Presentation Layer**:
    - UI screens and components.
    - ViewModels for state management and business logic handling.

2. **Domain Layer**:
    - Defines core application logic.
    - Includes use cases, state models, and data transformation logic.

3. **Data Layer**:
    - Responsible for data management.
    - Local (Room database) and remote (API services) sources.
    - Repository pattern used for data abstraction.

---

### Modules Overview

#### **App Module**

- Acts as the entry point of the application.
- Contains the main activity, application-level DI configurations (`AppModule`), and navigation setup (`SetupNavGraph`).

#### **Core Module**

- Shared logic used across feature modules.
- **Core/Data**:
    - `local`: Room database entities, DAOs, and database configuration.
    - `remote`: Retrofit services and network clients.
    - `repository`: Shared repositories (e.g., `AuthRepository`).
- **Core/UI**:
    - Shared UI components (e.g., navigation drawer, loading indicators).

#### **Feature Modules**

- **Auth Module**:
    - Handles authentication (login, logout, session management).
    - Contains its own `AuthRepository`, ViewModels, and UI screens (e.g., `AuthenticationScreen`).

- **Posts Module**:
    - Displays user posts.
    - Includes ViewModels (`HomeViewModel`), repositories, and data models for posts.

- **Profile Module**:
    - Displays user profile details.
    - Includes ViewModels (`ProfileViewModel`), repositories, and data models for user information.

---

### Updated Directory Structure

```
MultiMuduleProject/
├── app
│   ├── manifests
│   ├── kotlin+java
│   │   ├── io.github.ciriti.di
│   │   │   └── AppModule.kt
│   │   ├── io.github.ciriti.ui.navigation
│   │   │   ├── DrawerViewModel.kt
│   │   │   └── SetupNavGraph.kt
│   │   ├── App.kt
│   │   └── MainActivity.kt
│   └── ...
├── core
│   ├── data
│   │   ├── di
│   │   │   └── DataModule.kt
│   │   ├── local
│   │   │   ├── AppDatabase.kt
│   │   │   ├── PostDao.kt
│   │   │   ├── PostEntity.kt
│   │   │   ├── UserDao.kt
│   │   │   └── UserEntity.kt
│   │   ├── models
│   │   │   ├── PostDto.kt
│   │   │   └── UserDto.kt
│   │   ├── remote
│   │   │   ├── ApiService.kt
│   │   │   └── NetworkClient.kt
│   │   └── repository
│   │       └── AuthRepository.kt
│   ├── ui
│   │   ├── components
│   │   │   ├── AdaptiveNavigationDrawer.kt
│   │   │   ├── LoadingIndicator.kt
│   │   │   └── ...
│   │   ├── navigation
│   │   │   └── Route.kt
│   │   └── theme
│   │       ├── Color.kt
│   │       ├── Theme.kt
│   │       └── Type.kt
│   └── ...
├── feature
│   ├── auth
│   │   ├── di
│   │   │   └── AuthModule.kt
│   │   ├── ui.navigation
│   │   │   ├── AuthenticationScreen.kt
│   │   │   ├── AuthenticationViewModel.kt
│   │   │   └── AuthStateEffectIntent.kt
│   │   └── ...
│   ├── posts
│   │   ├── di
│   │   │   └── PostsModule.kt
│   │   ├── ui.navigation
│   │   │   ├── HomeScreen.kt
│   │   │   ├── HomeViewModel.kt
│   │   │   └── ...
│   │   └── ...
│   ├── profile
│   │   ├── di
│   │   │   └── ProfileModule.kt
│   │   ├── ui.navigation
│   │   │   ├── ProfileScreen.kt
│   │   │   ├── ProfileViewModel.kt
│   │   │   └── ...
│   │   └── ...
│   └── ...
└── ...

```

---

## Setup and Installation

### Prerequisites

- **Android Studio**: Latest version installed.
- **Kotlin**: Make sure Kotlin is configured in the IDE.

---

### Steps to Run

1. Clone the repository:

   ```bash
   git clone git@github.com:ciriti/MultiMuduleProject.git
   cd MultiMuduleProject
   ```

2. Open the project in Android Studio.

3. Sync the project dependencies by running:

   ```bash
   ./gradlew dependencies
   ```

4. Run the app:

   ```bash
   ./gradlew assembleDebug
   ```

---

## Testing

- **Unit Tests**:
  Run all unit tests:

  ```bash
  ./gradlew test
  ```

- **Instrumentation Tests**:
  Run all Android instrumentation tests:

  ```bash
  ./gradlew connectedAndroidTest
  ```

---

## Dependencies

- **Jetpack Compose**: For building the UI.
- **Koin**: Dependency injection.
- **Retrofit**: REST API integration.
- **Room**: Local database.
- **Kotlin Coroutines**: Asynchronous programming.

---

#Here’s how I’ve updated the **Error Handling** section to reflect your usage of the **Either pattern** from the **Arrow library**:

---

## Error Handling

The app uses structured error handling with ViewModels and state management to ensure consistent behavior and proper error displays in the UI.

### Either Pattern with Arrow Library

The **Either** pattern from the **Arrow library** is used to encapsulate operations that may result in a success (`Right`) or failure (`Left`). This approach ensures a functional programming style for error handling, promoting immutability and type safety.

Example implementation:

```kotlin
override suspend fun getPosts(): Either<Throwable, List<Post>> =
    check {
        val postsDto = postRepository.getPosts().getOrHandle { throw it }
        postsDto.map { postDto ->
            postDto.toDomain(postDto.userId)
        }
    }
```

### Benefits of the Either Pattern

- **Explicit Error Handling**: Errors are part of the function signature, making them explicit and easier to reason about.
- **Immutable Results**: The result (`Either`) is immutable, ensuring no unexpected changes to the error state.
- **Structured Failures**: The use of custom failure types like `AppFailure.NetworkFailure` provides detailed and categorized error handling.

### App-Specific Error Types

The app defines its custom failures (`AppFailure`) to handle common error scenarios:

1. **NetworkFailure**: Captures network-related errors (e.g., no internet).
2. **DatabaseFailure**: Handles database operation errors.
3. **ValidationFailure**: Handles invalid data inputs or arguments.
4. **UnknownFailure**: Captures any unhandled or unexpected errors.

---

This structured approach ensures that errors are gracefully managed and propagated throughout the app, resulting in better user feedback and a more reliable application.

---
