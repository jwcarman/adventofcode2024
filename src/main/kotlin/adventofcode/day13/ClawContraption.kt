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

package adventofcode.day13

import adventofcode.util.removeAll
import adventofcode.util.splitsAsInt

data class ClawMachine(val x1: Long, val y1: Long, val x2: Long, val y2: Long, val x: Long, val y: Long) {

    private fun calculateA(): Long {
        val numerator = y * x2 - x * y2
        val denominator = x2 * y1 - x1 * y2
        return if(numerator % denominator == 0L) numerator / denominator else -1
    }

    private fun calculateB(): Long {
        val numerator = y * x1 - x * y1
        val denominator = x1*y2 - x2 * y1
        return if(numerator % denominator == 0L) numerator / denominator else -1
    }

    fun calculateTokensRequired():Long {
        val a = calculateA()
        val b = calculateB()
        if(a == -1L || b == -1L) return -1L
        return 3L * a + b
    }
}

fun String.parseClawMachine(): ClawMachine {
    val lines = lines()
    val (x1, y1) = lines[0].removeAll("Button A: X\\+", ",", "Y\\+").splitsAsInt().map { it.toLong() }
    val (x2, y2) = lines[1].removeAll("Button B: X\\+", ",", "Y\\+").splitsAsInt().map { it.toLong() }
    val (x, y) = lines[2].removeAll("Prize: X=", ",", "Y=").splitsAsInt().map { it.toLong() }
    return ClawMachine(x1, y1, x2, y2, x, y)
}

fun String.parseClawMachines(): List<ClawMachine> = split("\n\n").map { it.parseClawMachine() }

fun String.calculateTokensRequiredToWinAllPossiblePrizes(): Long {
    val machines = parseClawMachines()
    return machines
        .map { it.calculateTokensRequired() }
        .filter { it != -1L }
        .sum()
}
