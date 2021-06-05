package kr.sixtyfive

object RightAlign : Alignment {
	override fun pad(): (String, Int, Char) -> String {
		return String::padStart
	}
}
