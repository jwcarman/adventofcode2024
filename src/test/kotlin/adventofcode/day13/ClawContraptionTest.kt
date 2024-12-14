package adventofcode.day13

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ClawContraptionTest {

    @Test
    fun `first machine should cost 280 tokens`() {
        val input = """
     Button A: X+94, Y+34
     Button B: X+22, Y+67
     Prize: X=8400, Y=5400
    """.trimIndent()

        input.parseClawMachine().calculateTokensRequired() shouldBe 280
    }

    @Test
    fun `second machine should not be possible`() {
        val input = """
            Button A: X+26, Y+66
            Button B: X+67, Y+21
            Prize: X=12748, Y=12176
        """.trimIndent()
        input.parseClawMachine().calculateTokensRequired() shouldBe -1
    }

    @Test
    fun `third machine should cost 200 tokens`() {
        val input = """
            Button A: X+17, Y+86
            Button B: X+84, Y+37
            Prize: X=7870, Y=6450
        """.trimIndent()
        input.parseClawMachine().calculateTokensRequired() shouldBe 200
    }

    @Test
    fun `fourth machine should not be possible`() {
        val input = """
            Button A: X+69, Y+23
            Button B: X+27, Y+71
            Prize: X=18641, Y=10279
        """.trimIndent()
        input.parseClawMachine().calculateTokensRequired() shouldBe -1
    }
}