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
import adventofcode.util.grid.TextGrid
import adventofcode.util.removeWhitespace
import adventofcode.util.splitByEmptyLines

fun String.parseInput(): Pair<Warehouse, Sequence<Direction>> {
    val parts = splitByEmptyLines()
    val moves = parts[1].removeWhitespace().asSequence()
        .map {
            when (it) {
                '^' -> Direction.NORTH
                'v' -> Direction.SOUTH
                '>' -> Direction.EAST
                else -> Direction.WEST

            }
        }
    return Pair(Warehouse(parts[0]), moves)
}

private const val BOX = 'O'
private const val ROBOT = '@'
private const val OPEN = '.'
private const val WALL = '#'
private const val LEFT_BOX = '['
private const val RIGHT_BOX = ']'

class Warehouse(input: String) {
    val map = TextGrid(input.lines())
    var robotLocation = map.coordinates().first { map[it] == ROBOT }

    fun push(direction: Direction) {
        val reachable = reachableFrom(robotLocation, direction)
        if (reachable.any { map[it] == WALL }) {
            return
        }
        reachable.filter { map[it] != OPEN }.forEach {
            map[it + direction] = map[it]
            map[it] = OPEN
        }
        robotLocation += direction
    }

    fun boxLocations() = map.coordinates().filter { map[it] == BOX || map[it] == LEFT_BOX }

    private fun reachableFrom(location: Point2D, direction: Direction): List<Point2D> {
        val queue = mutableListOf(location)
        val reachable = mutableListOf<Point2D>()
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            reachable.addFirst(current)
            val neighbor = current + direction

            when (direction) {
                Direction.NORTH, Direction.SOUTH -> when (map[current]) {
                    BOX, ROBOT, LEFT_BOX, RIGHT_BOX -> when (map[neighbor]) {
                        LEFT_BOX -> {
                            queue.add(neighbor)
                            queue.add(neighbor.east())
                        }

                        RIGHT_BOX -> {
                            queue.add(neighbor)
                            queue.add(neighbor.west())
                        }

                        else -> queue.add(neighbor)
                    }

                    else -> continue
                }

                else -> when (map[current]) {
                    BOX, LEFT_BOX, RIGHT_BOX, ROBOT -> queue.add(neighbor)
                    else -> continue
                }
            }
        }
        return reachable.distinct()
    }
}

fun Point2D.gpsCoordinate(): Long = 100L * y + x

fun String.calculateSumOfGpsCoordinates(): Long {
    val (warehouse, moves) = parseInput()
    moves.forEach { warehouse.push(it) }
    return warehouse.boxLocations().sumOf { it.gpsCoordinate() }
}

fun String.widened() = this
    .replace("$WALL", "$WALL$WALL")
    .replace("$BOX", "$LEFT_BOX$RIGHT_BOX")
    .replace("$OPEN", "$OPEN$OPEN")
    .replace("$ROBOT", "$ROBOT$OPEN")