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

package adventofcode.day19

import adventofcode.util.splitByEmptyLines
import adventofcode.util.trie.Trie

fun String.parseTowelsAndPatterns(): Pair<Trie, List<String>> {
    val (towelInput, patternInput) = splitByEmptyLines()
    val towels = towelInput.split(", ").toSet()
    val patterns = patternInput.lines()
    val trie = Trie()
    towels.forEach { trie.insert(it) }
    return trie to patterns
}

fun String.countPossibleDesigns(): Int {
    val (trie, patterns) = parseTowelsAndPatterns()
    val memory = mutableMapOf<String, Long>()
    return patterns.count { countPossibleCombos(it, trie, memory) > 0L }
}

fun String.countDesignCombos(): Long {
    val (trie, patterns) = parseTowelsAndPatterns()
    val memory = mutableMapOf<String, Long>()
    return patterns.sumOf { pattern -> countPossibleCombos(pattern, trie, memory) }
}

fun countPossibleCombos(pattern: String, trie: Trie, memory: MutableMap<String, Long>): Long {
    return memory.getOrPut(pattern) {
        trie.prefixesOf(pattern)
            .sumOf { if (it == pattern) 1L else countPossibleCombos(pattern.removePrefix(it), trie, memory) }
    }
}