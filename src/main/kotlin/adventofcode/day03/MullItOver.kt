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

package adventofcode.day03

import adventofcode.util.removeWhitespace
import adventofcode.util.splitsAsInt

fun String.evaluateProgram() = """mul\(\d+,\d+\)""".toRegex().findAll(this)
    .map { it.value }
    .map { it.substring(4, it.length - 1) }
    .map { it.replace(',', ' ') }
    .map { it.splitsAsInt() }
    .map { it[0] * it[1] }
    .sum()

fun String.removeDisabledInstructions() = "${removeWhitespace()}do()".replace("don't\\(\\).*?do\\(\\)".toRegex(), "")