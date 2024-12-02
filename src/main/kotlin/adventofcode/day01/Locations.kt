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
