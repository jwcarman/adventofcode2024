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

package adventofcode.day16

import adventofcode.util.geom.plane.Direction
import adventofcode.util.geom.plane.Point2D
import adventofcode.util.graph.Graphs
import adventofcode.util.graph.ShortestPaths
import adventofcode.util.grid.TextGrid

fun String.countBestTiles(): Int = ReindeerMaze(this).countBestTiles()

fun String.findLowestScore() = ReindeerMaze(this).findLowestScore()

data class Pose(val position: Point2D, val orientation: Direction) {
    fun goForward() = Pose(position + orientation, orientation)
    fun goBack() = Pose(position - orientation, orientation)
    fun turnLeft() = Pose(
        position, when (orientation) {
            Direction.NORTH -> Direction.WEST
            Direction.WEST -> Direction.SOUTH
            Direction.SOUTH -> Direction.EAST
            else -> Direction.NORTH
        }
    )

    fun turnRight() = Pose(
        position, when (orientation) {
            Direction.NORTH -> Direction.EAST
            Direction.EAST -> Direction.SOUTH
            Direction.SOUTH -> Direction.WEST
            else -> Direction.NORTH
        }
    )

    fun distanceTo(other: Pose) = if (orientation == other.orientation) 1.0 else 1000.0
}

class Traversal(val path: List<Pose>, val distance: Double) {
    val visited = path.toSet()
}

private const val WALL = '#'
private const val END = 'E'
private const val START = 'S'

class ReindeerMaze(input: String) {

    private val maze = TextGrid(input.lines())

    private fun Pose.isWall() = maze[position] == WALL

    fun successorsOf(pose: Pose) = neighborsFn(END) { it.goForward() }(pose)
    fun predecessorsOf(pose: Pose) = neighborsFn(START) { it.goBack() }(pose)

    private fun neighborsFn(goal: Char, advance: (Pose) -> Pose): (Pose) -> List<Pose> = { state ->
        val neighbors = mutableListOf<Pose>()
        if (maze[state.position] != goal) {
            if (!advance(state).isWall()) {
                neighbors.add(advance(state))
            }
            if (!advance(state.turnLeft()).isWall()) {
                neighbors.add(state.turnLeft())
            }
            if (!advance(state.turnRight()).isWall()) {
                neighbors.add(state.turnRight())
            }
        }
        neighbors
    }

    private fun shortestPaths(): ShortestPaths<Pose> {
        val start = Pose(maze.coordinates().find { maze[it] == START }!!, Direction.EAST)
        val vertices = Graphs.reachable(start, neighbors = ::successorsOf)
        return Graphs.shortestPaths(start, vertices, ::successorsOf) { p1, p2 -> p1.distanceTo(p2) }
    }

    fun findLowestScore(): Int {
        val shortestPaths = shortestPaths()
        val ends = shortestPaths.reachable().filter { maze[it.position] == END }
        return ends.minOf { end -> shortestPaths.distanceTo(end) }.toInt()
    }

    fun countBestTiles(): Int {
        val shortestPaths = shortestPaths()
        val ends = shortestPaths.reachable().filter { maze[it.position] == END }
        val shortestDistance = ends.minOf { end -> shortestPaths.distanceTo(end) }
        val stack = mutableListOf<Traversal>()
        ends.forEach { stack.addFirst(Traversal(listOf(it), 0.0)) }
        val allShortestPaths = mutableListOf<List<Pose>>()
        while (stack.isNotEmpty()) {
            val traversal = stack.removeFirst()
            val currentState = traversal.path.last()
            if (maze[currentState.position] == START) {
                allShortestPaths.add(traversal.path)
            } else {
                predecessorsOf(currentState)
                    .filter { it !in traversal.visited }
                    .filter { shortestPaths.distanceTo(it) + traversal.distance <= shortestDistance }
                    .forEach { pred ->
                        val newPath = traversal.path + pred
                        val newDistance = traversal.distance + pred.distanceTo(currentState)
                        stack.addFirst(Traversal(newPath, newDistance))
                    }
            }
        }
        return allShortestPaths.flatten().map { it.position }.distinct().size
    }
}

