/*
 * Copyright (c) 2024 James Carman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package adventofcode.day02

import adventofcode.util.splitsAsInt
import adventofcode.util.withoutIndex

fun String.countSafeReports() = lines()
    .map { it.splitsAsInt() }
    .count { it.isSafe() }

fun String.countSafeReportsWithDampener() = lines()
    .map { it.splitsAsInt() }
    .count { it.isSafe() || it.isSafeDampened() }

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