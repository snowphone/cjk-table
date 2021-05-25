package kr.sixtyfive


class Table(
	alignment: Alignment = AlignRight
) {
	private val delimiter = '│'
	private val horizontalLine = '─'
	private val cross = '┼'
	private val topCross = '┬'
	private val bottomCross = '┴'

	private val table = mutableListOf<List<String>>()
	private val lineIndices = mutableSetOf<Int>()
	private val padFn = alignment.pad()

	/**
	 * Insert a row to the bottom of the table.
	 */
	fun addRow(vararg args: Any) {
		table.add(args.iterator().asSequence().map(Any::toString).toList())
	}

	/**
	 * Add a horizontal line to the bottom of a current table.
	 */
	fun addLine() {
		lineIndices.add(table.size)
	}

	/**
	 * Render a table and return as a string.
	 */
	fun render(): String {
		val colLen = table.map { it.size }.maxOrNull() ?: 0
		val widths = (0 until colLen).map(this::calcWidth).toList()
		val builder = StringBuilder()
		(0..table.size).forEach { r ->
			if (r in lineIndices) {
				val vertDelimiter = when (r) {
					0 -> topCross
					table.size -> bottomCross
					else -> cross
				}
				val borderLine = widths.map(horizontalLine.toString()::repeat).toList()
				builder.append(renderLine(borderLine, widths, vertDelimiter))
			}

			if (r in table.indices) {
				val lineWords = when (val col = table[r].size) {
					colLen -> table[r]
					else -> table[r] + List(colLen - col) { "" }
				}
				builder.append(renderLine(lineWords, widths))
			}

		}
		return builder.toString()
	}

	private fun renderLine(words: List<String>, widths: List<Int>, delimiter: Char = this.delimiter): String {
		val padChar = ' '
		val builder = StringBuilder()
		for (c in words.indices) {
			val word = words[c]
			val width = widths[c] - word.filter(Char::isFullWidth).count()
			val elem = padFn(word, width, padChar) + if (c + 1 < words.size) {
				delimiter
			} else {
				'\n'
			}
			builder.append(elem)
		}
		return builder.toString()
	}

	private fun calcWidth(colIndex: Int): Int {
		val vertLine = table.map { if (colIndex in it.indices) it[colIndex] else "" }
		return (vertLine.map(this::len).maxOrNull() ?: 0)
	}

	/**
	 * Return length by bytes (half-width)
	 */
	private fun len(s: String): Int {
		return s.map {
			if (it.isFullWidth()) 2 else 1
		}.sum()
	}
}