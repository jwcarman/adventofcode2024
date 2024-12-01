package adventofcode

import adventofcode.day01.calculateSimilarityScore
import adventofcode.day01.calculateTotalDistance
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day01Test {

    @Test
    fun example1() {
        calculatePart1(readExample1()) shouldBe 11
    }

    @Test
    fun part1() {
        calculatePart1(readInput()) shouldBe 2904518
    }

    @Test
    fun example2() {
        calculatePart2(readExample2()) shouldBe 31
    }

    @Test
    fun part2() {
        calculatePart2(readInput()) shouldBe 18650129
    }

    private fun calculatePart1(input: String): Int = input.calculateTotalDistance()

    private fun calculatePart2(input: String): Int = input.calculateSimilarityScore()
}