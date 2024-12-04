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

package adventofcode.day04

import adventofcode.util.wordsearch.WordSearch

fun String.countXmas() = WordSearch(this).searchForWord("XMAS").count()

fun String.countMasX() = WordSearch(this).searchForWord("MAS")
    .filter { it.direction.isDiagonal() }
    .groupBy { it.points[1] }
    .count{it.value.size == 2}