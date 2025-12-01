package be.pocito.pboard

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.KeyboardView
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.TextView
import be.pocito.pboard.R
import be.pocito.pboard.style.FontStyle
import be.pocito.pboard.style.FontStyleTransformer

/**
 * PBoard Input Method Service
 * 
 * Main service that manages the keyboard lifecycle and input handling.
 * Extends InputMethodService to integrate with Android's input method framework.
 */
class PBoardIME : InputMethodService(), KeyboardView.OnKeyboardActionListener {
    
    private var keyboardView: KeyboardView? = null
    private var keyboard: android.inputmethodservice.Keyboard? = null
    private var inputConnection: InputConnection? = null
    
    // UI components
    private var styleIndicator: TextView? = null
    private var styleButton: Button? = null
    
    // Font style management
    private var currentFontStyle: FontStyle = FontStyle.NORMAL
    
    /**
     * Called when the input view is being created.
     * This is where we inflate and return our keyboard view.
     */
    override fun onCreateInputView(): View {
        // Inflate keyboard view from layout
        val keyboardView = layoutInflater.inflate(R.layout.keyboard_view, null) as View
        
        // Initialize keyboard with QWERTY layout
        keyboard = android.inputmethodservice.Keyboard(this, R.xml.qwerty)
        
        // Set up keyboard view
        this.keyboardView = keyboardView.findViewById<KeyboardView>(R.id.keyboard_view)
        this.keyboardView?.keyboard = keyboard
        this.keyboardView?.setOnKeyboardActionListener(this)
        
        // Set up UI components
        styleIndicator = keyboardView.findViewById(R.id.style_indicator)
        styleButton = keyboardView.findViewById(R.id.style_button)
        
        // Set up style button click listener
        styleButton?.setOnClickListener {
            showStyleSelector()
        }
        
        // Update style indicator with current style
        updateStyleIndicator()
        
        return keyboardView
    }
    
    /**
     * Called when the input method is starting.
     */
    override fun onStartInput(attribute: EditorInfo?, restarting: Boolean) {
        super.onStartInput(attribute, restarting)
        // Get input connection for sending text
        inputConnection = currentInputConnection
    }
    
    /**
     * Called when the input method is finishing.
     */
    override fun onFinishInput() {
        super.onFinishInput()
        // Clean up input session
        inputConnection = null
    }
    
    /**
     * Handle key press events from the keyboard.
     */
    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        inputConnection?.let { ic ->
            when (primaryCode) {
                // Special key codes
                Keyboard.KEYCODE_DELETE -> {
                    ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
                    ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL))
                }
                Keyboard.KEYCODE_DONE -> {
                    ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
                    ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER))
                }
                Keyboard.KEYCODE_SHIFT -> {
                    // TODO: Handle shift key
                }
                -100 -> { // Custom style button code
                    showStyleSelector()
                }
                else -> {
                    // Regular character key
                    val char = primaryCode.toChar()
                    // Transform character based on current font style
                    val transformedChar = FontStyleTransformer.transformCharacter(char, currentFontStyle)
                    ic.commitText(transformedChar.toString(), 1)
                }
            }
        }
    }
    
    /**
     * Handle text input events.
     */
    override fun onText(text: CharSequence?) {
        inputConnection?.let { ic ->
            // Transform text based on current font style
            val transformedText = FontStyleTransformer.transformText(text.toString(), currentFontStyle)
            ic.commitText(transformedText, 1)
        }
    }
    
    /**
     * Handle key press feedback.
     */
    override fun onPress(primaryCode: Int) {
        // Optional: Provide haptic or audio feedback
        // TODO: Add haptic feedback if enabled
    }
    
    /**
     * Handle key release feedback.
     */
    override fun onRelease(primaryCode: Int) {
        // Optional: Handle key release
    }
    
    /**
     * Handle swipe gestures (not implemented for MVP).
     */
    override fun swipeLeft() {}
    override fun swipeRight() {}
    override fun swipeDown() {}
    override fun swipeUp() {}
    
    /**
     * Update the style indicator text with current font style.
     */
    private fun updateStyleIndicator() {
        styleIndicator?.text = "Style: ${currentFontStyle.displayName}"
    }
    
    /**
     * Set the current font style and update UI.
     */
    fun setFontStyle(style: FontStyle) {
        currentFontStyle = style
        updateStyleIndicator()
    }
    
    /**
     * Get the current font style.
     */
    fun getCurrentFontStyle(): FontStyle {
        return currentFontStyle
    }
    
    /**
     * Show style selector dialog.
     * TODO: Implement StyleSelector UI
     */
    private fun showStyleSelector() {
        // Placeholder: cycle through styles for now
        // Will be implemented with proper StyleSelector UI
        val allStyles = FontStyleTransformer.getAllStyles()
        val currentIndex = allStyles.indexOf(currentFontStyle)
        val nextIndex = (currentIndex + 1) % allStyles.size
        setFontStyle(allStyles[nextIndex])
        
        android.widget.Toast.makeText(
            this,
            "Style: ${currentFontStyle.displayName}",
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }
}