package kr.sixtyfive

class AlignRight : Alignment {
	override fun pad(): (String, Int, Char) -> String {
		return String::padStart
	}
}
