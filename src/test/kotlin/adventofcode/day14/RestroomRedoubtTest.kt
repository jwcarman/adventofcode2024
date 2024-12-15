package adventofcode.day14

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class RestroomRedoubtTest {
    @Test
    fun `robot should move prescribed velocity within bounds without wrapping`() {
        val robot = "p=0,0 v=1,3".parseRobot()
        val point = robot.move(3, 10, 10)
        point.x shouldBe 3
        point.y shouldBe 9
    }

    @Test
    fun `robot should wrap around the right side`() {
        val robot = "p=9,0 v=1,0".parseRobot()
        val point = robot.move(1, 10, 10)
        point.x shouldBe 0
        point.y shouldBe 0
    }

    @Test
    fun `robot should wrap around the left side`() {
        val robot = "p=0,0 v=-1,0".parseRobot()
        val point = robot.move(1, 10, 10)
        point.x shouldBe 9
        point.y shouldBe 0
    }

    @Test
    fun `robot should wrap around the bottom side`() {
        val robot = "p=0,9 v=0,1".parseRobot()
        val point = robot.move(1, 10, 10)
        point.x shouldBe 0
        point.y shouldBe 0
    }

    @Test
    fun `robot should wrap around the top side`() {
        val robot = "p=0,0 v=0,-1".parseRobot()
        val point = robot.move(1, 10, 10)
        point.x shouldBe 0
        point.y shouldBe 9
    }

    @Test
    fun `robot should wrap multiple times`() {
        val robot = "p=0,0 v=1,1".parseRobot()
        val point = robot.move(100, 10, 10)
        point.x shouldBe 0
        point.y shouldBe 0
    }

}