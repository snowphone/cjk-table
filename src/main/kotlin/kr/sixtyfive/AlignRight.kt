package kr.sixtyfive

object AlignRight : Alignment {
	override fun pad(): (String, Int, Char) -> String {
		return String::padStart
	}
}
