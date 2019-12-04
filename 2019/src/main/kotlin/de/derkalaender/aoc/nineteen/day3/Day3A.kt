package de.derkalaender.aoc.nineteen.day3

fun main() {
    val fileContent = {}.javaClass.getResource("/inputs/day3/input.txt").readText()

    val pointSteps = fileContent.lines().map { l ->
        l.splitToSequence(',').map {
            it[0] to it.substring(1).toInt()
        }.toList()
    }

    val start = Point(0, 0)
    val pathA = Path(start).trace(pointSteps[0])
    val pathB = Path(start).trace(pointSteps[1])
    val crossPoints = pathA.getAllIntersections(pathB).filterNot { it == start }

    val distances = crossPoints.map { it.getManhattanDistance(start) }

    println("Smallest Manhatten Distance: ${distances.min()}")
}