package be.pocito.pboard.style

/**
 * Transforms text into different Unicode font styles with graceful fallback
 * to original characters for styles that don't have complete coverage.
 */
object FontStyleTransformer {

    fun transformCharacter(char: Char, style: FontStyle): String {
        if (style == FontStyle.NORMAL) return char.toString()
        return FontStyles.getMapping(style)[char] ?: char.toString()
    }

    fun transformText(text: String, style: FontStyle): String {
        if (style == FontStyle.NORMAL) return text
        val mapping = FontStyles.getMapping(style)
        return text.map { char -> mapping[char] ?: char.toString() }.joinToString("")
    }
}
