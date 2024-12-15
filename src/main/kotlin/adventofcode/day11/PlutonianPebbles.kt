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

package adventofcode.day11

import adventofcode.util.splitsAsInt

data class Stone(val number: Long) {
    private fun evolve(): List<Stone> {
        return when {
            number == 0L -> listOf(Stone(1L))
            number.toString().length % 2 == 0 -> split()
            else -> return listOf(Stone(number * 2024))
        }
    }

    fun countDescendents(generations: Int, memory: MutableMap<Pair<Long, Int>, Long>): Long {
        val key = Pair(number, generations)
        if (memory.containsKey(key)) {
            return memory.getValue(key)
        }
        if (generations == 0) {
            memory[Pair(number, generations)] = 1
            return 1
        }
        val count = evolve().sumOf { it.countDescendents(generations - 1, memory) }
        memory[key] = count
        return count
    }

    private fun split(): List<Stone> {
        val numberAsString = number.toString()
        val mid = numberAsString.length / 2
        return listOf(Stone(numberAsString.substring(0, mid).toLong()), Stone(numberAsString.substring(mid).toLong()))
    }
}

fun String.countStonesAfterBlinks(blinks: Int) = parseStones().sumOf { it.countDescendents(blinks, mutableMapOf()) }
fun String.parseStones() = splitsAsInt().map { Stone(it.toLong()) }