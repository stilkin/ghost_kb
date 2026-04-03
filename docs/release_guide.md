# Ghost Keyboard Release Guide

Complete guide for building and deploying Ghost Keyboard as a release APK for sideloading on Android phones.

## 🏗️ Build Release APK

### Step 1: Generate a Signing Key (One-time)

First, you need to create a keystore file to sign your APK:

```bash
keytool -genkey -v -keystore ~/pboard-release.keystore -keyalg RSA -keysize 2048 -validity 10000 -alias pboard
```

You'll be prompted for:
- **Keystore password** - Create a strong password and remember it!
- **Key password** - Can be the same as keystore password
- **Your name, organization, etc.** - Fill in as desired

This creates `~/pboard-release.keystore` - **keep this file safe!** You'll need it for future updates.

### Step 2: Configure Gradle Signing

Edit `app/build.gradle.kts` to add signing configuration:

```kotlin
android {
    // ... existing config ...
    
    signingConfigs {
        create("release") {
            storeFile = file(System.getProperty("user.home") + "/pboard-release.keystore")
            storePassword = "YOUR_KEYSTORE_PASSWORD"
            keyAlias = "pboard"
            keyPassword = "YOUR_KEY_PASSWORD"
        }
    }
    
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}
```

**⚠️ Security Note:** Don't commit passwords to git! For production, use environment variables instead:

```kotlin
signingConfigs {
    create("release") {
        storeFile = file(System.getenv("PBOARD_KEYSTORE_PATH") ?: "~/pboard-release.keystore")
        storePassword = System.getenv("PBOARD_KEYSTORE_PASSWORD")
        keyAlias = System.getenv("PBOARD_KEY_ALIAS") ?: "pboard"
        keyPassword = System.getenv("PBOARD_KEY_PASSWORD")
    }
}
```

Then set environment variables before building:

```bash
export PBOARD_KEYSTORE_PATH=~/pboard-release.keystore
export PBOARD_KEYSTORE_PASSWORD=your_password
export PBOARD_KEY_ALIAS=pboard
export PBOARD_KEY_PASSWORD=your_password
```

### Step 3: Build Release APK

```bash
cd /home/srt/AndroidStudioProjects/PBoard

# Build the release APK
./gradlew assembleRelease

# Output will be at:
# app/build/outputs/apk/release/app-release.apk
```

The build process will:
- Compile all Kotlin code
- Process resources
- Sign the APK with your keystore
- Optimize the final package

---

## 📱 Install on Phone

### Option A: Using ADB (Android Debug Bridge)

**Prerequisites:**
- Android SDK Platform Tools installed
- Phone connected via USB
- USB debugging enabled on phone

```bash
# Connect your phone via USB and enable USB debugging

# Install the APK
adb install app/build/outputs/apk/release/app-release.apk

# Or reinstall if already installed:
adb install -r app/build/outputs/apk/release/app-release.apk

# Verify installation
adb shell pm list packages | grep pboard
```

### Option B: Manual Sideload

1. **Transfer APK to phone:**
   ```bash
   adb push app/build/outputs/apk/release/app-release.apk /sdcard/Download/
   ```

2. **On your phone:**
   - Open Files app
   - Navigate to Downloads folder
   - Tap `app-release.apk`
   - Tap "Install"
   - Allow installation from unknown sources if prompted

### Option C: Share the APK

```bash
# Copy to a location you can share
cp app/build/outputs/apk/release/app-release.apk ~/Downloads/Ghost.apk

# Then email, upload to cloud storage, etc.
```

---

## ⚙️ Enable Keyboard on Phone

After installation, users need to enable the keyboard:

1. **Open Ghost app** - Tap the launcher icon
2. **Click "Enable Keyboard"** - Opens Settings → Languages & input → Virtual keyboard
3. **Enable "Ghost Keyboard"** - Toggle it on
4. **Click "Select Keyboard"** - Opens input method selector
5. **Choose "Ghost Keyboard"** as default
6. **Open any text field** - Keyboard should appear!

---

## 🔍 Verification & Debugging

### Verify Installation

```bash
# Check if app is installed
adb shell pm list packages | grep pboard

# View detailed app info
adb shell dumpsys package be.pocito.pboard

# View app logs while using keyboard
adb logcat | grep pboard

# Clear app data (if needed)
adb shell pm clear be.pocito.pboard
```

### View Logs

```bash
# Real-time logs
adb logcat | grep pboard

# Save logs to file
adb logcat > pboard_logs.txt

# Filter by log level (E=Error, W=Warning, I=Info, D=Debug)
adb logcat *:E | grep pboard
```

### Test Keyboard

```bash
# Open a text field in a test app
adb shell am start -a android.intent.action.MAIN -n com.android.settings/.Settings

# Or open Notes app
adb shell am start -n com.google.android.keep/.activities.MainActivity
```

---

## 📦 Distribution Options

### 1. Direct APK Share
- Email the APK file
- Upload to cloud storage (Google Drive, Dropbox, OneDrive, etc.)
- Share via messaging apps
- Host on personal website

**Pros:** Simple, no account needed
**Cons:** Manual updates required

### 2. GitHub Releases
```bash
# Create a release on GitHub with the APK attached
# Users can download directly from releases page
# Supports version history and release notes
```

**Pros:** Version control, release notes, easy updates
**Cons:** Requires GitHub account

### 3. Google Play Store (Future)
- Requires Google Play Developer account ($25 one-time fee)
- Professional distribution platform
- Automatic updates
- User reviews and ratings
- Analytics and crash reporting

**Pros:** Largest audience, automatic updates, professional
**Cons:** Review process, fees, more complex

### 4. F-Droid (Open Source)
- Free app store for open source Android apps
- No account needed
- Good for privacy-conscious users
- Automatic builds from source

**Pros:** Free, open source friendly, privacy-focused
**Cons:** Requires open source license, slower review process

---

## 🔐 Security Checklist

Before distributing:

- ✅ Build in release mode (not debug)
- ✅ Sign with your keystore
- ✅ Test on multiple devices
- ✅ Verify keyboard works in various apps (Notes, Messages, Twitter, etc.)
- ✅ Check for crashes in logcat
- ✅ Keep keystore file safe and backed up
- ✅ Don't commit keystore to git
- ✅ Use strong passwords for keystore
- ✅ Store keystore in secure location
- ✅ Consider using environment variables for passwords

---

## 📝 Version Management

For future updates, increment the version in `app/build.gradle.kts`:

```kotlin
android {
    defaultConfig {
        applicationId = "be.pocito.pboard"
        minSdk = 30
        targetSdk = 36
        versionCode = 2  // Increment for each release
        versionName = "1.1"  // User-facing version (e.g., "1.0", "1.1", "2.0")
    }
}
```

**Version Code:** Internal number (must increase with each release)
**Version Name:** User-visible version string

Then rebuild and redistribute:

```bash
./gradlew assembleRelease
```

---

## 🆘 Troubleshooting

### "Installation failed"
```bash
# Ensure USB debugging is enabled on phone
# Try restarting ADB
adb kill-server
adb start-server

# Check phone storage has space
adb shell df /data

# Try uninstalling first
adb uninstall be.pocito.pboard
adb install app/build/outputs/apk/release/app-release.apk
```

### "App crashes on launch"
```bash
# Check logcat for errors
adb logcat | grep pboard

# Look for stack traces and error messages
# Common issues:
# - Missing resources
# - Null pointer exceptions
# - Permission issues

# Test on emulator first
# Emulator → Settings → Apps → Ghost → Permissions
```

### "Keyboard doesn't appear"
```bash
# Verify app is installed
adb shell pm list packages | grep pboard

# Check if keyboard is enabled
adb shell settings get secure enabled_input_methods

# Verify keyboard is set as default
adb shell settings get secure default_input_method

# Restart phone if needed
adb reboot
```

### "Build fails with signing error"
```bash
# Verify keystore file exists
ls -la ~/pboard-release.keystore

# Check keystore password is correct
keytool -list -v -keystore ~/pboard-release.keystore

# Verify gradle config has correct paths and passwords
# Check for typos in build.gradle.kts
```

---

## 📋 Release Checklist

Before each release:

- [ ] Update version code and version name in `build.gradle.kts`
- [ ] Update `README.md` with new features/changes
- [ ] Test on multiple Android versions (API 29, 33, 36)
- [ ] Test on multiple devices (phone, tablet, different screen sizes)
- [ ] Test keyboard in multiple apps (Notes, Messages, Twitter, Email, etc.)
- [ ] Check for crashes in logcat
- [ ] Verify all 11 font styles work correctly
- [ ] Test style persistence (select style, close, reopen)
- [ ] Build release APK: `./gradlew assembleRelease`
- [ ] Sign APK with keystore
- [ ] Test installation: `adb install -r app/build/outputs/apk/release/app-release.apk`
- [ ] Create release notes
- [ ] Upload to distribution platform (GitHub, Google Play, etc.)
- [ ] Announce release

---

## 🔄 Update Process

For users updating from a previous version:

```bash
# Reinstall with -r flag (replaces existing app)
adb install -r app/build/outputs/apk/release/app-release.apk

# Or uninstall first, then install
adb uninstall be.pocito.pboard
adb install app/build/outputs/apk/release/app-release.apk
```

**Note:** User preferences (selected style) are preserved during updates because they're stored in SharedPreferences.

---

## 📚 Additional Resources

- [Android Signing Documentation](https://developer.android.com/studio/publish/app-signing)
- [ADB Documentation](https://developer.android.com/studio/command-line/adb)
- [Google Play Console](https://play.google.com/console)
- [F-Droid Documentation](https://f-droid.org/docs/)
- [GitHub Releases](https://docs.github.com/en/repositories/releasing-projects-on-github/about-releases)

---

## 💡 Tips

- **Keep backups:** Store your keystore file in multiple secure locations
- **Document passwords:** Use a password manager to store keystore passwords
- **Test thoroughly:** Always test on real devices before releasing
- **Monitor crashes:** Check logcat regularly for user-reported issues
- **Gather feedback:** Ask users for feedback on new features
- **Plan updates:** Keep a roadmap of planned features and improvements

---

**Happy releasing!** 🚀
