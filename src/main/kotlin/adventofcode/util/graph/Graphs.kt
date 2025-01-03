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

package adventofcode.util.graph

import java.util.PriorityQueue
import kotlin.time.measureTime
import kotlin.time.measureTimedValue

private fun compareDoubles(left: Double, right: Double): Int {
    if (left < right) {
        return -1
    }
    if (right < left) {
        return 1
    }
    return 0
}

object Graphs {

    /**
     * Implementation of Yen's K-Shortest Paths Algorithm as a sequence
     * @param start the starting vertex
     * @param end the ending vertex
     * @param vertices the set of all vertices
     * @param neighbors a function that returns the neighbors of a vertex
     * @param weight a function that returns the weight of an edge
     * @return a sequence of increasingly longer paths from the start vertex to the end vertex
     */
    fun <V> shortestPaths(start: V, end: V, vertices: Set<V>, neighbors: (V) -> List<V>, weight: (V, V) -> Double) =
        sequence {
            val previousPaths = mutableListOf<List<V>>()
            val absoluteShortest = shortestPaths(start, vertices, neighbors, weight)
            if (absoluteShortest.pathExists(end)) {
                val shortest = absoluteShortest.pathTo(end)
                yield(shortest)
                previousPaths.addLast(shortest)
                var k = 2
                while (true) {
                    val previousPath = previousPaths.last()
                    val candidates = previousPath.asSequence().zipWithNext().map { (from, to) ->
                        val head = previousPath.takeWhile { it != to }
                        val visited = head.toSet()
                        val limitedNeighbors: (V) -> List<V> =
                            { n -> if (n == from) neighbors(n) - visited - to else neighbors(n) - visited }
                        val (tails, _) = measureTimedValue { shortestPaths(from, vertices, limitedNeighbors, weight) }
                        head to tails
                    }
                        .filter { (_, tails) -> tails.pathExists(end) }
                        .map { (head, tails) -> head + tails.pathTo(end).drop(1) }
                        .filter { it !in previousPaths }
                        .toList()

                    if (candidates.isEmpty()) {
                        return@sequence
                    }
                    val nextShortest =
                        candidates.minBy { it.asSequence().zipWithNext().sumOf { (from, to) -> weight(from, to) } }
                    yield(nextShortest)
                    previousPaths.addLast(nextShortest)
                    k++
                }
            }
        }

    /**
     * Implementation of Dijkstra's Algorithm
     * @param start the starting vertex
     * @param vertices the set of all vertices
     * @param neighbors a function that returns the neighbors of a vertex
     * @param weight a function that returns the weight of an edge
     * @return the shortest paths from the start vertex to all other vertices
     */
    fun <V> shortestPaths(
        start: V,
        vertices: Set<V>,
        neighbors: (V) -> List<V>,
        weight: (V, V) -> Double = { _, _ -> 1.0 }
    ): ShortestPaths<V> {
        val pred = mutableMapOf<V, V>()
        val dist = mutableMapOf<V, Double>()
        val visited = mutableSetOf<V>()
        vertices.forEach { vertex -> dist[vertex] = Double.POSITIVE_INFINITY }
        dist[start] = 0.0
        val queue = PriorityQueue { l: V, r: V ->
            compareDoubles(
                dist.getOrDefault(l, Double.MAX_VALUE),
                dist.getOrDefault(r, Double.MAX_VALUE)
            )
        }
        queue.add(start)
        while (queue.isNotEmpty()) {
            val vertex = queue.poll()
            require(vertex in vertices)
            visited.add(vertex)
            val distanceToVertex = dist.getOrDefault(vertex, Double.MAX_VALUE)
            neighbors(vertex).filter { it !in visited }.forEach { neighbor ->
                require(neighbor in vertices)
                val distanceThroughVertex = distanceToVertex + weight(vertex, neighbor)
                if (distanceThroughVertex < dist[neighbor]!!) {
                    pred[neighbor] = vertex
                    dist[neighbor] = distanceThroughVertex
                    queue.add(neighbor)
                }
            }
        }
        return ShortestPaths(start, dist, pred)
    }

    private data class Reachable<V>(val steps: Int, val vertex: V)

    fun <V> reachable(start: V, maxSteps: Int = Int.MAX_VALUE, neighbors: (V) -> List<V>): Set<V> {
        val visited = mutableSetOf<V>()
        val queue = mutableListOf(Reachable(0, start))
        while (queue.isNotEmpty()) {
            val reachable = queue.removeLast()
            visited += reachable.vertex
            if (reachable.steps < maxSteps) {
                val ns = neighbors(reachable.vertex)
                    .filter { it !in visited }
                    .map { Reachable(reachable.steps + 1, it) }
                queue.addAll(ns)
            }
        }
        return visited
    }

    fun <V> dfs(start: V, end: V, neighbors: (V) -> List<V>): List<V> {
        return search(start, end, neighbors) { addFirst(it) }
    }

    fun <V> bfs(start: V, end: V, neighbors: (V) -> List<V>): List<V> {
        return search(start, end, neighbors) { addLast(it) }
    }

    fun <V> dfsTraversal(start: V, neighbors: (V) -> List<V>): List<V> {
        return traverse(start, neighbors) { addFirst(it) }
    }

    fun <V> bfsTraversal(start: V, neighbors: (V) -> List<V>): List<V> {
        return traverse(start, neighbors) { addLast(it) }
    }

    fun <V> allPaths(start: V, end: V, neighbors: (V) -> List<V>): List<List<V>> {

        val searchPaths = mutableListOf(listOf(start))
        val paths = mutableListOf<List<V>>()
        while (searchPaths.isNotEmpty()) {
            val searchPath = searchPaths.removeFirst()
            val terminus = searchPath.last()
            if (terminus == end) {
                paths.add(searchPath)
            }
            neighbors(terminus).filter { it !in searchPath }.forEach { neighbor ->
                searchPaths.add(searchPath + neighbor)
            }
        }
        return paths
    }

    fun <V> connectedComponents(vertices: Set<V>, neighbors: (V) -> List<V>): List<Set<V>> {
        val components = mutableListOf<Set<V>>()
        val visited = mutableSetOf<V>()
        vertices.forEach { vertex ->
            if (vertex !in visited) {
                val component = reachable(vertex, neighbors = neighbors)
                visited += component
                components.add(component)
            }
        }
        return components
    }

    private fun <V> traverse(
        start: V,
        neighbors: (V) -> List<V>,
        append: MutableList<V>.(V) -> Unit
    ): List<V> {
        val visited = mutableSetOf(start)
        val vertices = mutableListOf(start)
        val traversal = mutableListOf<V>()
        while (vertices.isNotEmpty()) {
            val vertex = vertices.removeFirst()
            traversal.add(vertex)
            neighbors(vertex).filter { it !in visited }.forEach { neighbor ->
                visited += neighbor
                vertices.append(neighbor)
            }
        }
        return traversal
    }

    private fun <V> search(
        start: V,
        end: V,
        neighbors: (V) -> List<V>,
        append: MutableList<List<V>>.(List<V>) -> Unit
    ): List<V> {
        val visited = mutableSetOf<V>()
        visited += start
        val paths = mutableListOf(listOf(start))
        while (paths.isNotEmpty()) {
            val path = paths.removeFirst()
            val terminus = path.last()
            if (terminus == end) {
                return path
            }
            neighbors(terminus).filter { it !in visited }.forEach { neighbor ->
                visited += neighbor
                paths.append(path + neighbor)
            }
        }
        return listOf()
    }

    fun String.parseGraph(): Graph<String, String> {
        val regex = Regex("""\s*(\S+)\s*--\s*(\S+)\[(\d+\.\d+|\d+)]\s*-->\s*(\S+)\s*""")
        val graph = SparseGraph<String, String>()
        lines().forEach { line ->
            regex.matchEntire(line)?.destructured?.let { (from, edge, weight, to) ->
                graph.addVertex(from)
                graph.addVertex(to)
                graph.addEdge(from, to, edge, weight.toDouble())
            }
        }
        return graph
    }
}
