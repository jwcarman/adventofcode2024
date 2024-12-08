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

data class Ray(val origin: Point2D, val dx: Int, val dy: Int) {

    constructor(origin: Point2D, slope: Slope) : this(origin, slope.dx, slope.dy) {}
    constructor(origin: Point2D, other: Point2D) : this(origin, Slope(origin, other)) {}
    constructor(origin: Point2D, direction: Direction) : this(origin, direction.dx, direction.dy) {}

    fun points() = generateSequence(origin) { it.translate(dx, dy) }

}
