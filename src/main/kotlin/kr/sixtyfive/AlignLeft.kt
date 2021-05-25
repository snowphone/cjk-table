package kr.sixtyfive

object AlignLeft : Alignment {
	override fun pad(): (String, Int, Char) -> String {
		return String::padEnd
	}
}
