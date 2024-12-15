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

package adventofcode.day15

import adventofcode.util.geom.plane.Direction
import adventofcode.util.geom.plane.Point2D
import adventofcode.util.geom.plane.Ray
import adventofcode.util.grid.TextGrid
import adventofcode.util.removeWhitespace
import adventofcode.util.splitByEmptyLines

fun String.parseInput(): Pair<TextGrid, Sequence<Direction>> {
    val parts = splitByEmptyLines()
    val map = TextGrid(parts[0].lines())
    val moves = parts[1].removeWhitespace().asSequence()
        .map {
            when (it) {
                '^' -> Direction.NORTH
                'v' -> Direction.SOUTH
                '>' -> Direction.EAST
                else -> Direction.WEST

            }
        }
    return Pair(map, moves)
}

private const val BOX = 'O'
private const val ROBOT = '@'
private const val OPEN = '.'
private const val WALL = '#'
private const val LEFT_BOX = '['
private const val RIGHT_BOX = ']'

fun TextGrid.boxLocations() = coordinates().filter { this[it] == BOX || this[it] == LEFT_BOX }

fun Point2D.gpsCoordinate() = y * 100 + x

fun TextGrid.pushOnce(robotLocation: Point2D, direction: Direction): Point2D {
    require(this[robotLocation] == ROBOT)
    val path = Ray(robotLocation, direction).points().takeWhile { this[it] != WALL }.toList()
    if (path.none { this[it] == OPEN }) {
        return robotLocation
    }
    path.takeWhile { this[it] != OPEN }.reversed().forEach { this[it + direction] = this[it] }
    this[robotLocation] = OPEN
    return robotLocation + direction
}

fun String.calculateSumOfGpsCoordinates(): Int {
    val (map, moves) = parseInput()
    moves.fold(map.coordinates().first { map[it] == ROBOT }) { location, direction ->
        map.pushOnce(
            location,
            direction
        )
    }
    return map.boxLocations().sumOf { it.gpsCoordinate() }
}

fun String.widened() = this
    .replace("$WALL", "$WALL$WALL")
    .replace("$BOX", "$LEFT_BOX$RIGHT_BOX")
    .replace("$OPEN", "$OPEN$OPEN")
    .replace("$ROBOT", "$ROBOT$OPEN")