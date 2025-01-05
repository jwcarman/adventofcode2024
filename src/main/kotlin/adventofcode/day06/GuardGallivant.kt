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
import adventofcode.util.geom.plane.Pose
import adventofcode.util.grid.TextGrid

private const val OBSTACLE = '#'
private const val OPEN = '.'

private class Guard(input: String) {

    private val lab = TextGrid(input.lines())
    private val start = Pose(lab.coordinates().first { lab[it] == '^' }, Direction.NORTH)

    private fun Pose.patrolProtocol(): Pose {
        val fwd = forward()
        if(fwd.position !in lab) {
            return fwd
        }
        return if (lab[fwd.position] == OBSTACLE) {
            turnRight()
        } else {
            fwd
        }
    }

    fun patrol(): List<Point2D> {
        var current = start
        val visited = mutableSetOf<Pose>()
        val path = mutableListOf<Point2D>()
        while (current.position in lab && current !in visited) {
            visited.add(current)
            path.add(current.position)
            current = current.patrolProtocol()
        }
        path.add(current.position)
        return path
    }

    fun patrolWithObstacleAt(position: Point2D): List<Point2D> {
        val original = lab[position]
        try {
            lab[position] = OBSTACLE
            return patrol()
        } finally {
            lab[position] = original
        }
    }

    fun isOpen(position:Point2D): Boolean = lab[position] == OPEN
    fun isInLab(position:Point2D): Boolean = position in lab
}

fun String.countObstacleLocationsThatCauseLoops(): Int {
    val guard = Guard(this)
    return guard.patrol()
        .dropLast(1)
        .filter { guard.isOpen(it) }
        .toSet()
        .count { guard.isInLab(guard.patrolWithObstacleAt(it).last()) }
}

fun String.calculatePatrolPathLength() = Guard(this)
    .patrol()
    .toSet()
    .size - 1
