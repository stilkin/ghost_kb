package be.pocito.pboard.style

import org.junit.Assert.assertEquals
import org.junit.Test

class FontStyleTest {
    @Test
    fun fromName_resolvesEveryStyleByItsEnumName() {
        for (style in FontStyle.entries) {
            assertEquals(style, FontStyle.fromName(style.name))
        }
    }

    @Test
    fun fromName_fallsBackToNormalForUnknownNames() {
        assertEquals(FontStyle.NORMAL, FontStyle.fromName("MONOSPACE"))
        assertEquals(FontStyle.NORMAL, FontStyle.fromName(""))
    }

    @Test
    fun fromName_isCaseSensitive() {
        // Enum names are upper case; anything else is treated as unrecognized
        assertEquals(FontStyle.NORMAL, FontStyle.fromName("normal"))
    }

    @Test
    fun fromName_fallsBackToNormalForNullName() {
        assertEquals(FontStyle.NORMAL, FontStyle.fromName(null))
    }
}
