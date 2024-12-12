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

package adventofcode.day10

import adventofcode.util.graph.Graphs
import adventofcode.util.grid.TextGrid

fun String.sumOfAllTrailHeadRatings(): Int {
    val map = TextGrid(this.lines())
    val termini = map.coordinatesWithValues().filter { (_, value) -> value == '9' }.map { it.first }

    return map.coordinatesWithValues()
        .filter { (_, value) -> value == '0' }
        .map { it.first }
        .map { start ->
            termini.map { terminus ->
                Graphs.allPaths(start, terminus) { p ->
                    p.neighbors()
                        .filter { it in map }
                        .filter { n -> map[n] == map[p] + 1 }
                }
            }
                .map { it.size }
                .sum()
        }
        .sum()
}

fun String.sumOfAllTrailHeadScores(): Int {
    val map = TextGrid(this.lines())
    return map.coordinatesWithValues()
        .filter { (_, value) -> value == '0' }
        .map { it.first }
        .map { point ->
            Graphs.reachable(point, 9) { p ->
                p.neighbors()
                    .filter { it in map }
                    .filter { n -> map[n] == map[p] + 1 }
            }
                .filter { map[it] == '9' }
                .size
        }
        .sum()
}