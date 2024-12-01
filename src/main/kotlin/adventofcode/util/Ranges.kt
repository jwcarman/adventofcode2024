/*
 * Copyright (c) 2023 James Carman
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

package adventofcode.util

fun LongRange.intersection(other: LongRange): LongRange {
    val start = maxOf(this.first, other.first)
    val endInclusive = minOf(this.last, other.last)
    return start..endInclusive
}

operator fun LongRange.minus(other: LongRange): List<LongRange> {
    val intersection = this.intersection(other)
    if(intersection.isEmpty()) {
        return listOf(this)
    }
    val results = mutableListOf<LongRange>()
    if(this.first < intersection.first) {
        results.add(this.first ..< intersection.first)
    }
    if(this.last > intersection.last) {
        results.add((intersection.last + 1)..this.last)
    }
    return results
}

fun LongRange.removeAll(others:List<LongRange>): List<LongRange> {
    return others.fold(listOf(this)) { acc, other -> acc.flatMap { it - other } }
}

fun LongRange.translate(offset: Long): LongRange = (first + offset)..(last + offset)
