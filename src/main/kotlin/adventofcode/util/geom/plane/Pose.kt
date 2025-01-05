/*
 * Copyright (c) 2025 James Carman
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

data class Pose(val position: Point2D, val orientation: Direction) {
    fun turnRight() = copy(orientation = orientation.turnRight())
    fun turnLeft() = copy(orientation = orientation.turnLeft())
    fun turnAround() = copy(orientation = orientation.turnAround())
    fun forward() = copy(position = position + orientation)
    fun backward() = copy(position = position - orientation)
}