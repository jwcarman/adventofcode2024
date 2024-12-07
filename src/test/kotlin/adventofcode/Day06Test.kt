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

import adventofcode.day06.calculatePatrolPathLength
import adventofcode.day06.countObstacleLocationsThatCauseLoops
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day06Test {

    @Test
    fun example1() {
        calculatePart1(readExample1()) shouldBe 41
    }

    @Test
    fun part1() {
        calculatePart1(readInput()) shouldBe 5177
    }

    @Test
    fun example2() {
        calculatePart2(readExample2()) shouldBe 6
    }

    @Test
    fun part2() {
        calculatePart2(readInput()) shouldBe 1686
    }

    private fun calculatePart1(input: String): Int = input.calculatePatrolPathLength()

    private fun calculatePart2(input: String): Int = input.countObstacleLocationsThatCauseLoops()
}