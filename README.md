# PyCom - Python IDE for Android

A complete Android Studio project skeleton for a Python IDE mobile application. This project provides a working UI with bottom navigation between Editor, Terminal, File Manager, and Settings screens. Python execution is currently stubbed with placeholders and requires Chaquopy integration for real functionality.

## Project Overview

**PyCom** is an Android application designed to run Python code on Android devices. This project includes:

- ✅ Complete Android Studio project structure
- ✅ Working UI with bottom navigation (4 tabs)
- ✅ MVVM architecture with ViewModels and Repository pattern
- ✅ File management for .py scripts
- ✅ Persistent settings (theme, font size)
- ⚠️ **Placeholder Python execution** (requires Chaquopy integration)

## Project Details

| Property | Value |
|----------|-------|
| **App Name** | PyCom |
| **Package** | com.pycom.app |
| **Language** | Java |
| **Min SDK** | 29 (Android 10) |
| **Target SDK** | 35 |
| **Build System** | Gradle (Groovy DSL) |
| **Architecture** | MVVM + Repository Pattern |

## Project Structure

```
PyCom/
├── app/
│   ├── build.gradle                    # App module build configuration
│   ├── proguard-rules.pro              # ProGuard configuration
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml     # App manifest with permissions
│           ├── java/com/pycom/app/
│           │   ├── ui/                 # UI Layer
│           │   │   ├── MainActivity.java
│           │   │   ├── editor/
│           │   │   │   └── EditorFragment.java
│           │   │   ├── terminal/
│           │   │   │   └── TerminalFragment.java
│           │   │   ├── files/
│           │   │   │   ├── FileManagerFragment.java
│           │   │   │   └── FileAdapter.java
│           │   │   └── settings/
│           │   │       └── SettingsFragment.java
│           │   ├── viewmodel/          # ViewModels (MVVM)
│           │   │   ├── EditorViewModel.java
│           │   │   ├── TerminalViewModel.java
│           │   │   ├── FilesViewModel.java
│           │   │   └── SettingsViewModel.java
│           │   ├── data/               # Data Layer
│           │   │   ├── repository/
│           │   │   │   └── ProjectRepository.java
│           │   │   └── local/
│           │   │       └── PreferencesManager.java
│           │   └── executor/           # ⚠️ PLACEHOLDER STUBS
│           │       ├── PythonExecutorStub.java
│           │       └── ShellExecutorStub.java
│           ├── res/                    # Resources
│           │   ├── layout/             # XML layouts
│           │   ├── values/             # Strings, colors, themes
│           │   ├── drawable/           # Vector icons
│           │   ├── menu/               # Bottom navigation menu
│           │   └── xml/                # Backup rules
│           └── assets/                 # Assets
│               ├── sample_projects/    # Sample Python files
│               │   ├── hello_world.py
│               │   └── calculator.py
│               └── python_runtime/     # ⚠️ PLACEHOLDER
│                   ├── interpreter.py
│                   ├── pip_runner.py
│                   └── site-packages/
├── build.gradle                        # Root build configuration
├── settings.gradle                     # Project settings
├── gradle.properties                   # Gradle properties
└── README.md                           # This file
```

## Getting Started

### Prerequisites

- **Android Studio** (Latest stable version recommended)
- **JDK 17** or higher
- **Android SDK** with API Level 29+ installed
- **Gradle 8.2+** (included in project)

### Opening the Project in Android Studio

1. Launch **Android Studio**
2. Select **"Open an Existing Project"**
3. Navigate to this directory and select it
4. Wait for **Gradle sync** to complete
5. If prompted, accept any SDK updates

### Building the Project

**Using Android Studio:**
- Click **Build** → **Make Project**
- Or press `Ctrl+F9` (Windows/Linux) or `Cmd+F9` (Mac)

**Using Command Line:**
```bash
# On Unix/Mac/Linux:
./gradlew build

# On Windows:
gradlew.bat build
```

### Running on Device or Emulator

**Using Android Studio:**
1. Connect an Android device (with USB debugging enabled) **OR** start an Android Emulator
2. Click the **Run** button (green play icon)
3. Select your device/emulator from the list

**Using Command Line:**
```bash
# Install debug APK:
./gradlew installDebug

# Run on connected device:
adb shell am start -n com.pycom.app/.ui.MainActivity
```

## Features

### ✅ Implemented Features

1. **Bottom Navigation UI**
   - 4 tabs: Editor, Terminal, Files, Settings
   - Smooth navigation between screens
   - Material Design components

2. **Code Editor Screen**
   - Multi-line text editor with monospace font
   - Run button (FAB) that triggers Python execution stub
   - Sample Python code pre-loaded

3. **Terminal Screen**
   - Scrollable console output
   - Command input field
   - Fake shell responses for common commands

4. **File Manager Screen**
   - RecyclerView listing of .py files
   - Swipe-to-refresh functionality
   - Sample Python files from assets
   - New file button (stub)

5. **Settings Screen**
   - Dark mode toggle (persisted)
   - Font size slider (persisted)
   - Theme changes applied immediately

6. **Data Persistence**
   - SharedPreferences for user settings
   - File I/O for Python scripts in app-specific external storage
   - Automatic fallback to internal storage if external storage unavailable

7. **MVVM Architecture**
   - ViewModels for each screen
   - LiveData for reactive UI updates
   - Repository pattern for data access

### ⚠️ Placeholder Features (Require Integration)

The following features are **stubbed** and require Chaquopy integration:

1. **Python Code Execution** (`PythonExecutorStub.java`)
   - Currently returns fake output
   - Shows placeholder messages
   - See integration guide below

2. **Terminal Shell Execution** (`ShellExecutorStub.java`)
   - Responds to basic commands with fake output
   - No real process execution

3. **Pip Package Management**
   - Not implemented
   - Requires Chaquopy build-time or runtime pip configuration

## Placeholder Locations

All placeholders are clearly marked with comments and TODOs:

| File | Location | Purpose |
|------|----------|---------|
| `PythonExecutorStub.java` | `app/src/main/java/com/pycom/app/executor/` | Python code execution |
| `ShellExecutorStub.java` | `app/src/main/java/com/pycom/app/executor/` | Terminal shell commands |
| `interpreter.py` | `app/src/main/assets/python_runtime/` | Python runtime config |
| `pip_runner.py` | `app/src/main/assets/python_runtime/` | Pip integration config |
| `site-packages/` | `app/src/main/assets/python_runtime/` | Python packages directory |

## Integrating Chaquopy (Python Runtime)

To enable **real Python execution**, follow these steps:

### Step 1: Add Chaquopy Plugin to Root `build.gradle`

```groovy
buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url "https://chaquo.com/maven" }  // Add this line
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:8.2.0'
        classpath 'com.chaquo.python:gradle:15.0.1'  // Add this line
    }
}
```

### Step 2: Apply Plugin in `app/build.gradle`

```groovy
plugins {
    id 'com.android.application'
    id 'com.chaquo.python'  // Add this line
}
```

### Step 3: Configure Python in `app/build.gradle`

Add inside the `android` block's `defaultConfig`:

```groovy
defaultConfig {
    applicationId "com.pycom.app"
    minSdk 29
    targetSdk 35
    versionCode 1
    versionName "1.0"

    // Add Chaquopy configuration
    python {
        buildPython "/usr/bin/python3"  // Path to Python 3 on your system
        
        pip {
            // Install packages at build time
            install "numpy"
            install "matplotlib"
            install "requests"
        }
    }
}
```

### Step 4: Replace `PythonExecutorStub` with Chaquopy API

**Current stub code:**
```java
public String execute(String code) {
    return "[PLACEHOLDER OUTPUT]...";
}
```

**Replace with Chaquopy integration:**
```java
import com.chaquo.python.Python;
import com.chaquo.python.PyObject;

public String execute(String code) {
    if (!Python.isStarted()) {
        Python.start(new AndroidPlatform(context));
    }
    
    Python py = Python.getInstance();
    
    // Create a Python console
    PyObject console = py.getModule("code");
    PyObject interpreter = console.callAttr("InteractiveConsole");
    
    // Capture output
    PyObject io = py.getModule("io");
    PyObject stringIO = io.callAttr("StringIO");
    
    PyObject sys = py.getModule("sys");
    sys.put("stdout", stringIO);
    sys.put("stderr", stringIO);
    
    // Execute code
    try {
        interpreter.callAttr("push", code);
        return stringIO.callAttr("getvalue").toString();
    } catch (Exception e) {
        return "Error: " + e.getMessage();
    }
}
```

### Step 5: Sync and Rebuild

1. Click **File** → **Sync Project with Gradle Files**
2. Wait for sync to complete
3. Build and run the app

### Resources

- [Chaquopy Documentation](https://chaquo.com/chaquopy/doc/current/)
- [Chaquopy GitHub](https://github.com/chaquo/chaquopy)
- [Android Platform Setup](https://chaquo.com/chaquopy/doc/current/android.html)

## Storage Paths

The app uses **app-specific external storage** which works on all Android versions (including API 33+) without requiring special permissions.

| Purpose | Path |
|---------|------|
| **Python Scripts** | `/storage/emulated/0/Android/data/com.pycom.app/files/PyCom/projects/` |
| **Python Packages** | `/storage/emulated/0/Android/data/com.pycom.app/files/PyCom/site-packages/` |
| **User Preferences** | SharedPreferences (`pycom_preferences`) |

### Storage Implementation

**App-Specific Storage Benefits:**
- ✅ Works on **all API levels** (29+) including API 33+
- ✅ **No runtime permissions required**
- ✅ Automatically cleaned up when app is uninstalled
- ✅ Accessible via file managers on most devices
- ✅ Immune to scoped storage restrictions

The storage is implemented using `Context.getExternalFilesDir(null)` in `ProjectRepository.java`.

### Permissions

Required permissions declared in `AndroidManifest.xml`:

- `INTERNET` (for potential cloud features - placeholder)
- `WAKE_LOCK` (for long-running Python scripts - placeholder)

**Note:** No storage permissions are required because the app uses app-specific external storage. If you need to access shared storage directories in the future, consider using:
- **MANAGE_EXTERNAL_STORAGE** permission (API 30+) - requires special Play Store justification
- **Storage Access Framework (SAF)** - user-selected file access
- **MediaStore API** - for media files only

## Sample Python Files

Two sample Python files are included in `app/src/main/assets/sample_projects/`:

1. **`hello_world.py`** - Basic Python syntax demonstration
2. **`calculator.py`** - Functions and calculations example

These files are automatically copied to the projects directory on first launch.

## Dependencies

Current dependencies (see `app/build.gradle`):

- **AndroidX AppCompat** (1.6.1) - Backward compatibility
- **Material Components** (1.11.0) - Material Design UI
- **AndroidX Navigation** (2.7.7) - Fragment navigation
- **AndroidX Lifecycle** (2.7.0) - ViewModels and LiveData
- **AndroidX RecyclerView** (1.3.2) - File list display
- **AndroidX ConstraintLayout** (2.1.4) - Layouts

## Known Limitations

1. **No Python Execution** - Requires Chaquopy integration
2. **No Pip Installation** - Requires Chaquopy pip configuration
3. **No Syntax Highlighting** - Would require additional code editor library
4. **No Code Completion** - Would require Python AST parsing
5. **Launcher Icons** - Placeholder adaptive icons only (generate proper icons for production)

## Next Steps / Future Enhancements

After integrating Chaquopy, consider:

1. **Syntax Highlighting** - Integrate CodeView or similar library
2. **Code Completion** - Add IntelliSense-like features
3. **Package Manager UI** - Build UI for pip install/uninstall
4. **Project Management** - Multi-file projects with folder support
5. **Cloud Sync** - Save projects to cloud storage
6. **Debugging Support** - Python debugger integration
7. **Output Formatting** - Rich console output with colors
8. **Performance** - Optimize for large files and long-running scripts

## Troubleshooting

### Gradle Sync Failed
- Ensure you have **JDK 17** installed
- Check internet connection for dependency downloads
- Invalidate caches: **File** → **Invalidate Caches / Restart**

### App Crashes on Launch
- Check `AndroidManifest.xml` for missing declarations
- Verify all resource files are present
- Check Logcat for stack traces

### Storage Permissions Denied
- Manually grant permissions in device Settings
- For API 33+, use new media permissions
- Check `requestStoragePermissions()` in `MainActivity.java`

### Python Execution Not Working
- Verify Chaquopy is properly configured
- Check build.gradle for correct plugin application
- See PythonExecutorStub.java comments for integration code

## Contributing

This is a skeleton project. To extend it:

1. Fork the repository
2. Create a feature branch
3. Implement your enhancement
4. Test on real devices (API 29+)
5. Submit a pull request

## License

This project skeleton is provided as-is for educational and development purposes.

---

## Quick Reference

**Build Command:**
```bash
./gradlew build
```

**Install Debug APK:**
```bash
./gradlew installDebug
```

**Clean Build:**
```bash
./gradlew clean build
```

**View Project Structure:**
```bash
./project_info.sh
```

---

**Created:** October 2025  
**Version:** 1.0  
**Target Platform:** Android 10+ (API 29+)
