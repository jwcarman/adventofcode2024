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
import adventofcode.util.lcmInt
import adventofcode.util.occurrences
import adventofcode.util.removeAll
import adventofcode.util.splitsAsInt
import kotlin.math.ln


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

fun List<Point2D>.calculateShannonEntropy(maxX: Int, maxY: Int, gridWidth: Int, gridHeight: Int): Double {
    val cellWidth = maxX.toDouble() / gridWidth
    val cellHeight = maxY.toDouble() / gridHeight
    val histogram = this.groupingBy { point ->
        val cellX = (point.x / cellWidth).toInt()
        val cellY = (point.y / cellHeight).toInt()
        cellX to cellY
    }.eachCount()

    val totalPoints = size.toDouble()
    return histogram.values
        .asSequence()
        .map { it / totalPoints }
        .sumOf { -it * ln(it) }
}

private const val GRID_WIDTH = 10
private const val GRID_HEIGHT = 10

fun String.findTree(): Int {
    val robots = parseRobots()
    val maxX = robots.maxOf { it.ray.origin.x } + 1
    val maxY = robots.maxOf { it.ray.origin.y } + 1
    val sequences = robots.map { it.points(maxX, maxY) }
    val minEntropyT = (1..lcmInt(maxX, maxY)).minBy { _ ->
        sequences.map { it.next() }.calculateShannonEntropy(maxX, maxY, GRID_WIDTH, GRID_HEIGHT)
    }
    return minEntropyT
}