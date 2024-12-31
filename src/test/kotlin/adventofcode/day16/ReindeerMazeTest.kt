package adventofcode.day16

import adventofcode.util.geom.plane.Direction
import adventofcode.util.geom.plane.Point2D
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ReindeerMazeTest {

    @Test
    fun `path containing one item should have score zero`() {
        val path = listOf(State(Point2D.origin(), Direction.EAST))
        path.score() shouldBe 0
    }

    @Test
    fun `path with only a left turn should have score 1000`() {
        val path = listOf(
            State(Point2D.origin(), Direction.EAST),
            State(Point2D.origin(), Direction.NORTH)
        )
        path.score() shouldBe 1000
    }

    @Test
    fun `path with only a right turn should have score 1000`() {
        val path = listOf(
            State(Point2D.origin(), Direction.EAST),
            State(Point2D.origin(), Direction.SOUTH)
        )
        path.score() shouldBe 1000
    }

    @Test
    fun `path with only a forward move should have score 1`() {
        val path = listOf(
            State(Point2D.origin(), Direction.EAST),
            State(Point2D(1, 0), Direction.EAST)
        )
        path.score() shouldBe 1
    }

    @Test
    fun `path with multiple moves should have correct score`() {
        val path = listOf(
            State(Point2D.origin(), Direction.NORTH),
            State(Point2D.origin(), Direction.EAST),
            State(Point2D(1, 0), Direction.EAST),
            State(Point2D(2, 0), Direction.EAST),
            State(Point2D(3, 0), Direction.EAST),
            State(Point2D(3,0), Direction.SOUTH),
        )
        path.score() shouldBe 2003
    }

}