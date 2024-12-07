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

package adventofcode.day07

import adventofcode.util.head
import adventofcode.util.splitsAs
import adventofcode.util.tail



fun List<Long>.canYield(result: Long, vararg operators: Operator): Boolean {
    val rightOperand = head()
    if (size == 1) {
        return result == rightOperand
    }
    return operators.any { operator ->
        operator.canYield(result, rightOperand) && tail().canYield(operator.invert(result, rightOperand), *operators)
    }
}

interface Operator {
    fun canYield(result: Long, right: Long): Boolean
    fun invert(result: Long, right: Long): Long
}

class Addition : Operator {
    override fun canYield(result: Long, right: Long) = result >= right
    override fun invert(result: Long, right: Long) = result - right
}

class Multiplication : Operator {
    override fun canYield(result: Long, right: Long) = result % right == 0L
    override fun invert(result: Long, right: Long) = result / right
}

class Concatenation : Operator {
    override fun canYield(result: Long, right: Long) = result != right && result.toString().endsWith(right.toString())
    override fun invert(result: Long, right: Long) = result.toString().removeSuffix(right.toString()).toLong()
}

data class Equation(val testValue: Long, val operands: List<Long>) {
    fun isValidWith(vararg operators: Operator): Boolean {
        return operands.reversed().canYield(testValue, *operators)
    }
}

private const val COLON = ':'
fun String.parseEquation(): Equation {
    val testValue = substringBefore(COLON).toLong()
    val operands = substringAfter(COLON).trim().splitsAs { it.toLong() }
    return Equation(testValue, operands)
}