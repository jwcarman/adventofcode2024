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

package adventofcode.util.grid

import adventofcode.util.geom.plane.Direction
import adventofcode.util.geom.plane.Point2D

data class Path<T : Any>(val grid: Grid<T>, val direction: Direction, val start: Point2D) {
    fun asSequence() = generateSequence(start) { it + direction }.takeWhile { it in grid }
    fun asValueSequence() = asSequence().map { grid[it] }
}