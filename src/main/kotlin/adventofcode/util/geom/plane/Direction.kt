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

enum class Direction(val dx: Int, val dy: Int) {
    NORTH(0, -1),
    SOUTH(0, 1),
    EAST(1, 0),
    WEST(-1, 0),
    NORTHEAST(1, -1),
    SOUTHEAST(1, 1),
    SOUTHWEST(-1, 1),
    NORTHWEST(-1, -1);

    fun isDiagonal() = dx != 0 && dy != 0

    fun turnRight() = when(this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
        NORTHEAST -> SOUTHEAST
        SOUTHEAST -> SOUTHWEST
        SOUTHWEST -> NORTHWEST
        NORTHWEST -> NORTHEAST
    }

    fun turnLeft() = when(this) {
        NORTH -> WEST
        WEST -> SOUTH
        SOUTH -> EAST
        EAST -> NORTH
        NORTHEAST -> NORTHWEST
        NORTHWEST -> SOUTHWEST
        SOUTHWEST -> SOUTHEAST
        SOUTHEAST -> NORTHEAST
    }

    fun turnAround() = when(this) {
        NORTH -> SOUTH
        SOUTH -> NORTH
        EAST -> WEST
        WEST -> EAST
        NORTHEAST -> SOUTHWEST
        SOUTHWEST -> NORTHEAST
        SOUTHEAST -> NORTHWEST
        NORTHWEST -> SOUTHEAST
    }
}