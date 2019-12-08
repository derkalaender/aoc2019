package de.derkalaender.aoc.nineteen.day5

fun main() {
    val fileContent = {}.javaClass.getResource("/inputs/day5/input.txt").readText()

    val opCode = fileContent.splitToSequence(',').map { it.toInt() }

    Start(opCode.toMutableList()).exec()
}