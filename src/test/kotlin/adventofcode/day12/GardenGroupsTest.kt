package adventofcode.day12

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class GardenGroupsTest {

    @Test
    fun `first sides example should have a total price of 80`() {
        val input = """
            AAAA
            BBCD
            BBCC
            EEEC
        """.trimIndent()

        input.calculateTotalBulkFencePrice() shouldBe 80
    }

    @Test
    fun `second sides example should have a total price of 236`() {
        val input = """
            EEEEE
            EXXXX
            EEEEE
            EXXXX
            EEEEE
        """.trimIndent()
        input.calculateTotalBulkFencePrice() shouldBe 236
    }

    @Test
    fun `third sides example should have a total price of 368`() {
        val input = """
            AAAAAA
            AAABBA
            AAABBA
            ABBAAA
            ABBAAA
            AAAAAA
        """.trimIndent()
        input.calculateTotalBulkFencePrice() shouldBe 368
    }
}