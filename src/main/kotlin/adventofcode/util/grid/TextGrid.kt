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

package adventofcode.util.grid

fun List<String>.toGrid(padChar: Char = ' ') = TextGrid(this, padChar)
class TextGrid(lines: List<String>, padChar: Char = ' ') : AbstractGrid<Char>() {

    private var lines: MutableList<String>
    private val width: Int = lines.maxOf { it.length }

    init {
        this.lines = lines.map { it.padEnd(width, padChar) }.toMutableList()
    }

    override fun getImpl(x: Int, y: Int) = lines[y][x]

    override fun setImpl(x: Int, y: Int, value: Char) {
        lines[y] = lines[y].replaceRange(x, x + 1, value.toString())
    }

    override fun width() = width
    override fun height() = lines.size
    override fun rowAt(y: Int) = lines[y].asSequence()
    override fun columnAt(x: Int) = lines.map { it[x] }.asSequence()
    override fun toString() = lines.joinToString("\n")
}