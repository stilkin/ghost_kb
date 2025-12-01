package be.pocito.pboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast

/**
 * Main Activity for PBoard
 * 
 * Provides:
 * - App information and instructions
 * - Button to open IME settings
 * - Instructions for enabling the keyboard
 * - Display of supported font styles
 */
class MainActivity : Activity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Set up layout from XML
        setContentView(R.layout.activity_main)
        
        // Initialize UI components
        setupUI()
    }
    
    /**
     * Set up UI components and click listeners.
     */
    private fun setupUI() {
        // Enable Keyboard button - opens IME settings
        val enableKeyboardButton = findViewById<Button>(R.id.enable_keyboard_button)
        enableKeyboardButton.setOnClickListener {
            openInputMethodSettings()
        }
        
        // Select Keyboard button - opens input method selector
        val selectKeyboardButton = findViewById<Button>(R.id.select_keyboard_button)
        selectKeyboardButton.setOnClickListener {
            openInputMethodSelector()
        }
    }
    
    /**
     * Open Android's input method settings.
     */
    private fun openInputMethodSettings() {
        try {
            val intent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Could not open settings. Please enable PBoard in Settings > Languages & input > Virtual keyboard",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    
    /**
     * Open input method selector to choose PBoard as default keyboard.
     */
    private fun openInputMethodSelector() {
        try {
            // Use the same settings action - it will show the input method picker
            val intent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Could not open input method picker. Please select PBoard in Settings > Languages & input > Keyboard",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}