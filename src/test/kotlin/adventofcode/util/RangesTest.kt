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

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class RangesTest {
    @Test
    fun intersectWithEmptyRangeShouldReturnEmpty() {
        val range = 1L..10L
        range.intersection(LongRange.EMPTY) shouldBe LongRange.EMPTY
    }

    @Test
    fun intersectWithSelfShouldReturnSelf() {
        val range = 1L..10L
        range.intersection(range) shouldBe range
    }

    @Test
    fun intersectWithSuperRangeShouldReturnSelf() {
        val range = 1L..10L
        range.intersection(0L..11L) shouldBe range
        range.intersection(1L..11L) shouldBe range
        range.intersection(-10L..10L) shouldBe range
    }

    @Test
    fun intersectWithSubRangeShouldReturnSubRange() {
        val range = 1L..10L
        range.intersection(2L..9L) shouldBe 2L..9L
        range.intersection(1L..5L) shouldBe 1L..5L
        range.intersection(5L..10L) shouldBe 5L..10L
    }

    @Test
    fun intersectWithOverlappingTailRangeShouldOnlySuffix() {
        val range = 10L..20L
        range.intersection(15L..25L) shouldBe 15L..20L
    }

    @Test
    fun intersectWithOverlappingHeadRangeShouldOnlyPrefix() {
        val range = 10L..20L
        range.intersection(5L..15L) shouldBe 10L..15L
    }

    @Test
    fun translateShouldAddOffsetToRange() {
        val range = 10L..20L
        range.translate(5L) shouldBe 15L..25L
    }

    @Test
    fun subtractingEmptyRangeShouldReturnSelf() {
        val range = 1L..10L
        range - LongRange.EMPTY shouldBe listOf(range)
    }

    @Test
    fun subtractingSelfShouldReturnEmpty() {
        val range = 1L..10L
        range - range shouldBe emptyList()
    }

    @Test
    fun subtractingSuperRangeShouldReturnEmpty() {
        val range = 1L..10L
        range - (0L..11L) shouldBe emptyList()
    }

    @Test
    fun subtractingSubRangeShouldReturnHeadAndTail() {
        val range = 1L..10L
        range - (2L..9L) shouldBe listOf(1L..1L, 10L..10L)
    }

    @Test
    fun subtractingOverlappingTailRangeShouldReturnRemainingHead() {
        val range = 10L..20L
        range - (15L..25L) shouldBe listOf(10L..14L)
    }

    @Test
    fun subtractingOverlappingHeadRangeShouldReturnRemainingTail() {
        val range = 10L..20L
        range - (5L..15L) shouldBe listOf(16L..20L)
    }

    @Test
    fun subtractingHeadShouldReturnRemainingTail() {
        val range = 10L..20L
        range - (10L..15L) shouldBe listOf(16L..20L)
    }

    @Test
    fun subtractingTailShouldReturnRemainingHead() {
        val range = 10L..20L
        range - (15L..20L) shouldBe listOf(10L..14L)
    }

    @Test
    fun subtractingRangeAfterTailReturnsSelf() {
        val range = 10L..20L
        range - (30L..40L) shouldBe listOf(range)
    }

    @Test
    fun subtractingRangeBeforeHeadReturnsSelf() {
        val range = 10L..20L
        range - (0L..5L) shouldBe listOf(range)
    }

    @Test
    fun removeAllShouldReturnRemainingRanges() {
        val range = 1L..100L
        val others = listOf(1L..10L, 50L..75L, 90L..97L)
        val diff = range.removeAll(others)
        diff shouldBe listOf(11L..49L, 76L..89L, 98L..100L)
    }

    @Test
    fun removingCompleteSubrangesShouldReturnEmpty() {
        val range = 1L..100L
        val others = listOf(1L..50L, 51L..100L)
        val diff = range.removeAll(others)
        diff shouldBe emptyList()
    }

    @Test
    fun removingAllSubrangesAtHeadShouldReturnRemainingTail() {
        val range = 1L..100L
        val others = listOf(1L..50L, 51L..60L)
        val diff = range.removeAll(others)
        diff shouldBe listOf(61L..100L)
    }

    @Test
    fun removingAllSubrangesAtTailShouldReturnRemainingHead() {
        val range = 1L..100L
        val others = listOf(40L..50L, 51L..100L)
        val diff = range.removeAll(others)
        diff shouldBe listOf(1L..39L)
    }

    @Test
    fun removingAllSubrangesAtHeadAndTailShouldReturnRemainingMiddle() {
        val range = 1L..100L
        val others = listOf(1L..60L, 90L..100L)
        val diff = range.removeAll(others)
        diff shouldBe listOf(61L..89L)
    }

    @Test
    fun removingAllNonOverlappingSubrangesShouldReturnSelf() {
        val range = 1L..100L
        val others = listOf(101L..200L, 201L..300L)
        val diff = range.removeAll(others)
        diff shouldBe listOf(range)
    }
}
