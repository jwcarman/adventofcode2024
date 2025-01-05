/*
 * Copyright (c) 2025 James Carman
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

package adventofcode.util.trie

class Trie {

    private val root: TrieNode = TrieNode("")

    fun insert(word: String) {
        var current = root
        for (char in word) {
            current = current.children.getOrPut(char) { TrieNode(current.prefix + char) }
        }
        current.isEndOfWord = true
    }

    fun prefixesOf(word: String): List<String> {
        var current = root
        val prefixes = mutableListOf<String>()
        for (char in word) {
            if (current.isEndOfWord) {
                prefixes.add(current.prefix)
            }
            current = current.children[char] ?: return prefixes
        }
        if (current.isEndOfWord) {
            prefixes.add(current.prefix)
        }

        return prefixes
    }

    fun search(word: String): Boolean {
        var current = root
        for (char in word) {
            current = current.children[char] ?: return false
        }
        return current.isEndOfWord
    }

    fun startsWith(prefix: String): Boolean {
        var current = root
        for (char in prefix) {
            current = current.children[char] ?: return false
        }
        return true
    }
}

private class TrieNode(val prefix: String) {
    val children: MutableMap<Char, TrieNode> = mutableMapOf()
    var isEndOfWord: Boolean = false
}