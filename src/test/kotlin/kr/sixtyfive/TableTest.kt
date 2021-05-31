package kr.sixtyfive

import kotlin.test.Test
import kotlin.test.assertEquals

internal class TableTest {
	@Test
	fun chainCall() {
		val expected =
			"  Name\n" +
					"한국어\n"
		val table = Table()
			.addRow("Name")
			.addRow("한국어")

		assertEquals(expected, table.render())
	}

	@Test
	fun oneColumn() {
		val expected =
			"  Name\n" +
			"한국어\n"
		val table = Table()
		table.addRow("Name")
		table.addRow("한국어")

		assertEquals(expected, table.render())
	}

	@Test
	fun twoColumns() {
		val expected =
			"ID│  Name\n" +
			"10│한국어\n"
		val table = Table()
		table.addRow("ID", "Name")
		table.addRow(10, "한국어")

		assertEquals(expected, table.render())
	}

	@Test
	fun oneColumnHorizontalLine() {
		val expected =
			"Language\n" +
		    "────────\n" +
		    "  한국어\n"

		val table = Table()
		table.addRow("Language")
		table.addLine()
		table.addRow("한국어")

		assertEquals(expected, table.render())
	}

	@Test
	fun twoColumnsHorizontalLine() {
		val expected =
			"──┬──────\n" +
			"ID│  Name\n" +
			"──┼──────\n" +
			"10│한국어\n" +
			"──┴──────\n"
		val table = Table()
		table.addLine()
		table.addRow("ID", "Name")
		table.addLine()
		table.addRow("10", "한국어")
		table.addLine()

		assertEquals(expected, table.render())
	}

	@Test
	fun unevenColumns() {
		val expected =
			"ID│  Name│Food\n" +
			"10│한국어│    \n"
		val table = Table()
		table.addRow("ID", "Name", "Food")
		table.addRow(10, "한국어")

		assertEquals(expected, table.render())
	}
}