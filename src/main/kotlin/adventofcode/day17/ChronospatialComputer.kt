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

package adventofcode.day17

import adventofcode.util.splitByEmptyLines
import adventofcode.util.splitsAs
import kotlin.math.pow

fun Long.pow(exponent: Long) = this.toDouble().pow(exponent.toDouble()).toInt()

fun String.parseComputer(): Computer {
    val parts = splitByEmptyLines()
    val (a, b, c) = parts[0].lines().map { it.substringAfter(':').trim().toLong() }
    val program = parts[1].substringAfter(':').trim().replace(',', ' ').splitsAs { it.toLong() }
    return Computer(a, b, c, program)
}

fun String.determineProgramOutput(): String {
    val computer = parseComputer()
    val output = computer.runProgram()
    return output.joinToString(separator = ",")
}

fun String.findLeastSelfReplicatingValueForA() = generatorsOf(this, parseComputer().program).first()

private fun generatorsOf(input: String, output: List<Long>): List<Long> {
    if(output.size == 1) {
        return (0L..7L).filter { input.parseComputer().initializeARegister(it).runProgram() == output }
    }
    val tailGenerators = generatorsOf(input, output.drop(1))
    return tailGenerators.flatMap { generator ->
        (0..7).map { generator * 8 + it }
            .filter { input.parseComputer().initializeARegister(it).runProgram() == output }
    }
}

class Computer(
    private var a: Long = 0,
    private var b: Long = 0,
    private var c: Long = 0,
    val program: List<Long>
) {
    private var instructionPoInter: Int = 0

    fun initializeARegister(value: Long): Computer {
        a = value
        return this
    }

    private fun operand() = program[instructionPoInter + 1]

    private fun literal(): Long = operand()

    private fun combo(): Long = when (operand()) {
        4L -> a
        5L -> b
        6L -> c
        else -> literal()
    }

    fun runProgram(): List<Long> {
        val output = mutableListOf<Long>()
        while (instructionPoInter < program.size) {
            val currentInstructionPoInter = instructionPoInter
            when (program[instructionPoInter]) {
                0L -> a = a / 2L.pow(combo())
                1L -> b = b xor literal()
                2L -> b = combo() % 8
                3L -> if (a != 0L) instructionPoInter = literal().toInt()
                4L -> b = b xor c
                5L -> output += combo() % 8
                6L -> b = a / 2L.pow(combo())
                7L -> c = a / 2L.pow(combo())
                else -> throw IllegalArgumentException("Unknown opcode: $program[$instructionPoInter]")
            }
            if (currentInstructionPoInter == instructionPoInter) {
                instructionPoInter += 2
            }
        }
        return output
    }

}