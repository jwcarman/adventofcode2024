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

package adventofcode.day14

import adventofcode.util.geom.plane.Point2D
import adventofcode.util.geom.plane.Ray
import adventofcode.util.grid.TextGrid
import adventofcode.util.lcmInt
import adventofcode.util.occurrences
import adventofcode.util.removeAll
import adventofcode.util.splitsAsInt


data class Robot(val ray: Ray) {
    fun move(t: Int, maxX: Int, maxY: Int) = ray.points()
        .drop(1)
        .take(t)
        .last()
        .let { (x, y) -> Point2D(x.mod(maxX), y.mod(maxY)) }

    fun points(maxX: Int, maxY: Int) = ray.points().drop(1).map { Point2D(it.x.mod(maxX), it.y.mod(maxY)) }.iterator()
}

fun String.parseRobot() = removeAll("p=", "v=").replace(",", " ").splitsAsInt().let { (x, y, dx, dy) ->
    Robot(
        Ray(
            Point2D(x, y), dx, dy
        )
    )
}

fun String.parseRobots() = lines().map { it.parseRobot() }

fun List<Point2D>.safetyFactor(maxX: Int, maxY: Int): Int {
    val midX = maxX / 2
    val midY = maxY / 2
    return filter { it.x != midX && it.y != midY }
        .map { (x, y) ->
        when {
            x < midX && y < midY -> "1"
            x > midX && y < midY -> "2"
            x < midX && y > midY -> "3"
            else -> "4"
        }
    }
        .occurrences()
        .values.reduce(Int::times)
}

fun String.calculateSafetyFactorAt(t: Int): Int {
    val robots = parseRobots()
    val maxX = robots.maxOf { it.ray.origin.x } + 1
    val maxY = robots.maxOf { it.ray.origin.y } + 1
    return robots.map { it.move(t, maxX, maxY) }.safetyFactor(maxX, maxY)
}

fun Set<Point2D>.printGrid(maxX: Int, maxY: Int) {
    val grid = TextGrid(".".repeat(maxX * maxY).chunked(maxX))
    forEach { (x, y) ->
        grid[x, y] = '#'
    }
    println(grid)
}

fun String.findTree(): Long {
    val robots = parseRobots()
    val maxX = robots.maxOf { it.ray.origin.x } + 1
    val maxY = robots.maxOf { it.ray.origin.y } + 1

    val sequences = robots.map { it.points(maxX, maxY) }.toList()

    var t = 1
    val maxT = lcmInt(maxX, maxY)
    var maxCount = 0
    while (t <= maxT) {
        val points = sequences.map { it.next() }.toSet()

        val count = points.count { p -> setOf(p.northEast(), p.northWest(), p.southEast(), p.southWest()).any { it in points } }
        if(count > maxCount) {
            println("t = ${t}")
            points.printGrid(maxX, maxY)
            println()
            maxCount = count
        }
        //if(points.groupBy { it.y }.values.any{it.size > 3}) {

        //}
//        while (points.size >= tree.size) {
//            if (points.containsAll(tree)) {
//                return t
//            }
//            points = points.filter { it.y > 0 }.map { it.north() }.toSet()
//        }
        t++
    }
    return -1L
}