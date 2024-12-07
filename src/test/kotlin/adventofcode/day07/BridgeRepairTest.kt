package adventofcode.day07

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BridgeRepairTest {

    @Test
    fun `should parse equation from String`() {
        val equation = "161011: 16 10 13".parseEquation()
        assertThat(equation.testValue).isEqualTo(161011)
        assertThat(equation.operands).containsExactly(16, 10, 13)
    }

    @Test
    fun `addition can yield result gte right operand`() {
        val add = Addition()
        assertThat(add.canYield(10, 5)).isTrue()
        assertThat(add.canYield(5, 5)).isTrue()
        assertThat(add.canYield(5, 10)).isFalse()
    }

    @Test
    fun `addition inverts to subtraction`() {
        val add = Addition()
        assertThat(add.invert(10, 5)).isEqualTo(10 - 5)
        assertThat(add.invert(5, 5)).isEqualTo(5 - 5)
    }

    @Test
    fun `multiplication can only yield results that are multiples of right operand`() {
        val mult = Multiplication()
        assertThat(mult.canYield(10, 5)).isTrue()
        assertThat(mult.canYield(5, 5)).isTrue()
        assertThat(mult.canYield(5, 10)).isFalse()
        assertThat(mult.canYield(5, 3)).isFalse()
    }

    @Test
    fun `multiplication inverts to division`() {
        val mult = Multiplication()
        assertThat(mult.invert(10, 5)).isEqualTo(10 / 5)
        assertThat(mult.invert(5, 5)).isEqualTo(5 / 5)
    }

    @Test
    fun `concatenation can only yield results that end with right operand`() {
        val concat = Concatenation()
        assertThat(concat.canYield(10, 0)).isTrue()
        assertThat(concat.canYield(10, 1)).isFalse()
        assertThat(concat.canYield(10, 10)).isFalse()
    }

    @Test
    fun `concatenation inverts to removal of right operand`() {
        val concat = Concatenation()
        assertThat(concat.invert(10, 0)).isEqualTo(1)
        assertThat(concat.invert(1234, 34)).isEqualTo(12)
    }

}