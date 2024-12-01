package adventofcode.day01

import adventofcode.util.readAsString
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day01Test {
    @Test
    fun example1() {
        calculatePart1(readAsString("day01-example1.txt")) shouldBe 11
    }

    @Test
    fun part1() {
        calculatePart1(readAsString("day01.txt")) shouldBe 2904518
    }

    @Test
    fun example2() {
        calculatePart2(readAsString("day01-example2.txt")) shouldBe 31
    }

    @Test
    fun part2() {
        calculatePart2(readAsString("day01.txt")) shouldBe 18650129
    }

    private fun calculatePart1(input: String): Int = input.calculateTotalDistance()

    private fun calculatePart2(input: String): Int = input.calculateSimilarityScore()
}