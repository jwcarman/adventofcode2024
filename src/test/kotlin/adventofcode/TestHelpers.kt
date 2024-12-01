package adventofcode

import adventofcode.util.readAsString


private fun Any.resourcePrefix() = this::class.java.simpleName.substring(0, 5).lowercase()

fun Any.readExample1() = readAsString("${resourcePrefix()}-example1.txt")
fun Any.readExample2() = readAsString("${resourcePrefix()}-example2.txt")
fun Any.readInput() = readAsString("${resourcePrefix()}.txt")