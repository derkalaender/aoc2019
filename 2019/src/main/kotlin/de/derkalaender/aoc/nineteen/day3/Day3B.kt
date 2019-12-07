package de.derkalaender.aoc.nineteen.day3

fun main() {
    val fileContent = {}.javaClass.getResource("/inputs/day3/input.txt").readText()

    val pointSteps = fileContent.lines().map { l ->
        l.splitToSequence(',').map {
            it[0] to it.substring(1).toInt()
        }.toList()
    }

    val start = Point(0, 0)
    val pathA = Path.trace(start, pointSteps[0])
    val pathB = Path.trace(start, pointSteps[1])
    val crossPoints = pathA.getAllIntersections(pathB).filterNot { it == start }
    val crossPointSubPaths = crossPoints.map { pathA.getSubPath(it)!! to pathB.getSubPath(it)!! }

    val distances = crossPointSubPaths.map {it.first.length + it.second.length}

    println("Smallest combined path lengths: ${distances.min()}")
}