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

package adventofcode.day18

import adventofcode.util.geom.plane.Point2D
import adventofcode.util.graph.Graphs
import adventofcode.util.grid.TextGrid

fun String.parsePoints(): List<Point2D> = lines()
    .map { it.split(',').map { split -> split.toInt() } }
    .map { (x, y) -> Point2D(x, y) }

fun List<Point2D>.generateMap() = TextGrid(List(maxOf { it.y } + 1) { ".".repeat(maxOf { it.x } + 1) })

fun String.findFirstByteThatBlocksExit(): Point2D {
    val points = parsePoints()

    val start = Point2D.origin()
    val xRange = 0..points.maxOf { it.x }
    val yRange = 0..points.maxOf { it.y }
    val end = Point2D(xRange.last, yRange.last)
    val fallen = mutableSetOf<Point2D>()
    val neighbors =
        { p: Point2D -> p.neighbors().filter { it.x in xRange && it.y in yRange }.filter { it !in fallen} }
    return points.first { p ->
        fallen.add(p)
        Graphs.dfs(start, end, neighbors).isEmpty()
    }
}

fun String.findShortestPathToExitAfter(nBytes: Int): Int {
    val points = parsePoints()
    val map = points.generateMap()
    points.take(nBytes).forEach { map[it] = '#' }
    val vertices = map.coordinates().filter { map[it] != '#' }.toSet()
    val neighbors = { p: Point2D -> p.neighbors().filter { it in map }.filter { map[it] != '#' } }
    return Graphs.shortestPaths(
        map.coordinates().first(),
        vertices,
        neighbors
    ) { _, _ -> 1.0 }.pathTo(map.coordinates().last()).size - 1
}