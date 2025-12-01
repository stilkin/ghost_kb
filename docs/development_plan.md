# PBoard - Custom Android Keyboard Development Plan

## Overview

Create a minimalist, MVP-style custom keyboard (Input Method Editor - IME) for Android that transforms text into Unicode font styles. The keyboard features a single QWERTY layout with support for 10+ Unicode font styles (double-struck, cursive, fraktur, upside-down, fullwidth, small caps, bubble text, and more). Users can easily switch between styles using a dedicated style selector button.

**Target SDK**: Android 15 (API 36)  
**Min SDK**: Android 10 (API 29)  
**Language**: Kotlin  
**Design Philosophy**: Clean, minimalist, easy to read and maintain  
**Special Feature**: Unicode font style transformation with graceful fallback

---

## Architecture Overview

The custom keyboard implementation uses Android's standard IME framework combined with a Unicode font style transformation engine:

### Core Components

- **InputMethodService**: Core service that manages the keyboard lifecycle and input handling
- **KeyboardView**: Deprecated but simple view component that renders the keyboard UI
- **Keyboard**: XML-based keyboard layout definition
- **MainActivity**: Settings/launcher activity for users to enable the keyboard
- **FontStyleTransformer**: Engine that transforms text into Unicode font styles
- **FontStyles**: Character mapping data for each supported style
- **StyleSelector**: UI component for switching between font styles

### Font Style System

The keyboard supports 10+ Unicode font styles:

| Style | Example | Unicode Range |
|-------|---------|---|
| Normal | Hello | Standard ASCII |
| Double-struck | 𝕳𝖊𝖑𝖑𝖔 | Mathematical Alphanumeric Symbols |
| Cursive | 𝓗𝓮𝓵𝓵𝓸 | Mathematical Alphanumeric Symbols |
| Cursive Bold | 𝓗𝓮𝓵𝓵𝓸 | Mathematical Alphanumeric Symbols |
| Fraktur | ℌ𝔢𝔩𝔩𝔬 | Mathematical Alphanumeric Symbols |
| Fraktur Bold | 𝕳𝖊𝖑𝖑𝖔 | Mathematical Alphanumeric Symbols |
| Upside Down | ɥǝllo | Latin Extended-E, IPA Extensions |
| Fullwidth | Ｈｅｌｌｏ | Halfwidth and Fullwidth Forms |
| Small Caps | ʜᴇʟʟᴏ | Latin Extended-B, Phonetic Extensions |
| Bubble | ⓗⓔⓛⓛⓞ | Enclosed Alphanumerics |
| Bubble Black | 🅗🅔🅛🅛🅞 | Enclosed Alphanumeric Supplement |
| Square | 🄷🄴🄻🄻🄾 | Enclosed Alphanumerics |
| Square Black | 🅓🅔🅛🅛🅞 | Enclosed Alphanumeric Supplement |

### Why These Components?

- **KeyboardView & Keyboard**: Deprecated since API 29, but still functional and the simplest approach for MVP. They handle all the complexity of key rendering and touch detection.
- **InputMethodService**: The standard Android framework for IME implementation. Required to integrate with the system input method manager.
- **Character Mapping Arrays**: Simple, fast (O(1) lookup), and maintainable. ~13 styles × ~62 characters = minimal memory footprint.

---

## Implementation Steps

### Step 1: Create Font Style System

**Files**: 
- `app/src/main/java/be/pocito/pboard/style/FontStyle.kt`
- `app/src/main/java/be/pocito/pboard/style/FontStyles.kt`
- `app/src/main/java/be/pocito/pboard/style/FontStyleTransformer.kt`

**Responsibilities**:
- Define `FontStyle` enum with all supported styles
- Create character mapping data for each style
- Implement transformation logic with fallback

**Key Components**:
```kotlin
enum class FontStyle {
    NORMAL, DOUBLE_STRUCK, CURSIVE, CURSIVE_BOLD,
    FRAKTUR, FRAKTUR_BOLD, UPSIDE_DOWN, FULLWIDTH,
    SMALL_CAPS, BUBBLE, BUBBLE_BLACK, SQUARE, SQUARE_BLACK
}

object FontStyles {
    val DOUBLE_STRUCK = mapOf(
        'a' to '𝕒', 'b' to '𝕓', /* ... */
        'A' to '𝔸', 'B' to '𝔹', /* ... */
    )
    // ... more style mappings
}

object FontStyleTransformer {
    fun transformText(text: String, style: FontStyle): String
    fun transformCharacter(char: Char, style: FontStyle): Char
}
```

---

### Step 2: Create the IME Service Class

**File**: `app/src/main/java/be/pocito/pboard/PBoardIME.kt`

**Responsibilities**:
- Extend `InputMethodService`
- Inflate and manage the keyboard view
- Handle key press events from `KeyboardView.OnKeyboardActionListener`
- Send transformed input to the focused text field via `InputConnection`
- Manage keyboard state (shift, caps lock, current font style)
- Integrate with `FontStyleTransformer` for text transformation

**Key Methods**:
- `onCreateInputView()`: Create and return the keyboard view
- `onKey(primaryCode: Int, keyCodes: IntArray?)`: Handle key presses and transform via current style
- `onText(text: CharSequence?)`: Handle text input
- `onPress(primaryCode: Int)`: Handle key press feedback
- `setFontStyle(style: FontStyle)`: Change current style and update keyboard display
- `getCurrentFontStyle()`: Get currently active style

---

### Step 3: Create Keyboard Layout XML

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
- **Style Button**: Add dedicated "Style" button (top-right) to open style selector

**Dynamic Key Display**:
- Keys can optionally display styled characters based on current `FontStyle`
- Controlled by user setting "Show styled characters on keys"
- Keyboard regenerated when style changes (if setting enabled)

---

### Step 4: Create Keyboard View Layout

**File**: `res/layout/keyboard_view.xml`

**Content**:
```xml
<LinearLayout
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  android:orientation="vertical">
  
  <!-- Style Indicator Bar -->
  <LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:padding="8dp">
    <TextView
      android:id="@+id/style_indicator"
      android:layout_width="0dp"
      android:layout_height="wrap_content"
      android:layout_weight="1"
      android:text="Style: Normal"
      android:textSize="12sp" />
    <Button
      android:id="@+id/style_button"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:text="Change Style" />
  </LinearLayout>
  
  <!-- Keyboard View -->
  <KeyboardView
    android:id="@+id/keyboard_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:keyTextColor="@color/key_text"
    android:keyTextSize="18sp"
    android:keyBackground="@drawable/key_background"
    android:keyPreviewLayout="@layout/key_preview"
    android:keyPreviewHeight="80dp"
    android:keyPreviewOffset="10dp" />
</LinearLayout>
```

---

### Step 5: Create Style Selector UI

**File**: `app/src/main/java/be/pocito/pboard/ui/StyleSelector.kt`

**Responsibilities**:
- Display all available font styles in a grid/list
- Handle style selection
- Update keyboard when style changes
- Show current style as selected

**Layout**: `res/layout/style_selector.xml`
- Grid of style buttons (2-3 columns)
- Each button shows style name and preview
- Current style highlighted

---

### Step 6: Create Preferences Manager

**File**: `app/src/main/java/be/pocito/pboard/preferences/KeyboardPreferences.kt`

**Responsibilities**:
- Persist user preferences to `SharedPreferences`
- Store current selected style
- Store "Show styled characters on keys" toggle
- Provide getters/setters for preferences

---

### Step 7: Register IME in AndroidManifest.xml

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

### Step 8: Create IME Metadata File

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

### Step 9: Create Launcher Activity

**File**: `app/src/main/java/be/pocito/pboard/MainActivity.kt`

**Responsibilities**:
- Display app information and feature overview
- Provide button to open IME settings
- Show instructions for enabling the keyboard
- Display list of supported font styles
- Link to style selector for preview

**Layout**: `res/layout/activity_main.xml`

---

### Step 10: Create Supporting Resources

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

### Basic Keyboard Functionality
- [ ] Build project successfully
- [ ] Install APK on emulator/device
- [ ] Open Settings → Languages & input → Virtual keyboard
- [ ] Enable "PBoard Keyboard"
- [ ] Set as default keyboard
- [ ] Open text field (e.g., Notes app)
- [ ] Verify keyboard appears

### Text Input
- [ ] Test typing letters (A-Z)
- [ ] Test Shift key (uppercase)
- [ ] Test Backspace (delete)
- [ ] Test Space key
- [ ] Test Enter/Done key
- [ ] Verify text appears in text field

### Font Style Switching
- [ ] Click "Change Style" button
- [ ] Verify style selector appears
- [ ] Select "Double-struck" style
- [ ] Type text and verify Unicode characters appear
- [ ] Select "Cursive" style
- [ ] Type text and verify different Unicode characters appear
- [ ] Test all 13 styles
- [ ] Verify fallback for incomplete character sets (partial transformation)

### Style Persistence
- [ ] Select a style
- [ ] Close keyboard
- [ ] Reopen keyboard
- [ ] Verify same style is still selected

### Settings
- [ ] Toggle "Show styled characters on keys" setting
- [ ] Verify keyboard display updates
- [ ] Test with setting ON and OFF

### Cross-App Compatibility
- [ ] Test in Notes app
- [ ] Test in Messages app
- [ ] Test in Twitter/social media
- [ ] Test in email app
- [ ] Verify styled text displays correctly in each app

---

## File Structure

```
PBoard/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/be/pocito/pboard/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── PBoardIME.kt
│   │   │   │   ├── style/
│   │   │   │   │   ├── FontStyle.kt (enum)
│   │   │   │   │   ├── FontStyles.kt (character mappings)
│   │   │   │   │   └── FontStyleTransformer.kt (transformation logic)
│   │   │   │   ├── ui/
│   │   │   │   │   └── StyleSelector.kt (style selection UI)
│   │   │   │   └── preferences/
│   │   │   │       └── KeyboardPreferences.kt (SharedPreferences wrapper)
│   │   │   ├── res/
│   │   │   │   ├── drawable/
│   │   │   │   │   └── key_background.xml
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   ├── keyboard_view.xml
│   │   │   │   │   ├── key_preview.xml
│   │   │   │   │   └── style_selector.xml
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

### 2. Character Mapping Arrays for Font Styles
**Decision**: Use simple Kotlin `Map<Char, Char>` for each font style  
**Rationale**:
- O(1) lookup performance
- Minimal memory footprint (~800 mappings total)
- Easy to maintain and extend
- No external dependencies or complex algorithms
- Fast transformation with no latency

### 3. Style Switching via Dedicated Button
**Decision**: Add "Change Style" button in keyboard UI (top-right)  
**Rationale**:
- Clear and discoverable for users
- No learning curve required
- Consistent with standard keyboard UX patterns
- Can be enhanced later with swipe gestures

### 4. Graceful Fallback for Incomplete Character Sets
**Decision**: Return original character if not found in style mapping  
**Rationale**:
- Ensures text is always readable
- Partial transformations are acceptable and expected
- No errors or warnings needed
- Transparent to user

### 5. Optional Visual Feedback on Keys
**Decision**: Add user setting to show styled characters on keys  
**Rationale**:
- Provides visual feedback when enabled
- Can be disabled for better legibility
- Controlled by user preference
- Keyboard regenerated on style change (if enabled)

### 6. Single QWERTY Layout
**Decision**: Implement only English QWERTY layout  
**Rationale**:
- Simplest MVP scope
- Can be extended later with additional layouts
- Covers most common use case

### 7. Minimal UI
**Decision**: Use basic Material Design styling  
**Rationale**:
- Clean and professional appearance
- Material library already included in project
- No animations or advanced features initially
- Easy to enhance later

### 8. No Advanced Features (MVP)
**Excluded from initial implementation**:
- Autocorrect/suggestions
- Swipe typing
- Multiple languages
- Themes/customization
- Sound/haptic feedback
- Swipe-to-switch styles (can be added later)

---

## Future Enhancements

### Phase 2 Features
1. **Swipe-to-Switch Styles**: Swipe left/right on space bar to cycle through styles
2. **More Font Styles**: Bold, italic, monospace, sans-serif, etc.
3. **Style Favorites**: Quick access to frequently used styles
4. **Animated Transitions**: Smooth style switching animations

### Phase 3 Features
5. **Additional Layouts**: Numbers, symbols, other languages
6. **Autocorrect**: Dictionary-based word suggestions
7. **Themes**: Dark mode, custom colors
8. **Haptic Feedback**: Vibration on key press
9. **Sound Feedback**: Click sound on key press
10. **Settings Screen**: Advanced user preferences
11. **Custom Style Creation**: Allow users to define custom character mappings
12. **Style Sharing**: Export/import custom styles

---

## Technical Implementation Details

### Font Style Transformation Algorithm

```kotlin
fun transformText(text: String, style: FontStyle): String {
    if (style == FontStyle.NORMAL) return text
    
    val mapping = getStyleMapping(style)
    return text.map { char ->
        mapping[char] ?: char  // Fallback to original if not found
    }.joinToString("")
}
```

**Complexity**: O(n) where n = text length  
**Performance**: Negligible overhead, suitable for real-time input

### Character Mapping Coverage

| Style | Coverage | Notes |
|-------|----------|-------|
| Double-struck | ~95% | Most complete, includes numbers |
| Cursive | ~95% | Most complete, includes numbers |
| Fraktur | ~90% | Missing some special chars |
| Upside Down | ~70% | Limited Unicode support |
| Fullwidth | ~100% | Complete coverage |
| Small Caps | ~80% | Limited to Latin letters |
| Bubble | ~95% | Includes numbers and symbols |
| Square | ~95% | Includes numbers and symbols |

### Data Storage

- **Character Mappings**: Hardcoded in Kotlin objects (no I/O overhead)
- **User Preferences**: `SharedPreferences` (current style, display settings)
- **Memory Usage**: ~50KB for all character mappings

---

## References

- [Android InputMethodService Documentation](https://developer.android.com/reference/android/inputmethodservice/InputMethodService)
- [Android Keyboard Documentation](https://developer.android.com/reference/android/inputmethodservice/Keyboard)
- [Android KeyboardView Documentation](https://developer.android.com/reference/android/inputmethodservice/KeyboardView)
- [Creating an Input Method Guide](https://developer.android.com/develop/ui/views/inputmethod/creating-an-input-method)
- [Unicode Mathematical Alphanumeric Symbols](https://en.wikipedia.org/wiki/Mathematical_Alphanumeric_Symbols)
- [YayText Font Styles Reference](https://yaytext.com/styles/)

---

## Notes

- The keyboard will be available in the system input method selector after installation
- Users must explicitly enable it in Settings
- The keyboard will only appear when a text field is focused
- All key codes follow Android standard key event codes
- Unicode support varies by app/platform; some apps may not render certain characters correctly
- Fallback ensures text is always readable even if styled characters aren't supported
- Character transformations happen in real-time with no perceptible latency
