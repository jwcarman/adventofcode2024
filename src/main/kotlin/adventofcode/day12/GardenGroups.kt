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

package adventofcode.day12

import adventofcode.util.geom.plane.Direction
import adventofcode.util.geom.plane.Point2D
import adventofcode.util.graph.Graphs
import adventofcode.util.grid.TextGrid

private fun Point2D.zoomIn() = Point2D(x * 2 + 1, y * 2 + 1)

fun String.parseGardenGroups(): List<GardenGroup> {
    val garden = TextGrid(this.lines())
    return Graphs.connectedComponents(garden.coordinates().toSet()) { p ->
        p.neighbors().filter { it in garden && garden[it] == garden[p] }
    }
        .map { GardenGroup(it.map { p -> p.zoomIn() }.toSet()) }
}


fun String.calculateTotalFencePrice(): Long = parseGardenGroups().sumOf { it.fencePrice }

fun String.calculateTotalBulkFencePrice(): Long = parseGardenGroups().sumOf { it.bulkFencePrice }

data class PerimeterPoint(val point: Point2D, val side: Direction) {
    fun neighbors():List<PerimeterPoint> = if(side == Direction.NORTH || side == Direction.SOUTH) {
        listOf(point.east().east(), point.west().west()).map { PerimeterPoint(it, side) }
    } else {
        listOf(point.north().north(), point.south().south()).map { PerimeterPoint(it, side) }
    }
}

data class GardenGroup(private val points: Set<Point2D>) {

    private fun sides(): List<Set<PerimeterPoint>> {
        val perimeter = perimeter()
        return Graphs.connectedComponents(perimeter) { p -> p.neighbors().filter { it in perimeter } }
    }

    private fun perimeter(): Set<PerimeterPoint> {
        return points
            .flatMap { p -> setOf(
                PerimeterPoint(p.north(), Direction.NORTH),
                PerimeterPoint(p.south(), Direction.SOUTH),
                PerimeterPoint(p.east(), Direction.EAST),
                PerimeterPoint(p.west(), Direction.WEST)
                )
            }.filter { it.point.neighbors().count { n -> n in points } == 1 }.toSet()
    }

    val fencePrice: Long
        get() = (perimeter().size * points.size).toLong()

    val bulkFencePrice: Long
        get() = (sides().size * points.size).toLong()

}
