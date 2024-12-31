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

import adventofcode.util.geom.plane.Point2D
import adventofcode.util.graph.Graphs.parseGraph
import io.kotest.matchers.shouldBe
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GraphsTest {
    @Test
    fun reachableWithZeroMaxDepthReturnsOriginal() {
        assertThat(Graphs.reachable(Point2D.origin(), 0, Point2D::neighbors)).containsExactly(Point2D.origin())
    }

    @Test
    fun reachableStopsAtMaxDepth() {
        assertThat(Graphs.reachable(Point2D.origin(), 1, Point2D::neighbors)).hasSize(5)
    }

    @Test
    fun reachableStopsWhenAllVisited() {
        val searchSpace = setOf(
            Point2D.origin(),
            Point2D(1, 0),
            Point2D(0, 1),
            Point2D(-1, 0),
            Point2D(0, -1)
        )
        assertThat(Graphs.reachable(Point2D.origin()) { it.neighbors().filter { n -> n in searchSpace } }).hasSize(5)
    }

    @Test
    fun `shortest paths sequence should be empty when there are no paths`() {
        val start = Point2D.origin()
        val end = Point2D(1, 1)
        val vertices = setOf(start, end)

        val neighborsFn: (Point2D) -> List<Point2D> = { _ -> emptyList() }
        val weightFn: (Point2D, Point2D) -> Double = { _, _ -> 1.0 }
        Graphs.shortestPaths(start, end, vertices, neighborsFn, weightFn).toList() shouldBe emptyList()
    }

    @Test
    fun `dijkstra shortest path should return absolute shortest path`() {
        val graph = SparseGraph<Char, Double>()
        graph.addVertex('C')
        graph.addVertex('D')
        graph.addVertex('E')
        graph.addVertex('F')
        graph.addVertex('G')
        graph.addVertex('H')

        graph.addEdge('C', 'D', 1.0, 3.0)
        graph.addEdge('C', 'E', 1.0, 2.0)
        graph.addEdge('E', 'D', 1.0, 1.0)
        graph.addEdge('E', 'F', 1.0, 2.0)
        graph.addEdge('E', 'G', 1.0, 3.0)
        graph.addEdge('D', 'F', 1.0, 4.0)
        graph.addEdge('F', 'H', 1.0, 1.0)
        graph.addEdge('F', 'G', 1.0, 2.0)
        graph.addEdge('G', 'H', 1.0, 2.0)

        val shortestPaths = graph.shortestPaths('C')
        shortestPaths.pathExists('H') shouldBe true
        shortestPaths.pathTo('H') shouldBe listOf('C', 'E', 'F', 'H')
        shortestPaths.distanceTo('H') shouldBe 5.0

    }

    @Test
    fun `parsing graph from text should create correct graph`() {
        val graph = """
            A -- e1[1.0] --> B
            B -- e2[1.0] --> C
            A -- e3[5.0] --> D
            D -- e4[5.0] --> C
            """.trimIndent().parseGraph()
        graph.vertices() shouldBe setOf("A", "B", "C", "D")
        graph.edge("A", "B") shouldBe Edge("e1", 1.0)
        graph.edge("B", "C") shouldBe Edge("e2", 1.0)
        graph.edge("A", "D") shouldBe Edge("e3", 5.0)
        graph.edge("D", "C") shouldBe Edge("e4", 5.0)
    }

    @Test
    fun `shortest path sequence should terminate after finding all paths`() {
        val graph = """
            A -- e1[1.0] --> B
            B -- e2[1.0] --> C
            A -- e3[5.0] --> D
            D -- e4[5.0] --> C
            """.trimIndent().parseGraph()
        val shortestPaths = graph.shortestPaths("A", "C").toList()
        shortestPaths shouldBe listOf(
            listOf("A", "B", "C"),
            listOf("A", "D", "C")
        )
    }

}