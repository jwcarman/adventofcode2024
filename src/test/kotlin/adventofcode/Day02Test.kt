package adventofcode

import adventofcode.day02.isSafe
import adventofcode.day02.isSafeDampened
import adventofcode.util.splitByWhitespace
import adventofcode.util.splitsAsInt
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

    private fun calculatePart1(input: String): Int = input.lines()
        .map { it.splitsAsInt() }
        .count { it.isSafe() }


    private fun calculatePart2(input: String): Int = input.lines()
        .map { it.splitsAsInt() }
        .count { it.isSafe() || it.isSafeDampened() }
}