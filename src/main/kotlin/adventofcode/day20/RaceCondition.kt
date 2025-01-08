/*
 * Copyright (c) 2025 James Carman
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

package adventofcode.day20

import adventofcode.util.geom.plane.Point2D
import adventofcode.util.graph.Graphs
import adventofcode.util.grid.TextGrid

fun String.countBestCheats(maxCheatLength: Int, savingsThreshold: Int): Int {
    val map = TextGrid(lines())
    val start = map.coordinates().find { map[it] == 'S' }!!
    val end = map.coordinates().find { map[it] == 'E' }!!
    val neighbors: (Point2D) -> List<Point2D> =
        { p: Point2D -> map.neighborsOf(p).filter { map[it] != '#' } }

    val shortestPath = Graphs.shortestPaths(start, neighbors).pathTo(end)

    val remainingStepsFrom = shortestPath.mapIndexed { index, point -> point to shortestPath.size - 1 - index }.toMap()
        .withDefault { Int.MAX_VALUE }

    return shortestPath.flatMapIndexed { index, origin ->
        shortestPath.subList(index + 1, shortestPath.size)
            .filter { cheat -> origin.manhattanDistance(cheat) <= maxCheatLength }
            .map { cheat ->
                remainingStepsFrom.getValue(origin) - origin.manhattanDistance(cheat) - remainingStepsFrom.getValue(
                    cheat
                )
            }
    }.count { it >= savingsThreshold }
}