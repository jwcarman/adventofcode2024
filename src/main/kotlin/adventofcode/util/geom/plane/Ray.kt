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

package adventofcode.util.geom.plane

data class Ray(val origin: Point2D, val direction: Direction) {

    fun points() = generateSequence(origin) { it + direction }

    fun intersects(point: Point2D) = when(direction) {
        Direction.EAST-> point.y == origin.y && point.x >= origin.x
        Direction.WEST -> point.y == origin.y && point.x <= origin.x
        Direction.NORTH -> point.x == origin.x && point.y <= origin.y
        else -> point.x == origin.x && point.y >= origin.y
    }

    fun intersects(other: Ray): Boolean = when(direction) {
        Direction.EAST -> other.origin.x >= origin.x && other.origin.y == origin.y
        Direction.WEST -> other.origin.x <= origin.x && other.origin.y == origin.y
        Direction.NORTH -> other.origin.y <= origin.y && other.origin.x == origin.x
        else -> other.origin.y >= origin.y && other.origin.x == origin.x
    }
}
