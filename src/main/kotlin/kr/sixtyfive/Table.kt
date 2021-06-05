package kr.sixtyfive

/**
 * @param alignment A rule for an alignment. Either of `AlignLeft` or `AlignRight` is allowed.
 */
class Table(
	alignment: Alignment = RightAlign,
	delimiterSet: Delimiter = FancyDelimiter
) {
	private val delimiter = delimiterSet.vertical
	private val horizontalLine = delimiterSet.horizontal
	private val cross = delimiterSet.cross
	private val topCross = delimiterSet.topCross
	private val bottomCross = delimiterSet.bottomCross

	private val table = mutableListOf<List<String>>()
	private val lineIndices = mutableSetOf<Int>()
	private val padFn = alignment.pad()

	/**
	 * Insert a row to the bottom of the table.
	 */
	fun addRow(vararg args: Any): Table {
		table.add(args.iterator().asSequence().map(Any::toString).toList())
		return this
	}

	/**
	 * Add a horizontal line to the bottom of a current table.
	 */
	fun addLine(): Table {
		lineIndices.add(table.size)
		return this
	}

	/**
	 * Render a table and return as a string.
	 */
	fun render(): String {
		val colLen = table.map { it.size }.maxOrNull() ?: 0
		val widths = (0 until colLen).map(this::calculateWidth).toList()
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

	/**
	 * Return maximum length in `colIndex`th column.
	 * Note that the length is in half-width.
	 */
	private fun calculateWidth(colIndex: Int): Int {
		val vertLine = table.map { if (colIndex in it.indices) it[colIndex] else "" }
		return (vertLine.map(this::strLenInHalfWidth).maxOrNull() ?: 0)
	}

	private fun strLenInHalfWidth(s: String): Int {
		return s.map {
			if (it.isFullWidth()) 2 else 1
		}.sum()
	}
}
