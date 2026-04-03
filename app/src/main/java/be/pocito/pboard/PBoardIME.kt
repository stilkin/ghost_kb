package be.pocito.pboard

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.TextView
import be.pocito.pboard.preferences.KeyboardPreferences
import be.pocito.pboard.style.FontStyle
import be.pocito.pboard.style.FontStyleTransformer

class PBoardIME : InputMethodService(), KeyboardView.OnKeyboardActionListener {
    private enum class ShiftState { OFF, ONE_SHOT, CAPS_LOCK }

    companion object {
        private const val KEYCODE_STYLE = -100
        private const val KEYCODE_SYMBOLS = -101
    }

    private var keyboardView: KeyboardView? = null
    private var keyboard: Keyboard? = null
    private var qwertyKeyboard: Keyboard? = null
    private var symbolsKeyboard: Keyboard? = null
    private var isSymbolsMode = false

    private var styleIndicator: TextView? = null
    private var stylePrevButton: Button? = null
    private var styleNextButton: Button? = null

    private var currentFontStyle: FontStyle = FontStyle.NORMAL
    private var shiftState: ShiftState = ShiftState.OFF

    private val preferences: KeyboardPreferences by lazy { KeyboardPreferences(this) }

    override fun onCreateInputView(): View {
        val view = layoutInflater.inflate(R.layout.keyboard_view, null) as View

        qwertyKeyboard = Keyboard(this, R.xml.qwerty)
        symbolsKeyboard = Keyboard(this, R.xml.symbols)
        keyboard = qwertyKeyboard
        isSymbolsMode = false
        keyboardView =
            view.findViewById<KeyboardView>(R.id.keyboard_view).also {
                it.keyboard = keyboard
                it.setOnKeyboardActionListener(this)
            }

        styleIndicator = view.findViewById(R.id.style_indicator)
        stylePrevButton = view.findViewById(R.id.style_prev_button)
        styleNextButton = view.findViewById(R.id.style_next_button)

        stylePrevButton?.setOnClickListener { cycleStyle(forward = false) }
        styleNextButton?.setOnClickListener { cycleStyle(forward = true) }

        updateStyleIndicator()
        return view
    }

    override fun onStartInput(
        attribute: EditorInfo?,
        restarting: Boolean,
    ) {
        super.onStartInput(attribute, restarting)
        if (!restarting && shiftState == ShiftState.ONE_SHOT) {
            shiftState = ShiftState.OFF
            updateKeyboardShiftState()
        }
        currentFontStyle = preferences.getCurrentStyle()
        updateStyleIndicator()
    }

    override fun onKey(
        primaryCode: Int,
        keyCodes: IntArray?,
    ) {
        val ic = currentInputConnection ?: return
        when (primaryCode) {
            Keyboard.KEYCODE_DELETE -> ic.sendKey(KeyEvent.KEYCODE_DEL)
            Keyboard.KEYCODE_DONE -> {
                ic.sendKey(KeyEvent.KEYCODE_ENTER)
                shiftState = ShiftState.OFF
                updateKeyboardShiftState()
            }
            Keyboard.KEYCODE_SHIFT -> {
                shiftState =
                    when (shiftState) {
                        ShiftState.OFF -> ShiftState.ONE_SHOT
                        ShiftState.ONE_SHOT -> ShiftState.CAPS_LOCK
                        ShiftState.CAPS_LOCK -> ShiftState.OFF
                    }
                updateKeyboardShiftState()
            }
            KEYCODE_STYLE -> cycleStyle(forward = true)
            KEYCODE_SYMBOLS -> toggleSymbolsMode()
            else -> {
                var char = primaryCode.toChar()
                if (shiftState != ShiftState.OFF && char.isLetter()) {
                    char = char.uppercaseChar()
                    if (shiftState == ShiftState.ONE_SHOT) {
                        shiftState = ShiftState.OFF
                        updateKeyboardShiftState()
                    }
                }
                ic.commitText(FontStyleTransformer.transformCharacter(char, currentFontStyle), 1)
            }
        }
    }

    override fun onText(text: CharSequence?) {
        if (text.isNullOrEmpty()) return
        currentInputConnection?.commitText(
            FontStyleTransformer.transformText(text.toString(), currentFontStyle),
            1,
        )
    }

    override fun onPress(primaryCode: Int) {}

    override fun onRelease(primaryCode: Int) {}

    override fun swipeLeft() {}

    override fun swipeRight() {}

    override fun swipeDown() {}

    override fun swipeUp() {}

    fun setFontStyle(style: FontStyle) {
        currentFontStyle = style
        updateStyleIndicator()
        preferences.setCurrentStyle(style)
    }

    fun getCurrentFontStyle(): FontStyle = currentFontStyle

    private fun cycleStyle(forward: Boolean) {
        val styles = FontStyle.entries
        val next = (styles.indexOf(currentFontStyle) + if (forward) 1 else styles.size - 1) % styles.size
        setFontStyle(styles[next])
    }

    private fun toggleSymbolsMode() {
        isSymbolsMode = !isSymbolsMode
        keyboard = if (isSymbolsMode) symbolsKeyboard else qwertyKeyboard
        keyboardView?.keyboard = keyboard
    }

    private fun updateStyleIndicator() {
        val name = currentFontStyle.displayName
        if (currentFontStyle == FontStyle.NORMAL) {
            styleIndicator?.text = name
        } else {
            val preview = FontStyleTransformer.transformText("ABC", currentFontStyle)
            styleIndicator?.text = "$name · $preview"
        }
    }

    private fun updateKeyboardShiftState() {
        // Update the shift key label and the keyboard's shifted state for visual feedback
        val shiftKey = keyboard?.keys?.find { it.codes[0] == Keyboard.KEYCODE_SHIFT }
        shiftKey?.label =
            when (shiftState) {
                ShiftState.OFF, ShiftState.ONE_SHOT -> "⇧"
                ShiftState.CAPS_LOCK -> "⇪"
            }
        keyboard?.isShifted = shiftState != ShiftState.OFF
        keyboardView?.invalidateAllKeys()
    }

    private fun InputConnection.sendKey(keyCode: Int) {
        sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, keyCode))
        sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, keyCode))
    }
}
