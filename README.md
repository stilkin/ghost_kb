# PBoard - Unicode Font Keyboard

A minimalist Android keyboard that transforms your text into beautiful Unicode font styles in real-time.

## Features

✨ **11 Unicode Font Styles**
- Double-struck (𝕳𝖊𝖑𝖑𝖔)
- Cursive (𝓗𝓮𝓵𝓵𝓸)
- Cursive Bold (𝓗𝓮𝓵𝓵𝓸)
- Fraktur (ℌ𝔢𝔩𝔩𝔬)
- Fraktur Bold (𝕳𝖊𝖑𝖑𝖔)
- Fullwidth (Ｈｅｌｌｏ)
- Small Caps (ʜᴇʟʟᴏ)
- Bubble (ⓗⓔⓛⓛⓞ)
- Bubble Black (🅗🅔🅛🅛🅞)
- Square (🄷🄴🄻🄻🄾)
- And more!

⚡ **Fast & Lightweight**
- Real-time character transformation
- O(1) lookup performance
- ~50KB total memory footprint
- No external dependencies

💾 **Persistent Settings**
- Your selected style is saved automatically
- Restored when you reopen the keyboard

🎯 **Easy to Use**
- Simple QWERTY layout
- One-tap style switching
- Graceful fallback for incomplete character sets

## Usage

1. **Type normally** - Text appears in your selected style
2. **Navigate styles** - Use ◀ and ▶ buttons to cycle through styles
3. **Capitalize text** - Press ⇧ (Shift) then type a letter for uppercase
4. **Your text transforms instantly** - See styled characters as you type


## Installation

If you do not want to build from source and just want to sideload the APK, download it from the `release` folder to your Android phone and install it.

### First-Time Setup

1. Open the **PBoard** app
2. Click **"Enable Keyboard"** → Enable in Settings
3. Click **"Select Keyboard"** → Choose PBoard as default
4. Open any text field and start typing!

## Build from Source

1. **Install Android SDK**

- download from [the Android Studio website](https://developer.android.com/studio)


2. **Install Oracle Java JDK**

- download from [the Oracle website](https://www.oracle.com/java/technologies/downloads/#java21)
- make sure your `JAVA_HOME` variable is set to the folder where you installed it

3. **Build the APK**
   ```bash
   cd /home/srt/AndroidStudioProjects/PBoard
   ./gradlew assembleDebug
   ```

4. **Install on device/emulator**
   ```bash
   ./gradlew installDebug
   ```

## Project Structure

```
PBoard/
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
    └── release_guide.md        # Instructions on how to build for release
```

## Technical Details

### Supported Android Versions
- **Min SDK**: Android 11 (API 30)
- **Target SDK**: Android 15 (API 36)
- **Language**: Kotlin

### Architecture
- **InputMethodService** - Core IME framework integration
- **KeyboardView** - Keyboard UI rendering
- **SharedPreferences** - User preference persistence
- **Character Mapping** - O(1) Unicode transformation

### Font Coverage
- **Double-struck**: 95% (includes numbers)
- **Cursive**: 95% (includes numbers)
- **Cursive Bold**: 95% (includes numbers)
- **Fraktur**: 90%
- **Fraktur Bold**: 90%
- **Fullwidth**: 100%
- **Small Caps**: 80%
- **Bubble**: 95%
- **Bubble Black**: 95%
- **Square**: 95%


## Future Enhancements

- Long-press for numbers and symbols
- Swipe-to-switch styles
- Additional font styles (bold, italic, monospace, etc.)
- Haptic/sound feedback
- Multiple keyboard layouts

## Troubleshooting

**Keyboard doesn't appear?**
- Ensure PBoard is enabled in Settings → Languages & input → Virtual keyboard
- Make sure PBoard is set as the default input method

**Build fails?**
- Verify Oracle Java 10+ is installed: `java -version`
- Set JAVA_HOME: `export JAVA_HOME=/usr/lib/jvm/java-...`
- Clean and rebuild: `./gradlew clean assembleDebug`

**Styled text not showing?**
- Some apps may not support Unicode characters
- Text will fall back to normal characters automatically
- Try in WhatsApp, Signal, Notes, or Twitter

## License

This project is open source and available under the MIT License.

## Contributing

Contributions are welcome! 
Feel free to:
- Report bugs
- Suggest features
- Improve documentation
- Fix bugs?

## References

- [Android IME Documentation](https://developer.android.com/develop/ui/views/touch-and-input/creating-input-method?hl=en)
- [Unicode Mathematical Symbols](https://en.wikipedia.org/wiki/Mathematical_Alphanumeric_Symbols)
- [YayText Font Styles](https://yaytext.com/styles/)

---

**Made with ❤️ for Unicode enthusiasts**

## Support

If you enjoy PBoard and want to support its development, consider buying me a drink:

[![Ko-fi](https://img.shields.io/badge/Ko--fi-F16061?style=for-the-badge&logo=ko-fi&logoColor=white)](https://ko-fi.com/stilkin)

Your support helps me continue developing and improving Low-Ki! ☕
