package adventofcode.day09

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class DiskFragmenterTest {

    @Test
    fun `should generate file blocks`() {
        "12345".toBlocks() shouldBe listOf(0, -1, -1, 1, 1, 1, -1, -1, -1, -1, 2, 2, 2, 2, 2)
    }
}