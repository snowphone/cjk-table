# cjk-table

This is a simple table supporting CJK letters and alignments.
Still there are several alternatives, but none of them supports both of CJK letters
(full-width letters) and aligned tables.
That is why I started this project.

## Usage

### maven

```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
<dependency>
  <groupId>com.github.snowphone</groupId>
  <artifactId>cjk-table</artifactId>
  <version>0.2</version>
</dependency>
```

### gradle

```kotlin
repositories {
  maven { url="https://jitpack.io".let(::uri) }
}
dependencies {
  implementation("com.github.snowphone:cjk-table:0.2")
}
```

## Example

```kotlin
  fun renderTable() {
    val expected =
      "──┬────────\n" +
      "ID│Language\n" +
      "──┼────────\n" +
      " 0│  한국어\n" +
      "──┴────────\n"

    // Default argument is AlignRight but AlignLeft is also possible!
    val table = Table() 
    table.addLine()
    table.addRow("ID", "Language")
    table.addLine()
    table.addRow(0, "한국어")
    table.addLine()

    assertEquals(expected, table.render())
  }
```
