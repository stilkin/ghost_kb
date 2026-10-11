# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**Ghost Keyboard** is a minimalist Android IME (Input Method Engine) that transforms typed text into 11 Unicode font styles in real-time. Single-module Kotlin app targeting Android 11+ (API 30–36), no third-party runtime dependencies.

Application ID: `be.pocito.ghostkb` (code namespace and package dirs remain `be.pocito.pboard`)

## Build Commands

Requires JDK 21 (`JAVA_HOME` must point to it).

```bash
# Debug build and install
./gradlew assembleDebug
./gradlew installDebug

# Lint and formatting — detekt.yml sets maxIssues: 0, so any detekt finding fails the build.
# Empty function bodies are allowed (EmptyFunctionBlock is disabled for interface callbacks).
./gradlew ktlintCheck
./gradlew ktlintFormat      # auto-fix formatting (e.g. missing trailing commas)
./gradlew detekt

# Run unit tests
./gradlew test
./gradlew test --tests "be.pocito.pboard.ExampleUnitTest"   # single test class

# Run instrumented tests (requires connected device/emulator)
./gradlew connectedAndroidTest

# Release build — keystore path is hardcoded to ~/ghostkb-release.keystore, alias to "ghostkb";
# only the two passwords come from env vars:
export GHOSTKB_KEYSTORE_PASSWORD=<password>
export GHOSTKB_KEY_PASSWORD=<password>
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release.apk

# ADB install/debug
adb install -r app/build/outputs/apk/release/app-release.apk
adb logcat | grep ghostkb
```

## Architecture

The app is an `InputMethodService` with three supporting layers:

**Core IME** — `PBoardIME.kt` extends `InputMethodService` and implements `OnKeyboardActionListener`. It owns the keyboard lifecycle, handles key events via `onKey()`, and delegates character transformation. Shift cycles through a three-state machine (OFF → ONE_SHOT → CAPS_LOCK) with visual feedback (⇧/⇪ label swap). The persisted style is re-read in `onStartInput()` at every input focus.

**Style System** — three files in `style/`:
- `FontStyle.kt` — enum of 11 styles (NORMAL, DOUBLE_STRUCK, CURSIVE, etc.), each with a `displayName`
- `FontStyles.kt` — a single `object` holding one flat `Map<Char, String>` per style (lowercase, uppercase, and digits all in the same map); `getMapping(style)` dispatches via a `when` block
- `FontStyleTransformer.kt` — stateless singleton that calls `FontStyles.getMapping()` and falls back to the original character when no mapping exists

**Persistence** — `KeyboardPreferences.kt` wraps `SharedPreferences` (name `pboard_keyboard_prefs`), storing the active `FontStyle` by enum name.

**UI** — style selection lives in a header bar in the keyboard itself (`layout/keyboard_view.xml`): ◀/▶ buttons cycle styles via `cycleStyle()` → `setFontStyle()`, and a `style_indicator` TextView shows a `Name · 𝔸𝔹ℂ` preview. Two keyboard layouts are defined declaratively in `res/xml/qwerty.xml` (letters) and `res/xml/symbols.xml` (numbers/symbols), toggled via the `123`/`ABC` key and rendered by the framework `KeyboardView`. Special keys use negative codes: framework constants (shift `-1`, delete `-5`, enter `-4`) plus `KEYCODE_SYMBOLS = -101` from `PBoardIME`'s companion object. (Note: the project-structure section in `docs/building.md` is stale — it references a `ui/StyleSelector.kt` that no longer exists.)

**Data flow:**
```
onKey(keyCode) → FontStyleTransformer.transformCharacter(char, style) → FontStyles.getMapping(style)[char] → currentInputConnection.commitText()
```

## Adding a New Font Style

1. Add an entry to the `FontStyle` enum in `FontStyle.kt`
2. Add a `Map<Char, String>` in `FontStyles.kt` **and** a matching branch in `getMapping()`'s `when` block — the dispatch is not automatic
3. The style indicator, cycling, persistence, and transformer pick it up automatically
4. Bump `versionCode` and `versionName` in `app/build.gradle.kts` before releasing

## Release Checklist

- Increment `versionCode` (int) and `versionName` (string) in `app/build.gradle.kts`
- Keystore lives at `~/ghostkb-release.keystore` — never commit it or passwords to git
- Full checklist in `docs/release_guide.md`
