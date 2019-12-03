package de.derkalaender.aoc.nineteen.day3

data class Point(val x: Int, val y: Int) {
    fun trace(steps: List<Pair<Char, Int>>): Set<Point> {
        val newPoints = mutableSetOf<Point>()
        val firstStep = steps[0]
        val restSteps = steps.subList(1, steps.size)

        for(i in 1..firstStep.second) {
            val p = when(firstStep.first) {
                'L' -> Point(x - i, y)
                'R' -> Point(x + i, y)
                'U' -> Point(x, y + i)
                else -> Point(x, y - i)
            }
            newPoints.add(p)

            if(i == firstStep.second) {
                if(restSteps.isNotEmpty()) {
                    newPoints.addAll(p.trace(restSteps))
                }
            }
        }

        return newPoints
    }
}