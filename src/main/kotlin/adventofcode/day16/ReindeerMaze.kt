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
import adventofcode.util.grid.TextGrid

data class State(val position: Point2D, val orientation: Direction) {
    fun moveForward() = State(position + orientation, orientation)
    fun turnLeft() = State(position, orientation.turnLeft())
    fun turnRight() = State(position, orientation.turnRight())
}

private fun Direction.turnRight() = when (this) {
    Direction.NORTH -> Direction.EAST
    Direction.EAST -> Direction.SOUTH
    Direction.SOUTH -> Direction.WEST
    else -> Direction.NORTH
}

private fun Direction.turnLeft() = when (this) {
    Direction.NORTH -> Direction.WEST
    Direction.WEST -> Direction.SOUTH
    Direction.SOUTH -> Direction.EAST
    else -> Direction.NORTH
}

fun String.findBestTiles(): Int {
    val maze = TextGrid(lines())
    val start = State(maze.coordinates().find { maze[it] == 'S' }!!, Direction.EAST)

    val neighborsFn: (State) -> List<State> = { state ->
        listOf(state.moveForward(), state.turnRight(), state.turnLeft())
            .filter { maze[it.position] != '#' }
    }
    val reachable = Graphs.reachable(start, neighbors = neighborsFn)
    val ends = reachable.filter { maze[it.position] == 'E' }
    val memory = mutableMapOf<Pair<State, State>, Double>()
    val weight: (State, State) -> Double =
        { s1, s2 -> memory.computeIfAbsent(Pair(s1, s2)) { (s1, s2) -> score(s1, s2) } }
    val lowestScore =
        ends.minOf { end -> Graphs.shortestPaths(start, reachable, neighborsFn, weight).distanceTo(end).toInt() }

    val shortestPaths = ends
        .asSequence()
        .flatMap { end ->
            val shortestPathsForEnd =
                Graphs.shortestPaths(start, end, reachable, neighborsFn, weight)
                    .takeWhile { path -> path.score().toInt() == lowestScore }

            shortestPathsForEnd
        }.toList()

    val bestTiles = shortestPaths
        .flatten()
        .map { it.position }
        .toSet()
    return bestTiles.size

}

fun List<State>.score() = zipWithNext().sumOf { (s1, s2) -> score(s1, s2) }

private fun score(s1: State, s2: State) = if (s1.orientation == s2.orientation) 1.0 else 1000.0

fun String.findLowestScore(): Int {
    val maze = TextGrid(lines())
    val start = State(maze.coordinates().find { maze[it] == 'S' }!!, Direction.EAST)
    val neighborsFn: (State) -> List<State> = { state ->
        listOf(state.moveForward(), state.turnRight(), state.turnLeft())
            .filter { maze[it.position] != '#' }
    }
    val reachable = Graphs.reachable(start, neighbors = neighborsFn)
    return reachable.filter { maze[it.position] == 'E' }
        .minOf { end ->
            Graphs.shortestPaths(
                start,
                reachable,
                neighborsFn
            ) { s1, s2 -> score(s1, s2) }.distanceTo(end)
        }
        .toInt()
}