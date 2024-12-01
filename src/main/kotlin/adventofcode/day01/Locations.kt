package adventofcode.day01

import adventofcode.util.occurrences
import adventofcode.util.parseIntSplits
import kotlin.math.abs

fun String.calculateTotalDistance(): Int {
    val lines = lines()
    val list1 = lines.parseIntSplits(0).sorted()
    val list2 = lines.parseIntSplits(1).sorted()
    return list1.zip(list2).sumOf { abs(it.first - it.second) }
}

fun String.calculateSimilarityScore(): Int {
    val lines = lines()
    val list1 = lines.parseIntSplits(0)
    val counts = lines.parseIntSplits(1).occurrences()
    return list1.sumOf { n -> n * counts.getValue(n) }
}
