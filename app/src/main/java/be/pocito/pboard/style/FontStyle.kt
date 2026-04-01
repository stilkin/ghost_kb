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
        fun getAllStyles(): List<FontStyle> = values().toList()

        fun getByDisplayName(name: String): FontStyle? = values().find { it.displayName == name }
    }
}
