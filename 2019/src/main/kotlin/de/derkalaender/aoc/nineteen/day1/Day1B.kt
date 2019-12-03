package de.derkalaender.aoc.nineteen.day1

fun main() {
    val fileContent = {}.javaClass.getResource("/inputs/day1/input.txt").readText()

    val moduleMasses = fileContent.lineSequence().map { it.trim().toInt() }

    val totalAmountOfFuelNeeded = moduleMasses.fold(0, { total, next ->
        total + calcFuel(next)
    })

    println(totalAmountOfFuelNeeded)
}

fun calcFuel(mass: Int): Int {
    var fuel = ((mass/3) - 2).coerceAtLeast(0)

    /*
    Only further calculate fuel if additional amount would be at least 1
    => (1 + 2) * 3 = 9
    */
    if(fuel >= 9) {
        fuel += calcFuel(fuel)
    }
    return fuel
}