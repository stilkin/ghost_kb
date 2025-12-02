package be.pocito.pboard.style

/**
 * Character mappings for each Unicode font style.
 * 
 * Each style contains a map of standard ASCII characters to their Unicode equivalents (as strings).
 * If a character is not found in the map, the original character is used as fallback.
 */
object FontStyles {
    
    /**
     * Double-struck (Blackboard Bold) style
     * Unicode range: Mathematical Alphanumeric Symbols (U+1D400–U+1D7FF)
     */
    val DOUBLE_STRUCK: Map<Char, String> = mapOf(
        'a' to "𝕒", 'b' to "𝕓", 'c' to "𝕔", 'd' to "𝕕", 'e' to "𝕖", 'f' to "𝕗",
        'g' to "𝕘", 'h' to "𝕙", 'i' to "𝕚", 'j' to "𝕛", 'k' to "𝕜", 'l' to "𝕝",
        'm' to "𝕞", 'n' to "𝕟", 'o' to "𝕠", 'p' to "𝕡", 'q' to "𝕢", 'r' to "𝕣",
        's' to "𝕤", 't' to "𝕥", 'u' to "𝕦", 'v' to "𝕧", 'w' to "𝕨", 'x' to "𝕩",
        'y' to "𝕪", 'z' to "𝕫",
        'A' to "𝔸", 'B' to "𝔹", 'C' to "ℂ", 'D' to "𝔻", 'E' to "𝔼", 'F' to "𝔽",
        'G' to "𝔾", 'H' to "ℍ", 'I' to "𝕀", 'J' to "𝕁", 'K' to "𝕂", 'L' to "𝕃",
        'M' to "𝕄", 'N' to "ℕ", 'O' to "𝕆", 'P' to "ℙ", 'Q' to "ℚ", 'R' to "ℝ",
        'S' to "𝕊", 'T' to "𝕋", 'U' to "𝕌", 'V' to "𝕍", 'W' to "𝕎", 'X' to "𝕏",
        'Y' to "𝕐", 'Z' to "ℤ",
        '0' to "𝟘", '1' to "𝟙", '2' to "𝟚", '3' to "𝟛", '4' to "𝟜", '5' to "𝟝",
        '6' to "𝟞", '7' to "𝟟", '8' to "𝟠", '9' to "𝟡"
    )
    
    /**
     * Cursive (Mathematical Italic) style
     * Unicode range: Mathematical Alphanumeric Symbols
     */
    val CURSIVE: Map<Char, String> = mapOf(
        'a' to "𝓪", 'b' to "𝓫", 'c' to "𝓬", 'd' to "𝓭", 'e' to "𝓮", 'f' to "𝓯",
        'g' to "𝓰", 'h' to "𝓱", 'i' to "𝓲", 'j' to "𝓳", 'k' to "𝓴", 'l' to "𝓵",
        'm' to "𝓶", 'n' to "𝓷", 'o' to "𝓸", 'p' to "𝓹", 'q' to "𝓺", 'r' to "𝓻",
        's' to "𝓼", 't' to "𝓽", 'u' to "𝓾", 'v' to "𝓿", 'w' to "𝔀", 'x' to "𝔁",
        'y' to "𝔂", 'z' to "𝔃",
        'A' to "𝓐", 'B' to "𝓑", 'C' to "𝓒", 'D' to "��", 'E' to "𝓔", 'F' to "𝓕",
        'G' to "𝓖", 'H' to "𝓗", 'I' to "𝓘", 'J' to "𝓙", 'K' to "𝓚", 'L' to "𝓛",
        'M' to "𝓜", 'N' to "𝓝", 'O' to "𝓞", 'P' to "𝓟", 'Q' to "𝓠", 'R' to "𝓡",
        'S' to "𝓢", 'T' to "𝓣", 'U' to "𝓤", 'V' to "𝓥", 'W' to "𝓦", 'X' to "𝓧",
        'Y' to "𝓨", 'Z' to "𝓩"
    )
    
    /**
     * Cursive Bold style
     * Unicode range: Mathematical Alphanumeric Symbols
     */
    val CURSIVE_BOLD: Map<Char, String> = mapOf(
        'a' to "𝒂", 'b' to "𝒃", 'c' to "𝒄", 'd' to "𝒅", 'e' to "𝒆", 'f' to "𝒇",
        'g' to "𝒈", 'h' to "𝒉", 'i' to "𝒊", 'j' to "𝒋", 'k' to "𝒌", 'l' to "𝒍",
        'm' to "𝒎", 'n' to "𝒏", 'o' to "𝒐", 'p' to "𝒑", 'q' to "𝒒", 'r' to "𝒓",
        's' to "𝒔", 't' to "𝒕", 'u' to "𝒖", 'v' to "𝒗", 'w' to "𝒘", 'x' to "𝒙",
        'y' to "𝒚", 'z' to "𝒛",
        'A' to "𝑨", 'B' to "𝑩", 'C' to "��", 'D' to "𝑫", 'E' to "𝑬", 'F' to "𝑭",
        'G' to "𝑮", 'H' to "𝑯", 'I' to "𝑰", 'J' to "𝑱", 'K' to "𝑲", 'L' to "𝑳",
        'M' to "𝑴", 'N' to "𝑵", 'O' to "𝑶", 'P' to "𝑷", 'Q' to "𝑸", 'R' to "𝑹",
        'S' to "𝑺", 'T' to "𝑻", 'U' to "𝑼", 'V' to "𝑽", 'W' to "𝑾", 'X' to "𝑿",
        'Y' to "𝒀", 'Z' to "𝒁"
    )
    
    /**
     * Fraktur (Gothic/Blackletter) style
     * Unicode range: Mathematical Alphanumeric Symbols
     */
    val FRAKTUR: Map<Char, String> = mapOf(
        'a' to "𝔞", 'b' to "𝔟", 'c' to "𝔠", 'd' to "𝔡", 'e' to "𝔢", 'f' to "𝔣",
        'g' to "𝔤", 'h' to "𝔥", 'i' to "𝔦", 'j' to "𝔧", 'k' to "𝔨", 'l' to "𝔩",
        'm' to "𝔪", 'n' to "𝔫", 'o' to "𝔬", 'p' to "𝔭", 'q' to "𝔮", 'r' to "𝔯",
        's' to "𝔰", 't' to "𝔱", 'u' to "𝔲", 'v' to "𝔳", 'w' to "𝔴", 'x' to "𝔵",
        'y' to "𝔶", 'z' to "𝔷",
        'A' to "𝔄", 'B' to "𝔅", 'C' to "𝔆", 'D' to "𝔇", 'E' to "��", 'F' to "𝔉",
        'G' to "𝔊", 'H' to "ℌ", 'I' to "ℑ", 'J' to "𝔍", 'K' to "𝔎", 'L' to "𝔏",
        'M' to "𝔐", 'N' to "𝔑", 'O' to "𝔒", 'P' to "𝔓", 'Q' to "𝔔", 'R' to "ℜ",
        'S' to "𝔖", 'T' to "𝔗", 'U' to "𝔘", 'V' to "𝔙", 'W' to "𝔚", 'X' to "𝔛",
        'Y' to "𝔜", 'Z' to "ℨ"
    )
    
    /**
     * Fraktur Bold style
     * Unicode range: Mathematical Alphanumeric Symbols
     */
    val FRAKTUR_BOLD: Map<Char, String> = mapOf(
        'a' to "𝖆", 'b' to "𝖇", 'c' to "𝖈", 'd' to "𝖉", 'e' to "𝖊", 'f' to "𝖋",
        'g' to "𝖌", 'h' to "𝖍", 'i' to "𝖎", 'j' to "𝖏", 'k' to "𝖐", 'l' to "𝖑",
        'm' to "𝖒", 'n' to "𝖓", 'o' to "𝖔", 'p' to "𝖕", 'q' to "𝖖", 'r' to "𝖗",
        's' to "𝖘", 't' to "��", 'u' to "𝖚", 'v' to "𝖛", 'w' to "𝖜", 'x' to "𝖝",
        'y' to "𝖞", 'z' to "𝖟",
        'A' to "𝕳", 'B' to "𝖀", 'C' to "𝖁", 'D' to "𝖂", 'E' to "𝖃", 'F' to "𝖄",
        'G' to "𝖅", 'H' to "𝖆", 'I' to "𝖇", 'J' to "𝖈", 'K' to "𝖉", 'L' to "𝖊",
        'M' to "𝖋", 'N' to "𝖌", 'O' to "𝖍", 'P' to "𝖎", 'Q' to "𝖏", 'R' to "𝖐",
        'S' to "𝖑", 'T' to "𝖒", 'U' to "𝖓", 'V' to "𝖔", 'W' to "𝖕", 'X' to "𝖖",
        'Y' to "𝖗", 'Z' to "𝖘"
    )
    
    /**
     * Fullwidth style
     * Unicode range: Halfwidth and Fullwidth Forms
     */
    val FULLWIDTH: Map<Char, String> = mapOf(
        'a' to "ａ", 'b' to "ｂ", 'c' to "ｃ", 'd' to "ｄ", 'e' to "ｅ", 'f' to "ｆ",
        'g' to "ｇ", 'h' to "ｈ", 'i' to "ｉ", 'j' to "ｊ", 'k' to "ｋ", 'l' to "ｌ",
        'm' to "ｍ", 'n' to "ｎ", 'o' to "ｏ", 'p' to "ｐ", 'q' to "ｑ", 'r' to "ｒ",
        's' to "ｓ", 't' to "ｔ", 'u' to "ｕ", 'v' to "ｖ", 'w' to "ｗ", 'x' to "ｘ",
        'y' to "ｙ", 'z' to "ｚ",
        'A' to "Ａ", 'B' to "Ｂ", 'C' to "Ｃ", 'D' to "Ｄ", 'E' to "Ｅ", 'F' to "Ｆ",
        'G' to "Ｇ", 'H' to "Ｈ", 'I' to "Ｉ", 'J' to "Ｊ", 'K' to "Ｋ", 'L' to "Ｌ",
        'M' to "Ｍ", 'N' to "Ｎ", 'O' to "Ｏ", 'P' to "Ｐ", 'Q' to "Ｑ", 'R' to "Ｒ",
        'S' to "Ｓ", 'T' to "Ｔ", 'U' to "Ｕ", 'V' to "Ｖ", 'W' to "Ｗ", 'X' to "Ｘ",
        'Y' to "Ｙ", 'Z' to "Ｚ",
        '0' to "０", '1' to "１", '2' to "２", '3' to "３", '4' to "４", '5' to "５",
        '6' to "６", '7' to "７", '8' to "８", '9' to "９",
        ' ' to "　", '!' to "！", '"' to "＂", '#' to "＃", '$' to "＄", '%' to "％",
        '&' to "＆", '\'' to "＇", '(' to "（", ')' to "）", '*' to "＊", '+' to "＋",
        ',' to "，", '-' to "－", '.' to "．", '/' to "／", ':' to "：", ';' to "；",
        '<' to "＜", '=' to "＝", '>' to "＞", '?' to "？", '@' to "＠", '[' to "［",
        '\\' to "＼", ']' to "］", '^' to "＾", '_' to "＿", '`' to "｀", '{' to "｛",
        '|' to "｜", '}' to "｝", '~' to "～"
    )
    
    /**
     * Small Caps style
     * Unicode range: Latin Extended-B, Phonetic Extensions
     */
    val SMALL_CAPS: Map<Char, String> = mapOf(
        'a' to "ᴀ", 'b' to "ʙ", 'c' to "ᴄ", 'd' to "ᴅ", 'e' to "ᴇ", 'f' to "ꜰ",
        'g' to "ɢ", 'h' to "ʜ", 'i' to "ɪ", 'j' to "ᴊ", 'k' to "ᴋ", 'l' to "ʟ",
        'm' to "ᴍ", 'n' to "ɴ", 'o' to "ᴏ", 'p' to "ᴘ", 'q' to "ᴘ", 'r' to "ʀ",
        's' to "ꜱ", 't' to "ᴛ", 'u' to "ᴜ", 'v' to "ᴠ", 'w' to "ᴡ", 'x' to "x",
        'y' to "ʏ", 'z' to "ᴢ",
        'A' to "A", 'B' to "B", 'C' to "C", 'D' to "D", 'E' to "E", 'F' to "F",
        'G' to "G", 'H' to "H", 'I' to "I", 'J' to "J", 'K' to "K", 'L' to "L",
        'M' to "M", 'N' to "N", 'O' to "O", 'P' to "P", 'Q' to "Q", 'R' to "R",
        'S' to "S", 'T' to "T", 'U' to "U", 'V' to "V", 'W' to "W", 'X' to "X",
        'Y' to "Y", 'Z' to "Z"
    )
    
    /**
     * Bubble style (light)
     * Unicode range: Enclosed Alphanumerics
     */
    val BUBBLE: Map<Char, String> = mapOf(
        'a' to "ⓐ", 'b' to "ⓑ", 'c' to "ⓒ", 'd' to "ⓓ", 'e' to "ⓔ", 'f' to "ⓕ",
        'g' to "ⓖ", 'h' to "ⓗ", 'i' to "ⓘ", 'j' to "ⓙ", 'k' to "ⓚ", 'l' to "ⓛ",
        'm' to "ⓜ", 'n' to "ⓝ", 'o' to "ⓞ", 'p' to "ⓟ", 'q' to "ⓠ", 'r' to "ⓡ",
        's' to "ⓢ", 't' to "ⓣ", 'u' to "ⓤ", 'v' to "ⓥ", 'w' to "ⓦ", 'x' to "ⓧ",
        'y' to "ⓨ", 'z' to "ⓩ",
        'A' to "Ⓐ", 'B' to "Ⓑ", 'C' to "Ⓒ", 'D' to "Ⓓ", 'E' to "Ⓔ", 'F' to "Ⓕ",
        'G' to "Ⓖ", 'H' to "Ⓗ", 'I' to "Ⓘ", 'J' to "Ⓙ", 'K' to "Ⓚ", 'L' to "Ⓛ",
        'M' to "Ⓜ", 'N' to "Ⓝ", 'O' to "Ⓞ", 'P' to "Ⓟ", 'Q' to "Ⓠ", 'R' to "Ⓡ",
        'S' to "Ⓢ", 'T' to "Ⓣ", 'U' to "Ⓤ", 'V' to "Ⓥ", 'W' to "Ⓦ", 'X' to "Ⓧ",
        'Y' to "Ⓨ", 'Z' to "Ⓩ",
        '0' to "⓪", '1' to "①", '2' to "②", '3' to "③", '4' to "④", '5' to "⑤",
        '6' to "⑥", '7' to "⑦", '8' to "⑧", '9' to "⑨"
    )
    
    /**
     * Bubble Black style (dark)
     * Unicode range: Enclosed Alphanumeric Supplement
     */
    val BUBBLE_BLACK: Map<Char, String> = mapOf(
        'a' to "🅐", 'b' to "🅑", 'c' to "🅒", 'd' to "🅓", 'e' to "🅔", 'f' to "🅕",
        'g' to "🅖", 'h' to "🅗", 'i' to "🅘", 'j' to "🅙", 'k' to "🅚", 'l' to "🅛",
        'm' to "🅜", 'n' to "🅝", 'o' to "🅞", 'p' to "🅟", 'q' to "🅠", 'r' to "🅡",
        's' to "🅢", 't' to "🅣", 'u' to "🅤", 'v' to "🅥", 'w' to "🅦", 'x' to "🅧",
        'y' to "🅨", 'z' to "🅩",
        'A' to "🅰", 'B' to "🅱", 'C' to "🅲", 'D' to "🅳", 'E' to "🅴", 'F' to "🅵",
        'G' to "🅶", 'H' to "🅷", 'I' to "🅸", 'J' to "🅹", 'K' to "🅺", 'L' to "🅻",
        'M' to "🅼", 'N' to "🅽", 'O' to "🅾", 'P' to "🅿", 'Q' to "🆀", 'R' to "🆁",
        'S' to "🆂", 'T' to "🆃", 'U' to "🆄", 'V' to "🆅", 'W' to "🆆", 'X' to "🆇",
        'Y' to "🆈", 'Z' to "🆉"
    )
    
    /**
     * Square style (light)
     * Unicode range: Enclosed Alphanumerics
     */
    val SQUARE: Map<Char, String> = mapOf(
        'a' to "🄰", 'b' to "🄱", 'c' to "🄲", 'd' to "🄳", 'e' to "🄴", 'f' to "🄵",
        'g' to "🄶", 'h' to "🄷", 'i' to "🄸", 'j' to "🄹", 'k' to "🄺", 'l' to "🄻",
        'm' to "🄼", 'n' to "🄽", 'o' to "🄾", 'p' to "🄿", 'q' to "🅀", 'r' to "🅁",
        's' to "🅂", 't' to "🅃", 'u' to "🅄", 'v' to "🅅", 'w' to "🅆", 'x' to "🅇",
        'y' to "🅈", 'z' to "🅉",
        'A' to "🄰", 'B' to "🄱", 'C' to "🄲", 'D' to "🄳", 'E' to "🄴", 'F' to "🄵",
        'G' to "🄶", 'H' to "🄷", 'I' to "🄸", 'J' to "��", 'K' to "🄺", 'L' to "🄻",
        'M' to "🄼", 'N' to "🄽", 'O' to "🄾", 'P' to "🄿", 'Q' to "🅀", 'R' to "🅁",
        'S' to "🅂", 'T' to "🅃", 'U' to "🅄", 'V' to "🅅", 'W' to "🅆", 'X' to "🅇",
        'Y' to "🅈", 'Z' to "🅉"
    )
    
    /**
     * Square Black style (dark)
     * Unicode range: Enclosed Alphanumeric Supplement
     */
    val SQUARE_BLACK: Map<Char, String> = mapOf(
        'a' to "🅰", 'b' to "🅱", 'c' to "🅲", 'd' to "🅳", 'e' to "🅴", 'f' to "🅵",
        'g' to "🅶", 'h' to "🅷", 'i' to "🅸", 'j' to "🅹", 'k' to "🅺", 'l' to "🅻",
        'm' to "🅼", 'n' to "🅽", 'o' to "🅾", 'p' to "🅿", 'q' to "🆀", 'r' to "🆁",
        's' to "🆂", 't' to "🆃", 'u' to "��", 'v' to "🆅", 'w' to "🆆", 'x' to "🆇",
        'y' to "🆈", 'z' to "🆉",
        'A' to "🅰", 'B' to "🅱", 'C' to "🅲", 'D' to "🅳", 'E' to "🅴", 'F' to "🅵",
        'G' to "🅶", 'H' to "🅷", 'I' to "🅸", 'J' to "🅹", 'K' to "🅺", 'L' to "🅻",
        'M' to "🅼", 'N' to "🅽", 'O' to "🅾", 'P' to "🅿", 'Q' to "🆀", 'R' to "🆁",
        'S' to "🆂", 'T' to "🆃", 'U' to "🆄", 'V' to "🆅", 'W' to "🆆", 'X' to "🆇",
        'Y' to "🆈", 'Z' to "🆉"
    )
    
    /**
     * Get the character mapping for a specific font style.
     */
    fun getMapping(style: FontStyle): Map<Char, String> {
        return when (style) {
            FontStyle.NORMAL -> emptyMap()
            FontStyle.DOUBLE_STRUCK -> DOUBLE_STRUCK
            FontStyle.CURSIVE -> CURSIVE
            FontStyle.CURSIVE_BOLD -> CURSIVE_BOLD
            FontStyle.FRAKTUR -> FRAKTUR
            FontStyle.FRAKTUR_BOLD -> FRAKTUR_BOLD
            FontStyle.FULLWIDTH -> FULLWIDTH
            FontStyle.SMALL_CAPS -> SMALL_CAPS
            FontStyle.BUBBLE -> BUBBLE
            FontStyle.BUBBLE_BLACK -> BUBBLE_BLACK
            FontStyle.SQUARE -> SQUARE
            FontStyle.SQUARE_BLACK -> SQUARE_BLACK
        }
    }
}
