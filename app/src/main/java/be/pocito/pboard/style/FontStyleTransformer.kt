package be.pocito.pboard.style

/**
 * Transforms text into different Unicode font styles.
 * 
 * Provides O(1) character transformation with graceful fallback to original characters
 * for styles that don't have complete character coverage.
 */
object FontStyleTransformer {
    
    /**
     * Transform a single character based on the specified font style.
     * 
     * If the character is not found in the style's mapping, returns the original character.
     * This ensures text is always readable even if styled characters aren't available.
     * 
     * @param char The character to transform
     * @param style The font style to apply
     * @return The transformed character, or the original if not found in the style
     */
    fun transformCharacter(char: Char, style: FontStyle): Char {
        // Return original character for NORMAL style
        if (style == FontStyle.NORMAL) {
            return char
        }
        
        // Get the character mapping for this style
        val mapping = FontStyles.getMapping(style)
        
        // Return transformed character if found, otherwise return original (fallback)
        return mapping[char] ?: char
    }
    
    /**
     * Transform an entire text string based on the specified font style.
     * 
     * Transforms each character individually, with fallback to original characters
     * for any that aren't found in the style's mapping.
     * 
     * @param text The text to transform
     * @param style The font style to apply
     * @return The transformed text
     */
    fun transformText(text: String, style: FontStyle): String {
        // Return original text for NORMAL style
        if (style == FontStyle.NORMAL) {
            return text
        }
        
        // Get the character mapping for this style
        val mapping = FontStyles.getMapping(style)
        
        // Transform each character, using fallback for missing characters
        return text.map { char ->
            mapping[char] ?: char
        }.joinToString("")
    }
    
    /**
     * Get all available font styles.
     * 
     * @return List of all supported font styles
     */
    fun getAllStyles(): List<FontStyle> {
        return FontStyle.getAllStyles()
    }
    
    /**
     * Get a font style by its display name.
     * 
     * @param displayName The display name of the style
     * @return The FontStyle if found, null otherwise
     */
    fun getStyleByName(displayName: String): FontStyle? {
        return FontStyle.getByDisplayName(displayName)
    }
}
