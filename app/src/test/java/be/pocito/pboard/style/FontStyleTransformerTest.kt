package be.pocito.pboard.style

import org.junit.Assert.assertEquals
import org.junit.Test

class FontStyleTransformerTest {
    @Test
    fun transformCharacter_normalStylePassesCharacterThrough() {
        assertEquals("a", FontStyleTransformer.transformCharacter('a', FontStyle.NORMAL))
        assertEquals("Z", FontStyleTransformer.transformCharacter('Z', FontStyle.NORMAL))
        assertEquals("7", FontStyleTransformer.transformCharacter('7', FontStyle.NORMAL))
        assertEquals("!", FontStyleTransformer.transformCharacter('!', FontStyle.NORMAL))
    }

    @Test
    fun transformCharacter_mapsLettersAndDigits() {
        assertEquals("ℍ", FontStyleTransformer.transformCharacter('H', FontStyle.DOUBLE_STRUCK))
        assertEquals("𝕒", FontStyleTransformer.transformCharacter('a', FontStyle.DOUBLE_STRUCK))
        assertEquals("𝟝", FontStyleTransformer.transformCharacter('5', FontStyle.DOUBLE_STRUCK))
    }

    @Test
    fun transformCharacter_fallsBackToOriginalForUnmappedCharacters() {
        // Cursive maps no digits
        assertEquals("5", FontStyleTransformer.transformCharacter('5', FontStyle.CURSIVE))
        // Fraktur maps no punctuation
        assertEquals("!", FontStyleTransformer.transformCharacter('!', FontStyle.FRAKTUR))
    }

    @Test
    fun transformText_normalStylePassesTextThrough() {
        assertEquals(
            "The quick brown fox jumps over 3 lazy dogs!",
            FontStyleTransformer.transformText("The quick brown fox jumps over 3 lazy dogs!", FontStyle.NORMAL),
        )
    }

    @Test
    fun transformText_mapsEveryCharacter() {
        assertEquals("ℍ𝕖𝕝𝕝𝕠", FontStyleTransformer.transformText("Hello", FontStyle.DOUBLE_STRUCK))
        // '5' has no cursive mapping and falls back mid-string
        assertEquals("𝒞𝓊𝓇𝓈𝒾𝓋ℯ5", FontStyleTransformer.transformText("Cursive5", FontStyle.CURSIVE))
    }

    @Test
    fun transformText_smallCapsTransformsLowercaseOnly() {
        assertEquals("ʜᴇʟʟᴏ", FontStyleTransformer.transformText("hello", FontStyle.SMALL_CAPS))
        // Small caps has no uppercase glyphs; uppercase letters are mapped to themselves
        assertEquals("HELLO", FontStyleTransformer.transformText("HELLO", FontStyle.SMALL_CAPS))
    }

    @Test
    fun transformText_emptyStringStaysEmpty() {
        assertEquals("", FontStyleTransformer.transformText("", FontStyle.CURSIVE))
    }

    @Test
    fun transformText_isConsistentWithPerCharacterTransformation() {
        val text = "Ghost Keyboard v3: Type in 11 styles!"
        for (style in FontStyle.entries) {
            val perCharacter = text.map { FontStyleTransformer.transformCharacter(it, style) }.joinToString("")
            assertEquals(perCharacter, FontStyleTransformer.transformText(text, style))
        }
    }
}
