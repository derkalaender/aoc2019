package de.derkalaender.aoc.nineteen.day4

fun main() {
    val inputRange = 359282..820401

    val passwords = inputRange.filter {
        val digits = it.toString().toCharArray().map(Char::toInt)
        val zipped = digits.zipWithNext()
        val firstCriterium = zipped.any { t ->
            t.first == t.second
        } && zipped.all { t -> t.first <= t.second }
        var secondCriterium = false
        digits.forEachIndexed { index, d ->
            if (index != 0) {
                if (d == digits[index - 1]) {
                    if (index == 1 || digits[index - 2] != d) {
                        if (index == digits.size - 1 || digits[index + 1] != d) {
                            secondCriterium = true
                        }
                    }
                }
            }
        }
        firstCriterium && secondCriterium
    }

    println("${passwords.size} passwords match!")

}