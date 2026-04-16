<p align="center">
  <img src="docs/app_icon.jpg" alt="Ghost Keyboard" width="150">
</p>

<h1 align="center">Ghost Keyboard</h1>

<p align="center">
  A minimalist Android keyboard that transforms your text into beautiful Unicode font styles in real-time.
</p>

<p align="center">
  <img alt="Android 11+" src="https://img.shields.io/badge/Android-11%2B-3DDC84?logo=android&logoColor=white">
  <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-7F52FF?logo=kotlin&logoColor=white">
  <img alt="License" src="https://img.shields.io/badge/License-PolyForm_NC_1.0-blue">
</p>

---

## Table of Contents

- [Features](#features)
- [Usage](#usage)
- [Installation](#installation)
- [Building from Source](#building-from-source)
- [Future Enhancements](#future-enhancements)
- [Contributing](#contributing)
- [References](#references)
- [Support](#support)
- [License](#license)

## Features

**10 Unicode Font Styles** — type normally and see styled characters instantly:

| Style | Example |
|-------|---------|
| Double-struck | `ℍ𝕖𝕝𝕝𝕠` |
| Cursive | `𝒞𝓊𝓇𝓈𝒾𝓋ℯ` |
| Cursive Bold | `𝓗𝓮𝓵𝓵𝓸` |
| Fraktur | `ℌ𝔢𝔩𝔩𝔬` |
| Fraktur Bold | `𝕳𝖊𝖑𝖑𝖔` |
| Fullwidth | `Ｈｅｌｌｏ` |
| Small Caps | `ʜᴇʟʟᴏ` |
| Bubble | `ⓗⓔⓛⓛⓞ` |
| Bubble Black | `🅗🅔🅛🅛🅞` |
| Square | `🄷🄴🄻🄻🄾` |

Plus a **Normal** mode that passes text through unchanged.

**Fast & Lightweight** — O(1) lookup performance, ~50KB memory footprint, no external dependencies.

**Persistent Settings** — your selected style is saved automatically and restored when you reopen the keyboard.

## Usage

1. **Type normally** — text appears in your selected style
2. **Navigate styles** — use the arrow buttons to cycle through styles
3. **Capitalize** — press Shift then type a letter for uppercase
4. **Numbers & symbols** — tap `123` to switch, `ABC` to switch back

## Installation

Download the APK from the [`release`](release/) folder and sideload it on your Android phone.

### First-Time Setup

1. Open the **Ghost** app
2. Tap **"Enable Keyboard"** and enable it in Settings
3. Tap **"Select Keyboard"** and choose Ghost as your default
4. Open any text field and start typing!

## Building from Source

See [docs/building.md](docs/building.md) for build instructions, project structure, architecture details, and troubleshooting.

## Future Enhancements

- Long-press for alternate characters (accents, common symbols)
- Additional font styles (bold, italic, monospace, etc.)
- Haptic/sound feedback
- Emoji support

## Contributing

Contributions are welcome! Feel free to:
- Report bugs
- Suggest features

## References

- [Android IME Documentation](https://developer.android.com/develop/ui/views/inputmethod/creating-an-input-method)
- [Unicode Mathematical Symbols](https://en.wikipedia.org/wiki/Mathematical_Alphanumeric_Symbols)
- [YayText Font Styles](https://yaytext.com/styles/)

---

**Made with love for Unicode enthusiasts**

## Support

If you enjoy Ghost Keyboard and want to support its development, consider buying me a drink:

[![Ko-fi](https://img.shields.io/badge/Ko--fi-F16061?style=for-the-badge&logo=ko-fi&logoColor=white)](https://ko-fi.com/stilkin)

Your support helps me continue developing and improving Ghost Keyboard!

## License

[PolyForm Noncommercial 1.0.0](LICENSE) — free to use for personal and non-commercial purposes.
