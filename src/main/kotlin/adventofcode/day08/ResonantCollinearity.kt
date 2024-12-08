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

import adventofcode.util.geom.plane.Ray
import adventofcode.util.geom.plane.Slope
import adventofcode.util.grid.TextGrid
import adventofcode.util.uniquePairs


fun TextGrid.antennaPairs() = coordinates()
    .filter { this[it] != '.' }
    .groupBy { this[it] }
    .values
    .filter { it.size > 1 }
    .flatMap { it.uniquePairs() }


fun String.countMultipleAntinodes(): Int {
    val map = TextGrid(this.lines())
    return map.antennaPairs()
        .flatMap { (p1, p2) ->
            val slope = Slope(p1, p2)
            Ray(p1, slope).points().takeWhile { it in map } + Ray(p2, slope.reverse()).points().takeWhile { it in map }
        }
        .distinct()
        .count()
}

fun String.countSingularAntinodes(): Int {
    val map = TextGrid(this.lines())
    return map.antennaPairs()
        .flatMap { (p1, p2) ->
            val slope = Slope(p1, p2)
            Ray(p2, slope).points().drop(1).take(1) + Ray(p1, slope.reverse()).points().drop(1).take(1) }
        .filter { it in map }
        .distinct()
        .count()

}