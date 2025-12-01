package be.pocito.pboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView

/**
 * Main Activity for PBoard
 * 
 * Provides:
 * - App information and instructions
 * - Button to open IME settings
 * - Instructions for enabling the keyboard
 */
class MainActivity : Activity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // TODO: Set up layout from XML
        // TODO: Initialize UI components
        // TODO: Set up click listeners
        
        // Placeholder implementation
        setContentView(TextView(this).apply {
            text = "PBoard Keyboard - Coming Soon!"
        })
    }
    
    /**
     * Open Android's input method settings
     */
    private fun openInputMethodSettings() {
        val intent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
        startActivity(intent)
    }
    
    /**
     * Show instructions for enabling the keyboard
     */
    private fun showInstructions() {
        // TODO: Display instructions dialog or scrollable text
    }
}