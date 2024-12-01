package adventofcode.day01

import adventofcode.util.collection.MutableBag
import adventofcode.util.parseIntSplits
import adventofcode.util.readAsString
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.abs

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

    private fun calculatePart1(input: String): Int {
        val list1 = input.lines().parseIntSplits(0).sorted()
        val list2 = input.lines().parseIntSplits(1).sorted()
        return list1.zip(list2).sumOf { abs(it.first - it.second) }
    }

    private fun calculatePart2(input: String): Int {
        val list1 = input.lines().parseIntSplits(0)
        val bag = MutableBag<Int>()
        bag.addAll(input.lines().parseIntSplits(1))

        return list1.sumOf { n -> n * bag.count(n) }
    }
}