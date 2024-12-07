package adventofcode.util.geom.plane

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RayTest {
    @Test
    fun `should generate correct points sequence`() {
        val ray = Ray(Point2D(0, 0), Direction.EAST)
        val points = ray.points().take(3).toList()
        assertThat(points).containsExactly(Point2D(0, 0), Point2D(1, 0), Point2D(2, 0))
    }
}