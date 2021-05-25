package kr.sixtyfive

interface Alignment {
	fun pad(): (String, Int, Char) -> String
}