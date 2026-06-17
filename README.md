# 📱 AppsCompose

A collection of Android applications built with **Jetpack Compose** and modern Android architecture patterns, developed as part of an ongoing journey to deepen expertise in Kotlin, Clean Architecture, and the Android Jetpack ecosystem.

---

## 📂 Projects

### 🍽️ AdmRestaurant
> Restaurant administration app with full CRUD

| | |
|---|---|
| **Architecture** | MVI + Clean Architecture |
| **DI** | Hilt |
| **UI** | Jetpack Compose |
| **Data** | REST API consumption (custom backend with SQL Server Express) |

A restaurant management application built from scratch with **MVI (Model-View-Intent)** to explore unidirectional data flow at scale. Features full **CRUD operations** for:

- 🏷️ Categories
- 🍔 Dishes

Built on a self-hosted backend (Node.js + SQL Server Express) designed alongside the app. Demonstrates a complete MVI implementation: sealed `Intent` classes, immutable `State`, single-entry-point ViewModels, and parallel data loading with `async`/`await`. Includes mappers between API DTOs and domain models, a centralized `safeApiCall` wrapper using Kotlin's `Result` for error handling, and Compose forms with dynamic validation (`derivedStateOf`, `ExposedDropdownMenuBox`, dialog-based add/edit flows).

---

### 📚 AdminLibraryApp
> Library management system with full CRUD

| | |
|---|---|
| **Architecture** | MVVM + Clean Architecture |
| **DI** | Hilt |
| **UI** | Jetpack Compose |
| **Data** | REST API consumption |

A complete library administration app featuring full **CRUD operations** across multiple entities:

- 📖 Books
- ✍️ Authors
- 🏷️ Categories
- 🏢 Publishers
- 🔄 Loans

Demonstrates end-to-end feature development: API integration, repository pattern, use cases, ViewModels, and Compose UI — all wired with Hilt.

---

### 🗓️ AgendaApp
> Personal agenda app with local persistence

| | |
|---|---|
| **Architecture** | MVVM + Clean Architecture |
| **DI** | Hilt |
| **UI** | Jetpack Compose |
| **Data** | Room (local database) |

An offline-first agenda application that manages local data entirely through **Room**. Focuses on clean separation of concerns without a remote data source — ideal for understanding how Clean Architecture layers interact with local persistence.

---

### 🎓 CrashCourse
> Jetpack Compose learning exercises

| | |
|---|---|
| **Reference** | Philipp Lackner's Compose Course |
| **UI** | Jetpack Compose |
| **Language** | Kotlin |

A hands-on collection of exercises and mini-projects following **Philipp Lackner's Jetpack Compose crash course**. Covers core Compose concepts including state management, recomposition, layouts, navigation, and side effects.

---

## 🛠️ Tech Stack

| Technology | Usage |
|---|---|
| **Kotlin** | Primary language across all projects |
| **Jetpack Compose** | Declarative UI |
| **MVVM / MVI** | Presentation layer patterns |
| **Clean Architecture** | Layered project structure |
| **Hilt** | Dependency injection |
| **Room** | Local database (AgendaApp) |
| **Retrofit** | REST API communication |
| **Coroutines + Flow** | Async operations and reactive streams |

---

## 👩‍💻 Author

**Adriana Martinez** — Senior Android Developer  
[GitHub @AdrianaMartDev](https://github.com/AdrianaMartDev)
