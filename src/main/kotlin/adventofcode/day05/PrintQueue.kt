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

package adventofcode.day05

typealias PageOrderingRules = Map<Int, Set<Int>>
typealias SafetyManualUpdate = List<Int>

fun String.sumUpValidUpdates(): Int {
    val splits = split("\n\n")
    val orderingRules = splits[0].parsePageOrderingRules()
    val updates = splits[1].parseUpdates()
    return updates
        .filter { it.isValid(orderingRules) }
        .sumOfMiddlePageNumbers()
}

fun String.sumUpCorrectedUpdates(): Int {
    val splits = split("\n\n")
    val orderingRules = splits[0].parsePageOrderingRules()
    val updates = splits[1].parseUpdates()
    return updates
        .filterNot { it.isValid(orderingRules) }
        .map { it.reorder(orderingRules) }.sumOfMiddlePageNumbers()
}

fun String.parsePageOrderingRules(): PageOrderingRules = lines()
    .map { it.split('|') }
    .groupBy { it[0].toInt() }
    .mapValues { entry -> entry.value.map { it[1].toInt() }.toSet() }
    .withDefault { emptySet() }

fun String.parseUpdates(): List<SafetyManualUpdate> = lines()
    .map { it.split(',') }
    .map { it.map { i -> i.toInt() } }

fun List<SafetyManualUpdate>.sumOfMiddlePageNumbers() = sumOf { it[it.size / 2] }

fun SafetyManualUpdate.reorder(pageOrderingRules: PageOrderingRules) = sortedWith(pageOrderingRules.toComparator())

fun PageOrderingRules.toComparator() = Comparator<Int> { a, b ->
    when {
        b in getValue(a) -> -1
        a in getValue(b) -> 1
        else -> 0
    }
}

fun SafetyManualUpdate.isValid(orderingRules: PageOrderingRules): Boolean {
    val alreadyPrinted = mutableSetOf<Int>()
    for (pageNumber in this) {
        val successors = orderingRules.getValue(pageNumber)
        if (alreadyPrinted.intersect(successors).isNotEmpty()) {
            return false
        }
        alreadyPrinted.add(pageNumber)
    }
    return true
}