package kr.sixtyfive

object LeftAlign : Alignment {
	override fun pad(): (String, Int, Char) -> String {
		return String::padEnd
	}
}
