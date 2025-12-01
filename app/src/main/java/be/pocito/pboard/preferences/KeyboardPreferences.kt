package be.pocito.pboard.preferences

import android.content.Context
import android.content.SharedPreferences
import be.pocito.pboard.style.FontStyle

/**
 * Keyboard Preferences Manager
 * 
 * Handles persistence of user preferences using SharedPreferences.
 * Stores:
 * - Current selected font style
 * - "Show styled characters on keys" toggle
 */
class KeyboardPreferences(context: Context) {
    
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )
    
    companion object {
        private const val PREFERENCES_NAME = "pboard_keyboard_prefs"
        private const val KEY_CURRENT_STYLE = "current_font_style"
        private const val KEY_SHOW_STYLED_CHARS = "show_styled_characters"
        
        // Default values
        private const val DEFAULT_STYLE = "NORMAL"
        private const val DEFAULT_SHOW_STYLED_CHARS = false
    }
    
    /**
     * Get the currently saved font style.
     * 
     * @return The saved FontStyle, or NORMAL if not set
     */
    fun getCurrentStyle(): FontStyle {
        val styleName = sharedPreferences.getString(KEY_CURRENT_STYLE, DEFAULT_STYLE) ?: DEFAULT_STYLE
        return try {
            FontStyle.valueOf(styleName)
        } catch (e: IllegalArgumentException) {
            FontStyle.NORMAL
        }
    }
    
    /**
     * Save the current font style.
     * 
     * @param style The FontStyle to save
     */
    fun setCurrentStyle(style: FontStyle) {
        sharedPreferences.edit().apply {
            putString(KEY_CURRENT_STYLE, style.name)
            apply()
        }
    }
    
    /**
     * Check if styled characters should be shown on keys.
     * 
     * @return True if styled characters should be displayed, false otherwise
     */
    fun isShowStyledCharactersEnabled(): Boolean {
        return sharedPreferences.getBoolean(KEY_SHOW_STYLED_CHARS, DEFAULT_SHOW_STYLED_CHARS)
    }
    
    /**
     * Set whether styled characters should be shown on keys.
     * 
     * @param enabled True to show styled characters, false otherwise
     */
    fun setShowStyledCharactersEnabled(enabled: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(KEY_SHOW_STYLED_CHARS, enabled)
            apply()
        }
    }
    
    /**
     * Reset all preferences to default values.
     */
    fun resetToDefaults() {
        sharedPreferences.edit().apply {
            clear()
            apply()
        }
    }
}
