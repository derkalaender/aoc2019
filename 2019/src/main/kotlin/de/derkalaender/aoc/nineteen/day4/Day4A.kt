package de.derkalaender.aoc.nineteen.day4

fun main() {
    val inputRange = 359282..820401

    val passwords = inputRange.filter {
        val digits = it.toString().toCharArray().map(Char::toInt)
        val zipped = digits.zipWithNext()
        zipped.any { t -> t.first == t.second } && zipped.all { t -> t.first <= t.second }
    }
    println("${passwords.size} passwords match!")
}