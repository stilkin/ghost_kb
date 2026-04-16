# Building Ghost Keyboard

Guide for building Ghost Keyboard from source.

## Prerequisites

1. **Android SDK** — download from [the Android Studio website](https://developer.android.com/studio)
2. **Oracle Java JDK 21** — download from [the Oracle website](https://www.oracle.com/java/technologies/downloads/#java21) and ensure `JAVA_HOME` is set

## Build Commands

```bash
# Debug build
./gradlew assembleDebug

# Install on connected device/emulator
./gradlew installDebug

# Run unit tests
./gradlew test

# Run instrumented tests (requires connected device/emulator)
./gradlew connectedAndroidTest
```

For release builds, see the [Release Guide](release_guide.md).

## Project Structure

```
ghost_kb/
├── app/src/main/java/be/pocito/pboard/
│   ├── PBoardIME.kt              # Main IME service
│   ├── MainActivity.kt            # Setup/launcher activity
│   ├── style/
│   │   ├── FontStyle.kt           # Style enum
│   │   ├── FontStyles.kt          # Character mappings
│   │   └── FontStyleTransformer.kt # Transformation engine
│   ├── ui/
│   │   └── StyleSelector.kt       # Style picker dialog
│   └── preferences/
│       └── KeyboardPreferences.kt # Settings persistence
├── app/src/main/res/
│   ├── layout/                    # UI layouts
│   ├── xml/                       # Keyboard layout & metadata
│   ├── drawable/                  # Key styling
│   └── values/                    # Colors & strings
└── docs/
    ├── building.md                # This file
    └── release_guide.md           # Release build & distribution
```

## Architecture

- **InputMethodService** — Core IME framework integration
- **KeyboardView** — Keyboard UI rendering
- **SharedPreferences** — User preference persistence
- **Character Mapping** — O(1) Unicode transformation via HashMap

### Data Flow

```
onKey(keyCode) → transformCharacter(char, style) → FontStyles[style].map[char] → currentInputConnection.commitText()
```

### Key Components

**Core IME** — `PBoardIME.kt` extends `InputMethodService` and implements `OnKeyboardActionListener`. It owns the keyboard lifecycle, handles key events via `onKey()`, manages shift state, and delegates character transformation. Style cycling uses `setFontStyle()` which persists to `KeyboardPreferences`.

**Style System** — Three files:
- `FontStyle.kt` — enum of 11 styles (NORMAL, DOUBLE_STRUCK, CURSIVE, etc.)
- `FontStyles.kt` — singleton `object` per style, each holding a `HashMap<Char, String>` for O(1) lookup
- `FontStyleTransformer.kt` — stateless singleton; falls back to the original character when no mapping exists

**Persistence** — `KeyboardPreferences.kt` wraps `SharedPreferences`, lazily initialized, stores the active `FontStyle`.

**UI** — `StyleSelector.kt` is a `PopupWindow` grid for picking a style. Keyboard layouts are defined in `res/xml/qwerty.xml` (letters) and `res/xml/symbols.xml` (numbers/symbols).

## Font Coverage

| Style | Coverage |
|-------|----------|
| Double-struck | 95% (includes numbers) |
| Cursive | 95% (includes numbers) |
| Cursive Bold | 95% (includes numbers) |
| Fraktur | 90% |
| Fraktur Bold | 90% |
| Fullwidth | 100% |
| Small Caps | 80% |
| Bubble | 95% |
| Bubble Black | 95% |
| Square | 95% |

## Troubleshooting

**Build fails?**
- Verify Java 21 is installed: `java -version`
- Set JAVA_HOME: `export JAVA_HOME=/usr/lib/jvm/java-21-...`
- Clean and rebuild: `./gradlew clean assembleDebug`

**Keyboard doesn't appear?**
- Ensure Ghost is enabled in Settings → Languages & input → Virtual keyboard
- Make sure Ghost is set as the default input method

**Styled text not showing?**
- Some apps may not support Unicode characters
- Text will fall back to normal characters automatically
- Try in WhatsApp, Signal, Notes, or Twitter

## ADB Debugging

```bash
adb install -r app/build/outputs/apk/release/app-release.apk
adb logcat | grep pboard
```
