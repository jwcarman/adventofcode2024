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

package adventofcode.day09

import adventofcode.util.isEven


private const val EMPTY_BLOCK = -1


fun MutableList<Int>.fileDefragment(): MutableList<Int> {
    var fileScanNdx = size - 1
    var currentFileId = last()
    var currentFileSize = 0
    var currentFileStart = fileScanNdx
    while (fileScanNdx >= 0) {
        val fileId = get(fileScanNdx)
        if (fileId != EMPTY_BLOCK) {
            if (fileId == currentFileId) {
                currentFileSize++
                currentFileStart = fileScanNdx
            } else {
                var emptyScanNdx = 0
                var emptySize = 0
                while (emptyScanNdx < currentFileStart) {
                    if (get(emptyScanNdx) == EMPTY_BLOCK) {
                        emptySize++
                        if (emptySize == currentFileSize) {
                            repeat(currentFileSize) {
                                set(emptyScanNdx - it, currentFileId)
                                set(currentFileStart + it, EMPTY_BLOCK)
                            }
                            break
                        }
                    } else {
                        emptySize = 0
                    }
                    emptyScanNdx++
                }
                currentFileId = fileId
                currentFileSize = 1
                currentFileStart = fileScanNdx
            }
        }
        fileScanNdx--
    }
    return this
}

fun String.toBlocks() = toCharArray()
    .map { it - '0' }
    .flatMapIndexed { index: Int, n: Int -> if (index.isEven()) List(n) { index / 2 } else List(n) { EMPTY_BLOCK } }

fun List<Int>.blockDefragment() = sequence {
    val blocks = filterNot { it == EMPTY_BLOCK }
    val frontBlocks = iterator()
    val backBlocks = blocks.reversed().iterator()
    repeat(blocks.size) {
        val frontBlock = frontBlocks.next()
        yield(if (frontBlock == EMPTY_BLOCK) backBlocks.next() else frontBlock)
    }
}

fun Sequence<Int>.calculateFilesystemChecksum() = mapIndexed { pos, fileId -> if(fileId == EMPTY_BLOCK) 0 else pos * fileId }.map { it.toLong() }.sum()