# PBoard - Custom Android Keyboard Development Plan

## Overview

Create a minimalist, MVP-style custom keyboard (Input Method Editor - IME) for Android using the standard `InputMethodService` framework. The keyboard will feature a single QWERTY layout with clean, simple design.

**Target SDK**: Android 15 (API 36)  
**Min SDK**: Android 10 (API 29)  
**Language**: Kotlin  
**Design Philosophy**: Clean, minimalist, easy to read and maintain

---

## Architecture Overview

The custom keyboard implementation uses Android's standard IME framework:

- **InputMethodService**: Core service that manages the keyboard lifecycle and input handling
- **KeyboardView**: Deprecated but simple view component that renders the keyboard UI
- **Keyboard**: XML-based keyboard layout definition
- **MainActivity**: Settings/launcher activity for users to enable the keyboard

### Why These Components?

- **KeyboardView & Keyboard**: Deprecated since API 29, but still functional and the simplest approach for MVP. They handle all the complexity of key rendering and touch detection.
- **InputMethodService**: The standard Android framework for IME implementation. Required to integrate with the system input method manager.

---

## Implementation Steps

### Step 1: Create the IME Service Class

**File**: `app/src/main/java/be/pocito/pboard/PBoardIME.kt`

**Responsibilities**:
- Extend `InputMethodService`
- Inflate and manage the keyboard view
- Handle key press events from `KeyboardView.OnKeyboardActionListener`
- Send input to the focused text field via `InputConnection`
- Manage keyboard state (shift, caps lock, etc.)

**Key Methods**:
- `onCreateInputView()`: Create and return the keyboard view
- `onKey(primaryCode: Int, keyCodes: IntArray?)`: Handle key presses
- `onText(text: CharSequence?)`: Handle text input
- `onPress(primaryCode: Int)`: Handle key press feedback

---

### Step 2: Create Keyboard Layout XML

**File**: `res/xml/qwerty.xml`

**Structure**:
```xml
<Keyboard>
  <Row>
    <Key android:keyLabel="Q" android:keyCode="113" ... />
    <Key android:keyLabel="W" android:keyCode="119" ... />
    <!-- ... more keys ... -->
  </Row>
  <!-- ... more rows ... -->
</Keyboard>
```

**Layout Details**:
- **5 rows**: QWERTY, ASDFGH, ZXCVBN, Space/Backspace, Shift/Enter
- **Key sizing**: `android:keyWidth="%10p"` (10% of parent width)
- **Key height**: `android:keyHeight="50px"`
- **Gaps**: `android:horizontalGap="2px"`, `android:verticalGap="2px"`
- **Special keys**: Shift (code -1), Delete (code -5), Done (code -4)

---

### Step 3: Create Keyboard View Layout

**File**: `res/layout/keyboard_view.xml`

**Content**:
```xml
<KeyboardView
  android:id="@+id/keyboard_view"
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  android:keyTextColor="@color/key_text"
  android:keyTextSize="18sp"
  android:keyBackground="@drawable/key_background"
  android:keyPreviewLayout="@layout/key_preview"
  android:keyPreviewHeight="80dp"
  android:keyPreviewOffset="10dp"
/>
```

---

### Step 4: Register IME in AndroidManifest.xml

**Changes to**: `app/src/main/AndroidManifest.xml`

**Add**:
```xml
<service
  android:name=".PBoardIME"
  android:label="@string/ime_name"
  android:permission="android.permission.BIND_INPUT_METHOD">
  <intent-filter>
    <action android:name="android.view.InputMethod" />
  </intent-filter>
  <meta-data
    android:name="android.inputmethod.InputMethodSubtype"
    android:resource="@xml/method" />
</service>
```

**Permissions**:
- `android.permission.BIND_INPUT_METHOD` (required for IME)

---

### Step 5: Create IME Metadata File

**File**: `res/xml/method.xml`

**Content**:
```xml
<?xml version="1.0" encoding="utf-8"?>
<input-method xmlns:android="http://schemas.android.com/apk/res/android">
  <subtype
    android:name="@string/subtype_en_us"
    android:label="@string/subtype_en_us"
    android:imeSubtypeLocale="en_US"
    android:imeSubtypeMode="keyboard"
    android:imeSubtypeExtraValue="TreatAsDisplayLabel" />
</input-method>
```

---

### Step 6: Create Launcher Activity

**File**: `app/src/main/java/be/pocito/pboard/MainActivity.kt`

**Responsibilities**:
- Display app information
- Provide button to open IME settings
- Show instructions for enabling the keyboard

**Layout**: `res/layout/activity_main.xml`

---

### Step 7: Create Supporting Resources

#### Key Background Drawable
**File**: `res/drawable/key_background.xml`

State list drawable for key press feedback (normal, pressed states).

#### Key Preview Layout
**File**: `res/layout/key_preview.xml`

Simple layout showing magnified key when pressed.

#### String Resources
**File**: `res/values/strings.xml`

Add:
- `ime_name`: "PBoard Keyboard"
- `subtype_en_us`: "English (US)"
- `app_name`: "PBoard"

#### Color Resources
**File**: `res/values/colors.xml`

Add:
- `key_text`: Text color for keys
- `key_background`: Background color for keys
- `key_pressed`: Pressed state color

---

## Testing Checklist

- [ ] Build project successfully
- [ ] Install APK on emulator/device
- [ ] Open Settings → Languages & input → Virtual keyboard
- [ ] Enable "PBoard Keyboard"
- [ ] Set as default keyboard
- [ ] Open text field (e.g., Notes app)
- [ ] Verify keyboard appears
- [ ] Test typing letters (A-Z)
- [ ] Test Shift key (uppercase)
- [ ] Test Backspace (delete)
- [ ] Test Space key
- [ ] Test Enter/Done key
- [ ] Verify text appears in text field

---

## File Structure

```
PBoard/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/be/pocito/pboard/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   └── PBoardIME.kt
│   │   │   ├── res/
│   │   │   │   ├── drawable/
│   │   │   │   │   └── key_background.xml
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   ├── keyboard_view.xml
│   │   │   │   │   └── key_preview.xml
│   │   │   │   ├── values/
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   └── strings.xml
│   │   │   │   └── xml/
│   │   │   │       ├── method.xml
│   │   │   │       └── qwerty.xml
│   │   │   └── AndroidManifest.xml
├── docs/
│   └── development_plan.md
└── ...
```

---

## Design Decisions

### 1. Deprecated APIs
**Decision**: Use `KeyboardView` and `Keyboard` (deprecated since API 29)  
**Rationale**: 
- Simplest approach for MVP
- Still fully functional on all supported Android versions
- Handles complex rendering and touch detection automatically
- Modern alternatives (custom views) require significantly more code

### 2. Single QWERTY Layout
**Decision**: Implement only English QWERTY layout  
**Rationale**:
- Simplest MVP scope
- Can be extended later with additional layouts
- Covers most common use case

### 3. Minimal UI
**Decision**: Use basic Material Design styling  
**Rationale**:
- Clean and professional appearance
- Material library already included in project
- No animations or advanced features initially
- Easy to enhance later

### 4. No Advanced Features (MVP)
**Excluded from initial implementation**:
- Autocorrect/suggestions
- Swipe typing
- Multiple languages
- Themes/customization
- Sound/haptic feedback

---

## Future Enhancements

1. **Additional Layouts**: Numbers, symbols, other languages
2. **Autocorrect**: Dictionary-based word suggestions
3. **Themes**: Dark mode, custom colors
4. **Haptic Feedback**: Vibration on key press
5. **Sound Feedback**: Click sound on key press
6. **Swipe Typing**: Gesture-based input
7. **Settings Screen**: User preferences for keyboard behavior

---

## References

- [Android InputMethodService Documentation](https://developer.android.com/reference/android/inputmethodservice/InputMethodService)
- [Android Keyboard Documentation](https://developer.android.com/reference/android/inputmethodservice/Keyboard)
- [Android KeyboardView Documentation](https://developer.android.com/reference/android/inputmethodservice/KeyboardView)
- [Creating an Input Method Guide](https://developer.android.com/develop/ui/views/inputmethod/creating-an-input-method)

---

## Notes

- The keyboard will be available in the system input method selector after installation
- Users must explicitly enable it in Settings
- The keyboard will only appear when a text field is focused
- All key codes follow Android standard key event codes
