[README.md](https://github.com/user-attachments/files/28822072/README.md)
# 📱 AppsCompose

A collection of Android applications built with **Jetpack Compose** and modern Android architecture patterns, developed as part of an ongoing journey to deepen expertise in Kotlin, Clean Architecture, and the Android Jetpack ecosystem.

---

## 📂 Projects

### 🍽️ AdmRestaurant *(In progress)*
> Restaurant administration app

| | |
|---|---|
| **Architecture** | MVI + Clean Architecture |
| **DI** | Hilt |
| **UI** | Jetpack Compose |
| **Data** | REST API consumption |

A restaurant management application being built with **MVI (Model-View-Intent)** to explore unidirectional data flow at scale. Planned features include menu management, orders, and table administration backed by a custom REST API.

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
