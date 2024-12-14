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

import adventofcode.day13.parseClawMachines
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day13Test {

    @Test
    fun example1() {
        calculatePart1(readExample1()) shouldBe 480L
    }

    @Test
    fun part1() {
        calculatePart1(readInput()) shouldBe 31761L
    }

    @Test
    fun example2() {
        calculatePart2(readExample2()) shouldBe 875318608908L
    }

    @Test
    fun part2() {
        calculatePart2(readInput()) shouldBe 90798500745591L
    }

    private fun calculatePart1(input: String): Long = input.parseClawMachines()
        .map { it.calculateTokensRequired() }
        .filter { it != -1L }
        .sum()

    private fun calculatePart2(input: String): Long = input.parseClawMachines()
        .map { it.copy(x = it.x + 10000000000000, y = it.y + 10000000000000) }
        .map { it.calculateTokensRequired() }
        .filter { it != -1L }
        .sum()
}