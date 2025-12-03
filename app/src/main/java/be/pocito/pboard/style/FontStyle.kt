package be.pocito.pboard.style

/**
 * Enum representing all supported Unicode font styles.
 * 
 * Each style has a unique identifier and display name.
 */
enum class FontStyle(val displayName: String) {
    NORMAL("Normal"),
    DOUBLE_STRUCK("Double-struck"),
    CURSIVE("Cursive"),
    CURSIVE_BOLD("Cursive Bold"),
    FRAKTUR("Fraktur"),
    FRAKTUR_BOLD("Fraktur Bold"),
    FULLWIDTH("Fullwidth"),
    SMALL_CAPS("Small Caps"),
    BUBBLE("Bubble"),
    BUBBLE_BLACK("Bubble Black"),
    SQUARE("Square");
    
    companion object {
        /**
         * Get all styles as a list for UI display.
         */
        fun getAllStyles(): List<FontStyle> = values().toList()
        
        /**
         * Get style by display name.
         */
        fun getByDisplayName(name: String): FontStyle? {
            return values().find { it.displayName == name }
        }
    }
}
