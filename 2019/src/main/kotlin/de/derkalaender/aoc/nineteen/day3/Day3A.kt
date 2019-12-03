package de.derkalaender.aoc.nineteen.day3

import kotlin.math.absoluteValue

fun main() {
    val fileContent = {}.javaClass.getResource("/inputs/day3/input.txt").readText()

    // I apologize for everything in here

    val pointSteps = fileContent.lines().map { l ->
        l.splitToSequence(',').map {
            it[0] to it.substring(1).toInt()
        }.toList()
    }

    val p = Point(0, 0)

    val traceA = p.trace(pointSteps[0])
    val traceB = p.trace(pointSteps[1])

    val crossPoints = traceA.intersect(traceB)

    val distances = crossPoints.map { (it.x + it.y).absoluteValue }

    val smallest = distances.min()

    println("Smallest Manhattan Distance: $smallest")
}