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

package adventofcode.day08

import adventofcode.util.gcdInt
import adventofcode.util.geom.plane.Point2D
import adventofcode.util.grid.TextGrid

fun List<Point2D>.pairCombinations() = sequence {
    for (p1 in this@pairCombinations) {
        for (p2 in this@pairCombinations) {
            if (p1 < p2) {
                yield(p1 to p2)
            }
        }
    }
}

fun Point2D.project(dx: Int, dy: Int) = generateSequence(this) { it.translate(dx, dy) }


fun String.countAntinodeLocationsGcd(): Int {
    val map = TextGrid(this.lines())
    val frequencyLocations = map.coordinates()
        .filter { map[it] != '.' }
        .groupBy { map[it] }
        .withDefault { emptyList() }

    return frequencyLocations.values
        .asSequence()
        .filter { it.size > 1 }
        .flatMap { it.pairCombinations() }
        .flatMap { (p1, p2) ->
            val dx = p2.x - p1.x
            val dy = p2.y - p1.y
            val gcd = gcdInt(dy, dx)
            val dxReduced = dx / gcd
            val dyReduced = dy / gcd
            p1.project(dxReduced, dyReduced).takeWhile {it in map}.toList() + p2.project(-dxReduced, -dyReduced).takeWhile { it in map }.toList()
        }.distinct().count { it in map }
}

fun String.countAntinodeLocations(): Int {
    val map = TextGrid(this.lines())
    val frequencyLocations = map.coordinates()
        .filter { map[it] != '.' }
        .groupBy { map[it] }
        .withDefault { emptyList() }

    return frequencyLocations.values
        .asSequence()
        .filter { it.size > 1 }
        .flatMap { it.pairCombinations() }
        .flatMap { (p1, p2) ->
            val dx = p2.x - p1.x
            val dy = p2.y - p1.y
            listOf(p2.translate(dx, dy), p1.translate(-dx, -dy))
        }.distinct().count { it in map }
}