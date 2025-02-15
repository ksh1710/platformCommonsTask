# platformCommonsTask

A modern Android application showcasing offline-first architecture, real-time synchronization, and Material 3 Design implementation using Jetpack Compose with 
MVVM architecture, paging 3, dependency injection using Hilt, and reactive programming with kotlin Flows

## Get the APK here ↓
- https://drive.google.com/file/d/1PojQXpHdEDOYlvtktt8rWKBqoIwkx8bE/view?usp=sharing

## Considerations made
- using workManger with DI
- Error Handling for failed network requests
- Offline and online synced users displayed on the add User Screen for better visualization
- keeping a check of the synced and not synced users
- encrypting the API Key
- generating the release build
- Better UI for displaying movie details
- pagination for movie list
- retry mechanism for failed network requests

## Features

### User Management
- 📋 Paginated list of users from "reqres.in" API
- ✅ Add users with name and job information
- 📱 Real-time synchronization when network is available using WorkManager

### Movie Catalog
- 🎬 Browse trending movies from TMDB
- 📱 Detailed movie information
- 🖼️ High-quality movie posters
- 📑 Efficient pagination
- 🎯 Smooth navigation between screens

### Technical Features
- 🎨 Modern UI built with Jetpack Compose
- 📐 Material 3 Design Implementation
- 🏗️ MVVM Architecture
- 💉 Dependency Injection with Hilt
- 🔄 Kotlin Coroutines and Flow for reactive programming
- 🗃️ Room Database for local storage
- 📱 Single Activity, Multiple Composable Screens
- 🌐 Retrofit for network operations
- 📊 Paging 3 for efficient data loading

## Architecture

The application follows Clean Architecture principles and MVVM pattern:

```
app/
├── data/
│   ├── model/
│   ├── network/
│   └── repository/
│
├── db/
│   ├── dao/
│   └── entities/
│   ├── dao/
│
├── di/
├── paging/
├── presentation/
│   ├── common/
│   ├── navigation/
│   ├── theme/
│   ├── ui/
│       ├── addUserScreen/
│             ├── components/
│       ├── movieDetailScreen/
│             ├── components/
│       ├── movieListScreen/
│             ├── components/
│       ├── userListScreen/
│             ├── components/
├── utils/
└── viewmodel/
```

### Core Components

**Network Layer**
- Retrofit for API communication
- OkHttp for network interceptors

**API Services:**
- ReqRes API for user management
- TMDB API for movie data

**Local Storage**
- Room Database
- Entity Relationships
- Data Access Objects (DAOs)
  
**Background Processing**
- WorkManager for sync operations
- Network state monitoring
- Periodic sync scheduling

**UI Layer**
- Jetpack Compose
- Material 3 Components
- Coil for image loading
- Custom composable

### Key Components

1. **Repository Layer**
   - Handles data operations
   - Manages offline/online synchronization
   - Implements offline-first strategy

2. **ViewModel Layer**
   - Manages UI state
   - Handles business logic
   - Provides data to UI

3. **UI Layer**
   - Built with Jetpack Compose
   - Implements Material 3 Design
   - Provides real-time sync status feedback

4. **Database Layer**
   - Room Database for local storage
   - Entities and DAOs
   - Offline data persistence

5. **Network Layer**
   - Retrofit for API calls
   - Network status monitoring
   - Background synchronization

## Offline Support

The app implements a robust offline-first architecture:

1. **Local Storage**
   - All data is first saved locally
   - Immediate UI updates from the local database

2. **Sync Management**
   - Automatic background synchronization
   - Visual sync status indicators
   - Network state monitoring

3. **WorkManager Integration**
   - Scheduled sync operations
   - Network-aware sync scheduling
   - Battery-efficient background operations


### SyncStatusIndicator
Three distinct states:
- ✅ Synced: Green checkmark
- 🔄 Syncing: Loading indicator (when online)
- ❌ Not Synced: Red error icon (when offline)

## Getting Started

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle dependencies
4. get the TMDB API key and add it to the local.properties file
5. Run the app

### API Integration

**ReqRes API**

- **User List**: GET https://reqres.in/api/users?page={page}
- **Add User**: POST https://reqres.in/api/users

**TMDB API**

- **Trending Movies**: GET https://api.themoviedb.org/3/trending/movie/day
- **Movie Details**: GET https://api.themoviedb.org/3/movie/{movie_id}
- **Image URL**: http://image.tmdb.org/t/p/w185/{poster_path}


## Future Improvements
- [ ] Add unit tests
      
