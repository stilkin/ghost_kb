package be.pocito.pboard.style

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
    SQUARE("Square"),
    ;

    companion object {
        /**
         * Resolves a persisted style name to a [FontStyle].
         * Falls back to [NORMAL] for null or unrecognized names (e.g. stored by an older app version).
         */
        fun fromName(name: String?): FontStyle = FontStyle.entries.firstOrNull { it.name == name } ?: NORMAL
    }
}
