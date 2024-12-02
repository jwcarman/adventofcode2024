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

package adventofcode.util

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ListsTest {
    @Test
    fun `1 2 3 withoutIndex 0 should be 2 3`() {
        val input = listOf(1, 2, 3)
        input.withoutIndex(0) shouldBe listOf(2, 3)
    }

    @Test
    fun `1 2 3 withoutIndex 1 should be 1 3`() {
        val input = listOf(1, 2, 3)
        input.withoutIndex(1) shouldBe listOf(1, 3)
    }

    @Test
    fun `1 2 3 withoutIndex 2 should be 1 2`() {
        val input = listOf(1, 2, 3)
        input.withoutIndex(2) shouldBe listOf(1, 2)
    }

    @Test
    fun `1 2 3 withoutIndex -1 should be 1 2 3`() {
        val input = listOf(1, 2, 3)
        input.withoutIndex(-1) shouldBe listOf(1, 2, 3)
    }

    @Test
    fun `1 2 3 withoutIndex 3 should be 1 2 3`() {
        val input = listOf(1, 2, 3)
        input.withoutIndex(3) shouldBe listOf(1, 2, 3)
    }
}