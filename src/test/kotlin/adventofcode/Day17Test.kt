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


import adventofcode.day17.determineProgramOutput
import adventofcode.day17.findLeastSelfReplicatingValueForA
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day17Test {

    @Test
    fun example1() {
        calculatePart1(readExample1()) shouldBe "4,6,3,5,6,3,5,2,1,0"
    }

    @Test
    fun part1() {
        calculatePart1(readInput()) shouldBe "7,3,5,7,5,7,4,3,0"
    }

    @Test
    fun example2() {
        calculatePart2(readExample2()) shouldBe 117440L
    }

    @Test
    fun part2() {
        calculatePart2(readInput()) shouldBe 105734774294938L
    }

    private fun calculatePart1(input: String): String = input.determineProgramOutput()

    private fun calculatePart2(input: String): Long = input.findLeastSelfReplicatingValueForA()
}