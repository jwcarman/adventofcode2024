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
    fun <V> shortestPaths(
        start: V,
        vertices: Set<V>,
        neighbors: (V) -> List<V>,
        weight: (V, V) -> Double
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
            visited.add(vertex)
            val distanceToVertex = dist.getOrDefault(vertex, Double.MAX_VALUE)
            neighbors(vertex).filter { it !in visited }.forEach { neighbor ->
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
}
