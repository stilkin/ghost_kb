package be.pocito.pboard.preferences

import android.content.Context
import android.content.SharedPreferences
import be.pocito.pboard.style.FontStyle

class KeyboardPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )

    companion object {
        private const val PREFERENCES_NAME = "pboard_keyboard_prefs"
        private const val KEY_CURRENT_STYLE = "current_font_style"
        private const val DEFAULT_STYLE = "NORMAL"
    }

    fun getCurrentStyle(): FontStyle {
        val styleName = sharedPreferences.getString(KEY_CURRENT_STYLE, DEFAULT_STYLE) ?: DEFAULT_STYLE
        return try {
            FontStyle.valueOf(styleName)
        } catch (e: IllegalArgumentException) {
            FontStyle.NORMAL
        }
    }

    fun setCurrentStyle(style: FontStyle) {
        sharedPreferences.edit().putString(KEY_CURRENT_STYLE, style.name).apply()
    }
}
