package adventofcode.util

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ListsTest {
    @Test
    fun `1 2 3 withoutIndex 0 should be 2 3`() {
        val input = listOf(1, 2, 3)
        input.withoutIndex(0) shouldBe listOf(2, 3)
    }

    @Test
    fun `1 2 3 withoutIndex 1 should be 1 3`() {
        val input = listOf(1, 2, 3)
        input.withoutIndex(1) shouldBe listOf(1, 3)
    }

    @Test
    fun `1 2 3 withoutIndex 2 should be 1 2`() {
        val input = listOf(1, 2, 3)
        input.withoutIndex(2) shouldBe listOf(1, 2)
    }

    @Test
    fun `1 2 3 withoutIndex -1 should be 1 2 3`() {
        val input = listOf(1, 2, 3)
        input.withoutIndex(-1) shouldBe listOf(1, 2, 3)
    }

    @Test
    fun `1 2 3 withoutIndex 3 should be 1 2 3`() {
        val input = listOf(1, 2, 3)
        input.withoutIndex(3) shouldBe listOf(1, 2, 3)
    }
}