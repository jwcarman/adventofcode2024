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

package adventofcode.day06

import adventofcode.util.geom.plane.Direction
import adventofcode.util.geom.plane.Point2D
import adventofcode.util.grid.TextGrid

private fun Direction.turnRight() = when (this) {
    Direction.NORTH -> Direction.EAST
    Direction.EAST -> Direction.SOUTH
    Direction.SOUTH -> Direction.WEST
    Direction.WEST -> Direction.NORTH
    Direction.NORTHEAST -> Direction.SOUTHEAST
    Direction.SOUTHEAST -> Direction.SOUTHWEST
    Direction.SOUTHWEST -> Direction.NORTHWEST
    Direction.NORTHWEST -> Direction.NORTHEAST
}

private const val OBSTACLE = '#'
private const val OPEN = '.'

private fun TextGrid.isObstacle(position: Point2D) = position in this && this[position] == OBSTACLE

private class Guard {
    fun patrol(map: TextGrid) = sequence {
        var step = Step(map.coordinates().first { map[it] == '^' }, Direction.NORTH)
        while (step.position in map) {
            yield(step)
            val nextStep = step.nextStep()
            step = if (map.isObstacle(nextStep.position)) {
                step.turnRight()
            } else {
                nextStep
            }
        }
    }
}

private data class Step(val position: Point2D, val direction: Direction) {
    fun nextStep() = copy(position = position + direction)
    fun turnRight() = copy(direction = direction.turnRight())
}

private fun Sequence<Step>.isLoop(): Boolean {
    val visited = mutableSetOf<Step>()
    for (step in this) {
        if (step in visited) {
            return true
        }
        visited.add(step)
    }
    return false
}

private fun <T> TextGrid.withObstacleAt(position: Point2D, function: (TextGrid) -> T): T {
    try {
        this[position] = OBSTACLE
        return function(this)
    } finally {
        this[position] = OPEN
    }
}

fun String.countObstacleLocationsThatCauseLoops(): Int {
    val map = TextGrid(this.lines())
    val guard = Guard()
    val candidates = guard.patrol(map)
        .map { it.position }
        .filter { map[it] == OPEN }
        .toSet()
    return candidates
        .count { location -> map.withObstacleAt(location) { guard.patrol(it).isLoop() } }
}

fun String.calculatePatrolPathLength() = Guard().patrol(TextGrid(this.lines()))
    .map { it.position }
    .toSet()
    .size
