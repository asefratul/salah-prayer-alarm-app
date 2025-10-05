# PVIS Salah Prayer Times App

A modern Android TV application for displaying Islamic prayer times, developed for the Pembina Valley Islamic Society (PVIS). The app provides real-time prayer schedules, sunrise/sunset times, and community announcements, specifically designed for Android TV devices and digital displays in mosques.

## Features

### 🕌 Core Functionality
- **Daily Prayer Times**: Displays all five daily prayers (Fajr, Dhuhr, Asr, Maghrib, Isha) with start times, Adhan times, and Iqamah times
- **Jumu'ah Detection**: Automatically shows "Jumu'ah" instead of "Dhuhr" on Fridays
- **Sunrise/Sunset Times**: Real-time sunrise and sunset information
- **Live Clock & Date**: Current time and date display with automatic updates
- **Community Announcements**: Scrolling ticker for mosque announcements with HTML support

### 📺 Android TV Interface
- **TV-Optimized Design**: Clean, Islamic-themed interface designed for large displays
- **Touchscreen Support**: Works with both remote control and touchscreen Android TV devices
- **Digital Display Ready**: Perfect for mosque digital displays and TV screens
- **Real-time Updates**: Automatic data refresh when date changes
- **Progress Indicators**: Loading states for better user experience

### 🔧 Technical Features
- **Firebase Integration**: Real-time database for prayer times and announcements
- **Anonymous Authentication**: Secure Firebase authentication
- **Automatic Date Detection**: Updates prayer times at midnight
- **12-Hour Time Format**: User-friendly time display
- **Error Handling**: Graceful error management and logging

## Screenshots

The app features a TV-optimized interface with:
- Large, clear header section with mosque logo, current time/date, and PVIS branding
- Prominent prayer times table with all five daily prayers, designed for easy reading from a distance
- Sunrise/sunset information with large icons
- Scrolling announcement ticker at the bottom for community updates
- Optimized for 1080p and 4K TV displays

## Installation

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 21 (Android 5.0) or higher
- Android TV device or Android TV emulator
- Firebase project with Realtime Database enabled

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd SalahPrayerAlarmApp
   ```

2. **Firebase Configuration**
   - Create a new Firebase project at [Firebase Console](https://console.firebase.google.com)
   - Enable Realtime Database
   - Download `google-services.json` and place it in the `app/` directory
   - Update the Firebase project settings in the Firebase Console

3. **Database Structure**
   The app expects the following Firebase Realtime Database structure:
   ```
   prayer_time_list/
     └── YYYY-MM-DD/
         ├── Fajr Starts: "05:30"
         ├── Fajr Adhan: "05:25"
         ├── Fajr Iqama: "05:30"
         ├── Dhuhr Starts: "12:15"
         ├── Dhuhr Adhan: "12:10"
         ├── Dhuhr Iqama: "12:15"
         ├── Asr Starts: "15:45"
         ├── Asr Adhan: "15:40"
         ├── Asr Iqama: "15:45"
         ├── Maghrib Starts: "18:20"
         ├── Maghrib Adhan: "18:15"
         ├── Maghrib Iqama: "18:20"
         ├── Isha Starts: "19:45"
         ├── Isha Adhan: "19:40"
         ├── Isha Iqama: "19:45"
         ├── Sunrise: "06:15"
         └── Sunset: "18:30"
   
   announcements/
     └── (announcement text with HTML support)
   ```

4. **Build and Run**
   ```bash
   ./gradlew assembleDebug
   ```

5. **Deploy to Android TV**
   - Install the APK on your Android TV device
   - The app will appear in the Android TV launcher
   - Perfect for mosque digital displays and community centers

## Project Structure

```
app/
├── src/main/
│   ├── java/ca/pvis/salah_prayer_alarm_app/
│   │   ├── MainActivity.java              # Main activity with UI logic
│   │   ├── PrayerTimeModel.java           # Data model for prayer times
│   │   ├── PrayerTimeAdapter.java         # RecyclerView adapter
│   │   ├── DateTimeHelper.java            # Date/time utility functions
│   │   ├── NamazApiService.java           # API service interface
│   │   ├── ApiClient.java                 # Retrofit API client
│   │   ├── NamazResponse.java             # API response model
│   │   └── HijrahDateUtil.java            # Islamic calendar utilities
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_main.xml          # Main activity layout
│   │   │   └── item_prayer_time.xml       # Prayer time item layout
│   │   ├── values/
│   │   │   ├── strings.xml                # String resources
│   │   │   └── colors.xml                 # Color definitions
│   │   └── drawable/                      # Icons and backgrounds
│   └── AndroidManifest.xml                # App manifest
├── build.gradle.kts                       # App-level build configuration
└── google-services.json                   # Firebase configuration
```

## Dependencies

### Core Dependencies
- **AndroidX Libraries**: AppCompat, Material Design, ConstraintLayout
- **Firebase**: Authentication, Realtime Database, Core
- **Retrofit**: HTTP client for API calls
- **Gson**: JSON serialization/deserialization

### Version Information
- **Target SDK**: 34 (Android 14)
- **Minimum SDK**: 21 (Android 5.0)
- **Compile SDK**: 34
- **Java Version**: 11

## Configuration

### Firebase Setup
1. Enable Anonymous Authentication in Firebase Console
2. Set up Realtime Database with the structure shown above
3. Configure database rules for read access:
   ```json
   {
     "rules": {
       "prayer_time_list": {
         ".read": "auth != null"
       },
       "announcements": {
         ".read": "auth != null"
       }
     }
   }
   ```

### Time Format
- Input times should be in 24-hour format (HH:mm)
- The app automatically converts to 12-hour format for display
- All times are displayed in the device's local timezone

## Features in Detail

### Prayer Times Display
- Shows all five daily prayers with three time columns:
  - **STARTS**: When the prayer time begins
  - **ADHAN**: Call to prayer time
  - **IQAMAH**: Congregational prayer start time
- Special handling for Friday prayers (Jumu'ah)
- Large, TV-friendly text and layout for easy reading from a distance

### Real-time Updates
- Automatic date change detection
- Prayer times refresh at midnight
- Live clock updates every second
- Firebase real-time data synchronization

### Announcement System
- HTML-supported announcement ticker
- Clickable links support
- Automatic scrolling text
- Real-time updates from Firebase
- Perfect for displaying mosque announcements on large screens

### Android TV Specific Features
- **Leanback Launcher Support**: Appears in Android TV's main launcher
- **Remote Control Navigation**: Works with Android TV remote controls
- **Touchscreen Support**: Compatible with touchscreen Android TV devices
- **Large Display Optimization**: Text and UI elements sized for TV viewing
- **Always-On Display**: Designed to run continuously on mosque displays

## Development

### Building the Project
```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease
```

### Testing
```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is developed for the Pembina Valley Islamic Society (PVIS). All rights reserved.

## Support

For support and questions, please contact the development team or create an issue in the repository.

## Acknowledgments

- Developed for Pembina Valley Islamic Society (PVIS)
- Firebase for real-time database services
- Android TV platform for large display support
- Android community for open-source libraries
- Islamic community for prayer time calculations

---

**Note**: This Android TV app is specifically designed for the Pembina Valley Islamic Society and includes their branding and specific requirements. It's optimized for mosque digital displays and Android TV devices. For use by other organizations, please update the branding, colors, and Firebase configuration accordingly.
