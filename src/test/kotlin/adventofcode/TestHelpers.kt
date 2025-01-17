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

import adventofcode.util.readAsString


private fun Any.resourcePrefix() = this::class.java.simpleName.substring(0, 5).lowercase()

fun Any.readExample1() = readAsString("${resourcePrefix()}-example1.txt")
fun Any.readExample2() = readAsString("${resourcePrefix()}-example2.txt")
fun Any.readInput() = readAsString("${resourcePrefix()}.txt")