package adventofcode.util.trie

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class TrieTest {
    @Test
    fun `single letter should be a valid prefix`() {
        val trie = Trie()
        trie.insert("r")
        trie.insert("ru")
        trie.insert("hello")
        trie.insert("ruf")
        trie.insert("ruff")
        trie.prefixesOf("ruff") shouldBe listOf("r", "ru", "ruf", "ruff")
    }
}