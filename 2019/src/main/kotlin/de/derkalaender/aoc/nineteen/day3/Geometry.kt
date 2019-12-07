package de.derkalaender.aoc.nineteen.day3

import kotlin.math.absoluteValue

data class Point(val x: Int, val y: Int) {
    fun getManhattanDistance(o: Point): Int {
        return (o.x - x).absoluteValue + (o.y - y).absoluteValue
    }

    infix fun isOn(l: Line): Boolean {
        return if (x == l.start.x && x == l.end.x) {
            (l.start.y < y && y < l.end.y || l.start.y > y && y > l.end.y)
        } else if(y == l.start.y && y == l.end.y) {
            (l.start.x < x && x < l.end.x || l.start.x > x && x > l.end.x)
        } else {
            false
        }
    }
}

data class Line(val start: Point, val end: Point) {
    val vertical by lazy { start.x == end.x }
    val horizontal by lazy { start.y == end.y }
    val length by lazy { start.getManhattanDistance(end) }

    fun getIntersection(o: Line): Point? {
        return if (vertical == o.vertical || horizontal == o.horizontal) {
            null
        } else {
            val intersection = if (vertical && o.horizontal) {
                Point(start.x, o.start.y)
            } else {
                Point(o.start.x, start.y)
            }
            return if (intersection isOn this && intersection isOn o) intersection else null
        }
    }

    infix fun crosses(p: Point): Boolean = p.isOn(this)
}

data class Path(val start: Point, val lines: Set<Line>) {
    companion object {
        @JvmStatic
        fun trace(start: Point, steps: List<Pair<Char, Int>>): Path {
            val lines = mutableSetOf<Line>()
            var last = start

            for (step in steps) {
                val next = when (step.first) {
                    'L' -> Point(last.x - step.second, last.y)
                    'R' -> Point(last.x + step.second, last.y)
                    'U' -> Point(last.x, last.y + step.second)
                    else -> Point(last.x, last.y - step.second)
                }
                lines.add(Line(last, next))
                last = next
            }
            return Path(start, lines)
        }
    }

    val length by lazy { lines.sumBy { it.length } }

    fun getAllIntersections(o: Path): Set<Point> {
        return lines.flatMap { tl ->
            o.lines.mapNotNull { ol ->
                ol.getIntersection(tl)
            }
        }.toSet()
    }

    fun getSubPath(p: Point): Path? {
        val line = lines.find { it crosses p }
        return if(line == null) {
            null
        } else {
            val newLines = lines.take(lines.indexOf(line)).toMutableSet()
            newLines.add(Line(line.start, p))
            return Path(start, newLines)
        }
    }
}