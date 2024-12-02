/*
 * Copyright (c) 2023 James Carman
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

package adventofcode.util

fun String.removeAll(vararg strings: String): String {
    return strings.fold(this) { acc, s -> acc.replace(Regex(s), "") }
}

private val whitespace = Regex("\\s+")
fun String.splitByWhitespace(): List<String> {
    return split(whitespace)
}

fun String.removeWhitespace(): String {
    return replace(whitespace, "")
}

fun String.isNumeric(): Boolean = all { char -> char.isDigit() }

fun <T> String.parseSplit(splitNdx: Int, parser: (String) -> T): T = parser(splitByWhitespace()[splitNdx])

fun String.parseSplit(splitNdx: Int): String = parseSplit(splitNdx) { it }
fun String.parseIntSplit(splitNdx: Int): Int = parseSplit(splitNdx) { it.toInt() }

fun <T> List<String>.parseSplits(splitNdx: Int, parser: (String) -> T): List<T> {
    return map { it.parseSplit(splitNdx, parser) }
}

fun List<String>.parseIntSplits(splitNdx: Int): List<Int> = parseSplits(splitNdx) { it.toInt() }
fun List<String>.parseSplits(splitNdx: Int): List<String> = parseSplits(splitNdx) { it }

fun <T> String.splitsAs(parser: (String) -> T): List<T> {
    return splitByWhitespace().map(parser)
}

fun String.splitsAsInt(): List<Int> = splitsAs { it.toInt() }

