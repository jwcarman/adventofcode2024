package adventofcode

import adventofcode.day02.countSafeReports
import adventofcode.day02.countSafeReportsWithDampener
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day02Test {

    @Test
    fun example1() {
        calculatePart1(readExample1()) shouldBe 2
    }

    @Test
    fun part1() {
        calculatePart1(readInput()) shouldBe 686
    }

    @Test
    fun example2() {
        calculatePart2(readExample2()) shouldBe 4
    }

    @Test
    fun part2() {
        calculatePart2(readInput()) shouldBe 717
    }

    private fun calculatePart1(input: String): Int = input.countSafeReports()

    private fun calculatePart2(input: String): Int = input.countSafeReportsWithDampener()
}