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

package adventofcode.day02

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class RedNosedReactorTest {

    @Test
    fun `7 6 4 2 1 is safe`() {
        val input = listOf(7, 6, 4, 2, 1)
        input.isSafe() shouldBe true
    }

    @Test
    fun `1 2 7 8 9 is not safe`() {
        val input = listOf(1, 2, 7, 8, 9)
        input.isSafe() shouldBe false
    }

    @Test
    fun `9 7 6 2 1 is not safe`() {
        val input = listOf(9, 7, 6, 2, 1)
        input.isSafe() shouldBe false
    }

    @Test
    fun `1 3 2 4 5 is not safe`() {
        val input = listOf(1, 3, 2, 4, 5)
        input.isSafe() shouldBe false
    }

    @Test
    fun `8 6 4 4 1 is not safe`() {
        val input = listOf(8, 6, 4, 4, 1)
        input.isSafe() shouldBe false
    }

    @Test
    fun `1 3 6 7 9 is safe`() {
        val input = listOf(1, 3, 6, 7, 9)
        input.isSafe() shouldBe true
    }


    @Test
    fun `9 5 is not gradually decreasing`() {
        val input = listOf(9, 5)
        input.isGraduallyDecreasing() shouldBe false
    }

    @Test
    fun `9 6 is gradually decreasing`() {
        val input = listOf(9, 6)
        input.isGraduallyDecreasing() shouldBe true
    }

    @Test
    fun `9 7 is gradually decreasing`() {
        val input = listOf(9, 7)
        input.isGraduallyDecreasing() shouldBe true
    }

    @Test
    fun `9 8 is gradually decreasing`() {
        val input = listOf(9, 8)
        input.isGraduallyDecreasing() shouldBe true
    }

    @Test
    fun `9 9 is not gradually decreasing`() {
        val input = listOf(9, 9)
        input.isGraduallyDecreasing() shouldBe false
    }

    @Test
    fun `1 1 is not gradually increasing`() {
        val input = listOf(1, 1)
        input.isGraduallyIncreasing() shouldBe false
    }

    @Test
    fun `1 2 is gradually increasing`() {
        val input = listOf(1, 2)
        input.isGraduallyIncreasing() shouldBe true
    }

    @Test
    fun `1 3 is gradually increasing`() {
        val input = listOf(1, 3)
        input.isGraduallyIncreasing() shouldBe true
    }

    @Test
    fun `1 4 is gradually increasing`() {
        val input = listOf(1, 4)
        input.isGraduallyIncreasing() shouldBe true
    }

    @Test
    fun `1 5 is not gradually increasing`() {
        val input = listOf(1, 5)
        input.isGraduallyIncreasing() shouldBe false
    }


    @Test
    fun `1 2 7 8 9 is unsafe with dampening`() {
        val input = listOf(1, 2, 7, 8, 9)
        input.isSafeDampened() shouldBe false
    }

    @Test
    fun `9 7 6 2 1 is unsafe with dampening`() {
        val input = listOf(9, 7, 6, 2, 1)
        input.isSafeDampened() shouldBe false
    }

    @Test
    fun `1 3 2 4 5 is safe with dampening`() {
        val input = listOf(1, 3, 2, 4, 5)
        input.isSafeDampened() shouldBe true
    }

    @Test
    fun `8 6 4 4 1 is safe with dampening`() {
        val input = listOf(8, 6, 4, 4, 1)
        input.isSafeDampened() shouldBe true
    }

    @Test
    fun `1 3 6 7 9 is safe with dampening`() {
        val input = listOf(1, 3, 6, 7, 9)
        input.isSafeDampened() shouldBe true
    }

    @Test
    fun `1 2 3 4 should dampened should yield correct sublist variants`() {
        val input = listOf(1, 2, 3, 4)
        input.dampened() shouldBe listOf(
            listOf(2, 3, 4),
            listOf(1, 3, 4),
            listOf(1, 2, 4),
            listOf(1, 2, 3)
        )
    }

}