package adventofcode.util.table

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TableTest {

    @Test
    fun newTableShouldBeEmpty() {
        val table = Table<String, String, Int>()
        table.isEmpty() shouldBe true
    }


    @Test
    fun setShouldAddValue() {
        val table = Table<String, String, Int>()
        table["foo", "bar"] = 42
        table.isEmpty() shouldBe false
        table["foo", "bar"] shouldBe 42
        table.contains("foo", "bar") shouldBe true
    }

}