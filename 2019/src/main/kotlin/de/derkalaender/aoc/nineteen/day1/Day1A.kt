package de.derkalaender.aoc.nineteen.day1

fun main() {
    val fileContent = {}.javaClass.getResource("/inputs/day1/input.txt").readText()

    val moduleMasses = fileContent.lineSequence().map { it.trim().toInt() }

    val totalAmountOfFuelNeeded = moduleMasses.fold(0, { total, next ->
        total + (next/3) - 2
    })

    println(totalAmountOfFuelNeeded)
}