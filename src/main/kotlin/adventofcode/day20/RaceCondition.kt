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

fun String.countBestCheats(savingsThreshold: Int): Int {
    val map = TextGrid(lines())
    val start = map.coordinates().find { map[it] == 'S' }!!
    val end = map.coordinates().find { map[it] == 'E' }!!
    val vertices = map.coordinates().filter { map[it] != '#' }.toSet()
    val neighbors: (Point2D) -> List<Point2D> =
        { p: Point2D -> p.neighbors().filter { it in map }.filter { map[it] != '#' } }
    val shortestPaths = Graphs.shortestPaths(
        start,
        vertices,
        neighbors
    )
    val shortestDistance = shortestPaths.distanceTo(end)
    val shortestPath = shortestPaths.pathTo(end)

    val cheats = shortestPath.flatMap { p -> p.neighbors().filter { map[it] == '#' && !map.isEdge(it) } }.toSet()
    return cheats.count { cheat ->
        map[cheat] = '.'
        val newShortestPaths = Graphs.shortestPaths(start, vertices + cheat, neighbors)
        map[cheat] = '#'
        newShortestPaths.distanceTo(end) <= shortestDistance - savingsThreshold
    }
}