package kr.sixtyfive

class AlignLeft : Alignment {
	override fun pad(): (String, Int, Char) -> String {
		return String::padEnd
	}
}
