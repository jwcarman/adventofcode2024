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

package adventofcode

import adventofcode.day18.findFirstByteThatBlocksExit
import adventofcode.day18.findShortestPathToExitAfter
import adventofcode.util.geom.plane.Point2D
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day18Test {

    @Test
    fun example1() {
        calculatePart1(readExample1(), 12) shouldBe 22
    }

    @Test
    fun part1() {
        calculatePart1(readInput(), 1024) shouldBe 326
    }

    @Test
    fun example2() {
        calculatePart2(readExample2()) shouldBe Point2D(6,1)
    }

    @Test
    fun part2() {
        calculatePart2(readInput()) shouldBe Point2D(18,62)
    }

    private fun calculatePart1(input: String, bytes:Int): Int = input.findShortestPathToExitAfter(bytes)

    private fun calculatePart2(input: String): Point2D = input.findFirstByteThatBlocksExit()
}