package adventofcode.day02

import adventofcode.util.withoutIndex

fun List<Int>.isGraduallyIncreasing() = zipWithNext()
    .map { (a, b) -> b - a }
    .all { it in 1..3 }

fun List<Int>.isGraduallyDecreasing() = zipWithNext()
    .map { (a, b) -> b - a }
    .all { it in -3..-1 }

fun List<Int>.isSafe() = isGraduallyIncreasing() || isGraduallyDecreasing()

fun List<Int>.isSafeDampened() = dampened().any { it.isSafe() }

fun List<Int>.dampened() = indices
    .map { withoutIndex(it) }