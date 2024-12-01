package adventofcode

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day21Test {

    @Test
    fun example1() {
        calculatePart1(readExample1()) shouldBe 0
    }

    @Test
    fun part1() {
        calculatePart1(readInput()) shouldBe 0
    }

    @Test
    fun example2() {
        calculatePart2(readExample2()) shouldBe 0
    }

    @Test
    fun part2() {
        calculatePart2(readInput()) shouldBe 0
    }

    private fun calculatePart1(input: String): Int = 0

    private fun calculatePart2(input: String): Int = 0
}