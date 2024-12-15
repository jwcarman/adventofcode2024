package adventofcode.day15

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class WarehouseWoesTest {

    @Test
    fun `walls should be replaced by two walls`() {
        "#".widened() shouldBe "##"
    }

    @Test
    fun `boxes should be widened to left and right box`() {
        "O".widened() shouldBe "[]"
    }

    @Test
    fun `open space should be widened to two open spaces`() {
        ".".widened() shouldBe ".."
    }

    @Test
    fun `robot should be replaced by robot and open`() {
        "@".widened() shouldBe "@."
    }

    @Test
    fun `small example should widen properly`() {
        val input = """
            #######
            #...#.#
            #.....#
            #..OO@#
            #..O..#
            #.....#
            #######   
        """.trimIndent().trim()

        val target = """
            ##############
            ##......##..##
            ##..........##
            ##....[][]@.##
            ##....[]....##
            ##..........##
            ##############
        """.trimIndent().trim()

        input.widened() shouldBe target
    }

    @Test
    fun `large example should widen properly`() {
        val input = """
            ##########
            #..O..O.O#
            #......O.#
            #.OO..O.O#
            #..O@..O.#
            #O#..O...#
            #O..O..O.#
            #.OO.O.OO#
            #....O...#
            ##########
        """.trimIndent().trim()

        val target = """
            ####################
            ##....[]....[]..[]##
            ##............[]..##
            ##..[][]....[]..[]##
            ##....[]@.....[]..##
            ##[]##....[]......##
            ##[]....[]....[]..##
            ##..[][]..[]..[][]##
            ##........[]......##
            ####################
        """.trimIndent().trim()

        input.widened() shouldBe target
    }

}