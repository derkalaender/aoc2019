package de.derkalaender.aoc.nineteen.day3

import kotlin.math.absoluteValue

data class Point(val x: Int, val y: Int) {
    fun getManhattanDistance(o: Point): Int {
        return (o.x - x).absoluteValue + (o.y - y).absoluteValue
    }
}

data class Line(val start: Point, val end: Point) {
    fun getIntersection(o: Line): Point? {
        if ((start.x <= o.start.x && start.y <= o.start.y && end.x >= o.end.x && end.y >= o.end.y) ||
            (start.x <= o.end.x && start.y <= o.end.y && end.x >= o.start.x && end.y >= o.start.y)
        ) {
            return Point(o.start.x, start.y)
        } else if ((start.x >= o.start.x && start.y >= o.start.y && end.x <= o.end.x && end.y <= o.end.y) ||
            (start.x >= o.end.x && start.y >= o.end.y && end.x <= o.start.x && end.y <= o.start.y)
        ) {
            return Point(start.x, o.start.y)
        }
        return null
    }
}

class Path(val start: Point) {
    val lines = mutableSetOf<Line>()

    fun trace(steps: List<Pair<Char, Int>>): Path {
        val newPath = Path(start)
        var last = start
        var next: Point

        for (step in steps) {
            next = when (step.first) {
                'L' -> Point(last.x - step.second, last.y)
                'R' -> Point(last.x + step.second, last.y)
                'U' -> Point(last.x, last.y + step.second)
                else -> Point(last.x, last.y - step.second)
            }
            newPath.lines.add(Line(last, next))
            last = next
        }
        return newPath
    }

    fun getAllIntersections(o: Path): Set<Point> {
        return lines.flatMap { tl ->
            o.lines.mapNotNull { ol ->
                ol.getIntersection(tl)
            }
        }.toSet()
    }
}