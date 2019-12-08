package de.derkalaender.aoc.nineteen.day1

fun main() {
    val fileContent = {}.javaClass.getResource("/inputs/day1/input.txt").readText()

    val moduleMasses = fileContent.lineSequence().map { it.trim().toInt() }

    val totalAmountOfFuelNeeded = moduleMasses.fold(0, { total, next ->
        total + calcFuel(next)
    })

    println(totalAmountOfFuelNeeded)
}

tailrec fun calcFuel(newMass: Int, totalMass: Int = 0): Int {
    val fuel = ((newMass/3) - 2).coerceAtLeast(0)

    /*
    Only further calculate fuel if additional amount would be at least 1
    => (1 + 2) * 3 = 9
    */
    return if(fuel >= 9) {
        calcFuel(fuel, totalMass + fuel)
    } else {
        return totalMass
    }
}